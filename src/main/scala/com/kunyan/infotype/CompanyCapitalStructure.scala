package com.kunyan.infotype

import com.kunyan.db.LazyConnections
import com.kunyan.entity.CapitalStructure
import com.kunyan.util.{StringUtil, DBUtil}
import org.jsoup.Jsoup

import scala.collection.mutable.ListBuffer

/**
 * Created by lcm on 2017/4/12.
 * 股本结构
 */
object CompanyCapitalStructure {

  def reqWebData(url: String, stockCode: String,
                 capitalStructureList: ListBuffer[CapitalStructure]): Unit = {


    try {

      val doc = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0")
        .timeout(50000)
        .get()
      val eleTbody = doc.select("div [class=\"section\"]").get(1).select("tbody tr")

      if (eleTbody.size() > 0) {

        val eleDate = eleTbody.get(0).select("th[class=\"tips-dataR\"]")

        if (eleDate.size() >= 7) {

          val data:Array[Array[String]] = new Array(eleTbody.size())

          for (row <- 0 until eleTbody.size()) {

            val line:Array[String] = new Array(8)

            line(0) = eleTbody.get(row).children().first().text()

            for(col <- 0 to 6){

              var field = ""

              if(row == 0){
                field = eleTbody.get(row).select("th[class=\"tips-dataR\"]").get(col).text()
              }else{
                field = eleTbody.get(row).select("td[class=\"tips-dataR\"]").get(col).text()
              }

              if(field.contains(",") && line(0) != "变动原因"){
                field = field.replace(",","")
              }

              line(col + 1) = field
            }

            data(row) = line
          }
//          println(StringUtil.arrToJson(data))
          val capitalStructure = new CapitalStructure(stockCode,StringUtil.arrToStr(data))
          capitalStructureList.+=:(capitalStructure)

        } else {

          val data:Array[Array[String]] = new Array(eleTbody.size())

          for (row <- 0 until eleTbody.size()) {

            val line:Array[String] = new Array(eleDate.size() + 1)

            line(0) = eleTbody.get(row).children().first().text()

            for(col <- 0 until  eleDate.size()){

              var field = ""
              if(row == 0){
                field = eleTbody.get(row).select("th[class=\"tips-dataR\"]").get(col).text()
              }else{
                field = eleTbody.get(row).select("td[class=\"tips-dataR\"]").get(col).text()
              }

              if(field.contains(",") && line(0) != "变动原因"){
                field = field.replace(",","")
              }

              line(col + 1) = field
            }

            data(row) = line
          }

          //println(StringUtil.arrToStr(data))
          val capitalStructure = new CapitalStructure(stockCode,StringUtil.arrToStr(data))
          capitalStructureList.+=:(capitalStructure)

        }
      }

    }catch {

      case e: Exception =>
        println(url)
        e.printStackTrace()
    }


  }

  def insertData(lazyConn: LazyConnections, capitalStructureList: ListBuffer[CapitalStructure]): Unit = {

    val mysqlCompanyConn = lazyConn.mysqlCompanyConn
    mysqlCompanyConn.setAutoCommit(true)

    //清空表数据
    DBUtil.clearTable(mysqlCompanyConn, "capital_structure")
    println("清空表数据完成。。。")

    val insertCompanySql = mysqlCompanyConn.prepareStatement("INSERT INTO capital_structure (stock_code," +
      "data) VALUES (?,?)")

    for (capitalStructure <- capitalStructureList) {

      //插入数据
      DBUtil.insert(insertCompanySql,capitalStructure.cSStockCode,capitalStructure.cSData)

    }

    println("数据写入完成。。。。")
  }

}
