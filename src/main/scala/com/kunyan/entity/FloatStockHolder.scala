package com.kunyan.entity

/**
 * Created by lcm on 2017/3/14.
 * 十大流通股东信息类
 */
class FloatStockHolder(stockCode:String,date:String,
                       rank:Int, stockHolderName:String,
                       stockHolder_nature:String, shareType:String,
                       sharesNumber:String, totalRatio:String,
                       changeShare:String, changeRatio:String) {

  val fSHStockCode: String = stockCode
  val fSHDate: String = date
  val fSHRank: Int = rank
  val fSHStockHolderName: String = stockHolderName
  val fSHStockHolder_nature: String = stockHolder_nature
  val fSHShareType: String = shareType
  val fSHSharesNumber: String = sharesNumber
  val fSHTotalRatio: String = totalRatio
  val fSHChangeShare: String = changeShare
  val fSHChangeRatio: String = changeRatio

  override def toString:String={

    fSHStockCode + " " + fSHDate + " " +
      fSHRank + " " + fSHStockHolderName + " " + fSHStockHolder_nature + " " +
      fSHShareType + " " + fSHSharesNumber + " " + fSHTotalRatio + " " +
      fSHChangeShare + " " + fSHChangeRatio

  }
}
