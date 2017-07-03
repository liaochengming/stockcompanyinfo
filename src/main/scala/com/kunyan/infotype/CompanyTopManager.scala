package com.kunyan.infotype

import com.kunyan.db.LazyConnections
import com.kunyan.entity.CompanyExecutive
import com.kunyan.util.DBUtil
import org.jsoup.Jsoup

import scala.collection.mutable.ListBuffer

/**
 * Created by lcm on 2017/4/12.
 * 公司高管
 */
object CompanyTopManager {

  def reqWebData(url: String, stockCode: String,
                 companyExecutiveList: ListBuffer[CompanyExecutive]): Unit = {

    try {
      val doc = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0")
        .timeout(50000)
        .get()

      val companyExecutiveEles = doc.body().select("div[class=\"section\"]").get(1).select("tbody")

      if (companyExecutiveEles.size() > 0) {

        var date = ""
        var number = ""
        var name = ""
        var sex = ""
        var age = ""
        var education = ""
        var duty = ""
        var brief_intro = ""
        var tempStringArr:Array[String] = null

        for (index <- 0 until companyExecutiveEles.size()) {

          val companyExecutiveInfo = companyExecutiveEles.get(index).select("td")

          tempStringArr = companyExecutiveInfo.get(6).text().split(":")
          if(tempStringArr.length == 2){
            date = tempStringArr(1)
          }

          number = (index + 1).toString
          name = companyExecutiveInfo.get(0).text().replace(" ", "")
          tempStringArr = companyExecutiveInfo.get(1).text().split(":")

          if(tempStringArr.length == 2){
            sex = tempStringArr(1)
          }

          tempStringArr = companyExecutiveInfo.get(4).text().split(":")
          if(tempStringArr.length == 2){
            age = tempStringArr(1)
          }

          tempStringArr = companyExecutiveInfo.get(2).text().split(":")
          if(tempStringArr.length == 2){
            education = tempStringArr(1)
          }

          tempStringArr = companyExecutiveInfo.get(5).text().split(":")
          if(tempStringArr.length == 2){
            duty = tempStringArr(1)
          }

          brief_intro = companyExecutiveInfo.get(3).text()

          val companyExecutive = new CompanyExecutive(stockCode, date, number, name,
            sex, age, education, duty, brief_intro)

          //println(companyExecutive.toString)
          companyExecutiveList.+=:(companyExecutive)

        }
      }


    } catch {
      case e: Exception =>
        println(url)
        e.printStackTrace()
    }


  }

  def insertData(lazyConn: LazyConnections, companyExecutiveList: ListBuffer[CompanyExecutive]): Unit = {

    val mysqlCompanyConn = lazyConn.mysqlCompanyConn
    mysqlCompanyConn.setAutoCommit(true)

    //清空表数据
    DBUtil.clearTable(mysqlCompanyConn,"company_administer")
    println("清空表数据完成。。。")

    val insertCompanySql = mysqlCompanyConn.prepareStatement("INSERT INTO company_administer (stock_code,date," +
      "number,name,sex,age,education,duty,brief_info) VALUES (?,?,?,?,?,?,?,?,?)")

    for (companyExecutive <- companyExecutiveList) {

      //插入数据
      DBUtil.insert(insertCompanySql,companyExecutive.executiveStockCode,companyExecutive.executiveDate,
        companyExecutive.executiveNumber,companyExecutive.executiveName,companyExecutive.executiveSex,
        companyExecutive.executiveAge,companyExecutive.executiveEducation,companyExecutive.executiveDuty,
        companyExecutive.executiveBrief_info)
    }

    println("数据写入完成。。。。")
  }

}
