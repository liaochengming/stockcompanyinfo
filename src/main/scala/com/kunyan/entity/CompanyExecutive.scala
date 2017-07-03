package com.kunyan.entity

/**
 * Created by lcm on 2017/3/14.
 * 公司高管
 */
class CompanyExecutive(stockCode: String, date: String,
                       number: String, name: String,
                       sex: String, age: String,
                       education: String, duty: String,
                       brief_info: String) {


  val executiveStockCode = stockCode
  val executiveNumber = number
  val executiveName = name
  val executiveSex = sex
  val executiveAge = age
  val executiveEducation = education
  val executiveDuty = duty
  val executiveDate = date
  val executiveBrief_info = brief_info

  override def toString:String={
    executiveStockCode + " " + executiveNumber +
    " " + executiveName + " " + executiveSex +
    " " + executiveAge + " " + executiveEducation +
    " " + executiveDuty + " " + executiveDate +
    " " + executiveBrief_info
  }

}
