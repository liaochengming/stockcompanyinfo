package com.kunyan.entity

/**
 * Created by lcm on 2017/3/14.
 * 十大流通股东信息类
 */
class Company(stockCode: String, companyName: String,
              companyEngName: String, usedName: String,
              AStockCode: String, AShort: String,
              BStockCode: String, BShort: String,
              HStockCode: String, HShort: String,
              securityType: String, industryInvolved: String,
              CEO: String, lawPerson: String,
              secretary: String, chairman: String,
              securityAgent: String, independentDirector: String,
              companyTel: String, companyEmail: String,
              companyFax: String, companyWebsite: String,
              businessAddress: String, regAddress: String,
              area: String, postCode: String,
              regCapital: String, businessRegistration: String,
              employeeNum: String, adminNum: String,
              lawFirm: String, accountingFirm: String,
              companyIntro: String, businessScope: String
               ) {


  val cStockCode = stockCode
  val cCompanyName = companyName
  val cCompanyEngName = companyEngName
  val cUsedName = usedName
  val cAStockCode = AStockCode
  val cAShort = AShort
  val cBStockCode = BStockCode
  val cBShort = BShort
  val cHStockCode = HStockCode
  val cHShort = HShort
  val cSecurityType = securityType
  val cIndustryInvolved = industryInvolved
  val cCEO = CEO
  val cLawPerson = lawPerson
  val cSecretary = secretary
  val cChairman = chairman
  val cSecurityAgent = securityAgent
  val cIndependentDirector = independentDirector
  val cCompanyTel = companyTel
  val cCompanyEmail = companyEmail
  val cCompanyFax = companyFax
  val cCompanyWebsite = companyWebsite
  val cBusinessAddress = businessAddress
  val cRegAddress = regAddress
  val cArea = area
  val cPostCode = postCode
  val cRegCapital = regCapital
  val cBusinessRegistration = businessRegistration
  val cEmployeeNum = employeeNum
  val cAdminNum = adminNum
  val cLawFirm = lawFirm
  val cAccountingFirm = accountingFirm
  val cCompanyIntro = companyIntro
  val cBusinessScope = businessScope

  override def toString: String = {

      cStockCode + cCompanyName + cCompanyEngName +
      cUsedName + cAStockCode + cAShort + cBStockCode +
      cBShort + cHStockCode + cHShort + cSecurityType +
      cIndustryInvolved + cCEO + cLawPerson +
      cSecretary + cChairman + cSecurityAgent +
      cIndependentDirector + cCompanyTel + cCompanyEmail +
      cCompanyFax + cCompanyWebsite + cBusinessAddress +
      cRegAddress + cArea + cPostCode +
      cRegCapital + cBusinessRegistration + cEmployeeNum +
      cAdminNum + cLawFirm + cAccountingFirm +
      cCompanyIntro + cBusinessScope
  }
}
