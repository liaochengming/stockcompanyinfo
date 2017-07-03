package com.kunyan.entity

/**
 * Created by lcm on 2017/3/14.
 * 十大股东的信息类
 */
class StockHolder(stockCode: String, date: String,
                  rank: Int, stockHolderName: String,
                  shareType: String,
                  sharesNumber: String, totalRatio: String,
                  changeShare: String, changeRatio: String) {

  val sHStockCode: String = stockCode
  val sHDate: String = date
  val sHRank: Int = rank
  val sHStockHolderName: String = stockHolderName
  val sHShareType: String = shareType
  val sHSharesNumber: String = sharesNumber
  val sHTotalRatio: String = totalRatio
  val sHChangeShare: String = changeShare
  val sHChangeRatio: String = changeRatio

  override def toString: String = {
    sHStockCode + " " + sHDate + " " +
      sHRank + " " + sHStockHolderName + " " +
      sHShareType + " " + sHSharesNumber + " " + sHTotalRatio + " " +
      sHChangeShare + " " + sHChangeRatio
  }
}
