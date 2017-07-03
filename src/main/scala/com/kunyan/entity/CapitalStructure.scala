package com.kunyan.entity

/**
 * Created by lcm on 2017/3/14.
 * 股本结构
 */
class CapitalStructure(stockCode: String,data:String) {

  val cSStockCode = stockCode
  val cSData = data

  override def toString:String ={

    cSStockCode + " " + cSData
  }
}
