package com.kunyan.control

import com.kunyan.db.LazyConnections
import com.kunyan.entity._
import com.kunyan.infotype.{CompanyCapitalStructure, CompanyProfile, CompanyTopManager, TenBigFloatHolder}

import scala.collection.mutable.ListBuffer
import scala.xml.XML

/**
 * Created by lcm on 2017/3/14.
 * 主类
 */
object Control {

  def main(args: Array[String]) {

    val configFilePath = args(0)
    val configFile = XML.loadFile(configFilePath)
    val lazyConn = LazyConnections(configFile)

    val mysqlConnection = lazyConn.mysqlConn
    mysqlConnection.setAutoCommit(true)

    val selectSql = mysqlConnection.prepareStatement("select * from stock_basic")
    val selectResult = selectSql.executeQuery()

    var stockCode = ""

    val companyList = new ListBuffer[Company]
    val floatStockHolderList = new ListBuffer[FloatStockHolder]
    val stockHolderList = new ListBuffer[StockHolder]
    val capitalStructureList = new ListBuffer[CapitalStructure]
    val companyExecutiveList = new ListBuffer[CompanyExecutive]

    while (selectResult.next()) {

      stockCode = selectResult.getString("code")

      var companyProfileUrl = ""
      var tenBigFloatHolderUrl = ""
      var companyCapitalStructureUrl = ""
      var companyTopManagerUrl = ""

      if(stockCode.startsWith("6")){

        companyProfileUrl = String.format("http://f10.eastmoney.com/f10_v2/CompanySurvey.aspx?code=sh%s", stockCode)
        tenBigFloatHolderUrl = String.format("http://f10.eastmoney.com/f10_v2/ShareholderResearch.aspx?code=sh%s", stockCode)
        companyCapitalStructureUrl = String.format("http://f10.eastmoney.com/f10_v2/CapitalStockStructure.aspx?code=sh%s", stockCode)
        companyTopManagerUrl = String.format("http://f10.eastmoney.com/f10_v2/CompanyManagement.aspx?code=sh%s", stockCode)

      }else{

        companyProfileUrl = String.format("http://f10.eastmoney.com/f10_v2/CompanySurvey.aspx?code=sz%s", stockCode)
        tenBigFloatHolderUrl = String.format("http://f10.eastmoney.com/f10_v2/ShareholderResearch.aspx?code=sz%s", stockCode)
        companyCapitalStructureUrl = String.format("http://f10.eastmoney.com/f10_v2/CapitalStockStructure.aspx?code=sz%s", stockCode)
        companyTopManagerUrl = String.format("http://f10.eastmoney.com/f10_v2/CompanyManagement.aspx?code=sz%s", stockCode)

      }

      CompanyProfile.reqWebData(companyProfileUrl,stockCode,companyList)
      Thread.sleep(500L)
      TenBigFloatHolder.reqWebData(tenBigFloatHolderUrl, stockCode, floatStockHolderList, stockHolderList)
      Thread.sleep(500L)
      CompanyCapitalStructure.reqWebData(companyCapitalStructureUrl,stockCode,capitalStructureList)
      Thread.sleep(500L)
      CompanyTopManager.reqWebData(companyTopManagerUrl,stockCode,companyExecutiveList)
      Thread.sleep(500L)
    }

    selectSql.close()
    mysqlConnection.close()

    println("companyList size = " + companyList.size)
    println("floatStockHolderList size = " + floatStockHolderList.size)
    println("stockHolderList size = " + stockHolderList.size)
    println("capitalStructureList size = " + capitalStructureList.size)
    println("companyExecutiveList size = " + companyExecutiveList.size)

    CompanyProfile.insertData(lazyConn,companyList)
    TenBigFloatHolder.insertData(lazyConn, floatStockHolderList, stockHolderList)
    CompanyCapitalStructure.insertData(lazyConn,capitalStructureList)
    CompanyTopManager.insertData(lazyConn,companyExecutiveList)

  }



}
