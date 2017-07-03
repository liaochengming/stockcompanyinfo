package com.kunyan.infotype

import com.kunyan.db.LazyConnections
import com.kunyan.entity.Company
import com.kunyan.util.DBUtil
import org.jsoup.Jsoup

import scala.collection.mutable.ListBuffer

/**
 * Created by lcm on 2017/4/12.
 * 公司概况
 */
object CompanyProfile {

  def reqWebData(url: String, stockCode: String,
                 companyList: ListBuffer[Company]): Unit = {

    try {
      val doc = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0")
        .timeout(50000)
        .get()

      val companyInfo = doc.body().select("div[class=\"section first\"] tbody td")

      if (companyInfo.size() > 0) {

        val company = new Company(stockCode, companyInfo.get(0).text(),
          companyInfo.get(1).text(), companyInfo.get(2).text(),
          companyInfo.get(3).text(), companyInfo.get(4).text(),
          companyInfo.get(5).text(), companyInfo.get(6).text(),
          companyInfo.get(7).text(), companyInfo.get(8).text(),
          companyInfo.get(9).text(), companyInfo.get(10).text(),
          companyInfo.get(11).text(), companyInfo.get(12).text(),
          companyInfo.get(13).text(), companyInfo.get(14).text(),
          companyInfo.get(15).text(), companyInfo.get(16).text(),
          companyInfo.get(17).text(), companyInfo.get(18).text(),
          companyInfo.get(19).text(), companyInfo.get(20).text(),
          companyInfo.get(21).text(), companyInfo.get(22).text(),
          companyInfo.get(23).text(), companyInfo.get(24).text(),
          companyInfo.get(25).text(), companyInfo.get(26).text(),
          companyInfo.get(27).text(), companyInfo.get(28).text(),
          companyInfo.get(29).text(), companyInfo.get(30).text(),
          companyInfo.get(31).text(), companyInfo.get(32).text())

        //println(company.toString)
        companyList.+=:(company)
      }


    } catch {
      case e: Exception =>
        println(url)
        e.printStackTrace()
    }


  }

  def insertData(lazyConn: LazyConnections, companyList: ListBuffer[Company]): Unit = {

    val mysqlCompanyConn = lazyConn.mysqlCompanyConn

    //清空表数据
    DBUtil.clearTable(mysqlCompanyConn, "company_profile")
    println("清空表数据完成。。。")

    val insertCompanySql = mysqlCompanyConn.prepareStatement("INSERT INTO company_profile (stock_code, company_name, " +
      "company_eng_name, used_name, " +
      "A_stockcode, A_short, " +
      "B_stockcode, B_short," +
      "H_stockcode, H_short," +
      "security_type, industry_involved," +
      "ceo, law_person," +
      "secretary, chairman," +
      "security_agent, independent_director," +
      "company_tel, company_email," +
      "company_fax, company_website," +
      "business_address, reg_address," +
      "area, post_code," +
      "reg_captial, business_registration," +
      "employee_num, admin_num," +
      "law_firm, accounting_firm," +
      "company_intro, business_scope) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")

    for (company <- companyList) {

      //插入数据
      DBUtil.insert(insertCompanySql, company.cStockCode, company.cCompanyName,
        company.cCompanyEngName, company.cUsedName,
        company.cAStockCode, company.cAShort,
        company.cBStockCode, company.cBShort,
        company.cHStockCode, company.cHShort,
        company.cSecurityType, company.cIndustryInvolved,
        company.cCEO, company.cLawPerson,
        company.cSecretary, company.cChairman,
        company.cSecurityAgent, company.cIndependentDirector,
        company.cCompanyTel, company.cCompanyEmail,
        company.cCompanyFax, company.cCompanyWebsite,
        company.cBusinessAddress, company.cRegAddress,
        company.cArea, company.cPostCode,
        company.cRegCapital, company.cBusinessRegistration,
        company.cEmployeeNum, company.cAdminNum,
        company.cLawFirm, company.cAccountingFirm,
        company.cCompanyIntro, company.cBusinessScope)

    }
    println("数据写入完成。。。。")

  }

}
