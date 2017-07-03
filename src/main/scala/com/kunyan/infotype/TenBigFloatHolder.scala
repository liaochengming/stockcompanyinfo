package com.kunyan.infotype

import com.kunyan.db.LazyConnections
import com.kunyan.entity.{StockHolder, FloatStockHolder}
import com.kunyan.util.DBUtil
import org.jsoup.Jsoup

import scala.collection.mutable.ListBuffer

/**
 * Created by lcm on 2017/4/12.
 * 十大股东
 */
object TenBigFloatHolder {

  def reqWebData(url: String, stockCode: String, floatStockHolderList: ListBuffer[FloatStockHolder],
                 stockHolderList: ListBuffer[StockHolder]): Unit = {


    try {
      val doc = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0")
        .timeout(50000)
        .get()

      var eleDate = doc.body().select("div[class=\"section\"]:contains(十大流通股东) div.tab ul li")
      var date = ""
      var fSHRank = 0
      var fSHStockHolderName = ""
      var fSHStockHolder_nature = ""
      var fSHShareType = ""
      var fSHSharesNumber = ""
      var fSHTotalRatio = ""
      var fSHChangeShare = ""
      var fSHChangeRatio = ""

      for (num <- 0 until eleDate.size()) {

        date = eleDate.get(num).text()
        val floatStockholders = doc.body().select("div[id=\"TTCS_Table_Div\"] tbody").get(num)
          .select("tr:not(:contains(名次),:contains(合计))")
        for (index <- 0 until floatStockholders.size()) {

          val floatStockHolderInfo = floatStockholders.get(index).children()

          fSHRank = floatStockHolderInfo.get(0).select("em").text().toInt
          fSHStockHolderName = floatStockHolderInfo.get(1).text()
          fSHStockHolder_nature = floatStockHolderInfo.get(2).text()
          fSHShareType = floatStockHolderInfo.get(3).text()
          fSHSharesNumber = floatStockHolderInfo.get(4).text()
          fSHTotalRatio = floatStockHolderInfo.get(5).text()
          fSHChangeShare = floatStockHolderInfo.get(6).text()
          fSHChangeRatio = floatStockHolderInfo.get(7).text()

          if (fSHChangeRatio == "--") fSHChangeRatio = ""

          val floatStockHolder = new FloatStockHolder(stockCode, date, fSHRank, fSHStockHolderName, fSHStockHolder_nature,
            fSHShareType, fSHSharesNumber, fSHTotalRatio, fSHChangeShare, fSHChangeRatio)

          //println(floatStockHolder.toString)
          floatStockHolderList.+=:(floatStockHolder)
        }
      }

      eleDate = doc.body().select("div[class=\"section\"]:contains(十大股东) div.tab ul li")

      for (num <- 0 until eleDate.size()) {

        date = eleDate.get(num).text()
        var sHRank = 0
        var sHStockHolderName = ""
        var sHShareType = ""
        var sHSharesNumber = ""
        var sHTotalRatio = ""
        var sHChangeShare = ""
        var sHChangeRatio = ""
        val stockholders = doc.body().select("div[id=\"TTS_Table_Div\"] tbody").get(num)
          .select("tr:not(:contains(名次),:contains(合计))")

        for (index <- 0 until stockholders.size()) {

          val floatStockHolderInfo = stockholders.get(index).children()
          sHRank = floatStockHolderInfo.get(0).text().toInt
          sHStockHolderName = floatStockHolderInfo.get(1).text()
          sHShareType = floatStockHolderInfo.get(2).text()
          sHSharesNumber = floatStockHolderInfo.get(3).text()
          sHTotalRatio = floatStockHolderInfo.get(4).text()
          sHChangeShare = floatStockHolderInfo.get(5).text()
          sHChangeRatio = floatStockHolderInfo.get(6).text()

          if (sHChangeRatio == "--") sHChangeRatio = ""

          val stockHolder = new StockHolder(stockCode, date, sHRank, sHStockHolderName,
            sHShareType, sHSharesNumber, sHTotalRatio, sHChangeShare, sHChangeRatio)

          //println(stockHolder.toString)
          stockHolderList.+=:(stockHolder)
        }
      }


    } catch {
      case e: NullPointerException =>
        println(url)
        e.printStackTrace()
    }


  }

  def insertData(lazyConn: LazyConnections, floatStockHolderList: ListBuffer[FloatStockHolder],
                 stockHolderList: ListBuffer[StockHolder]): Unit = {

    val floatStockHolderPS = lazyConn.floatStockHolderPreparedStatement
    val stockHolderPS = lazyConn.stockHolderPreparedStatement

    val mysqlCompanyConn = lazyConn.mysqlCompanyConn

    //清空表数据
    DBUtil.clearTable(mysqlCompanyConn, "top10_float_stockholder")
    DBUtil.clearTable(mysqlCompanyConn, "top10_stockholder")
    println("清空表数据完成。。。")

    floatStockHolderList.foreach(floatStockHolder => {

      DBUtil.insert(floatStockHolderPS, floatStockHolder.fSHStockCode, floatStockHolder.fSHDate,
        floatStockHolder.fSHRank, floatStockHolder.fSHStockHolderName, floatStockHolder.fSHStockHolder_nature,
        floatStockHolder.fSHShareType, floatStockHolder.fSHSharesNumber, floatStockHolder.fSHTotalRatio,
        floatStockHolder.fSHChangeShare, floatStockHolder.fSHChangeRatio)

    })

    stockHolderList.foreach(stockHolder => {

      DBUtil.insert(stockHolderPS, stockHolder.sHStockCode, stockHolder.sHDate,
        stockHolder.sHRank, stockHolder.sHStockHolderName,
        stockHolder.sHShareType, stockHolder.sHSharesNumber, stockHolder.sHTotalRatio,
        stockHolder.sHChangeShare, stockHolder.sHChangeRatio)

    })

    println("数据写入完成。。。。")

  }

}
