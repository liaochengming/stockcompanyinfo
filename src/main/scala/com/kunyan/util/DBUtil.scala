package com.kunyan.util

import java.sql.{Connection, PreparedStatement}


/**
 * Created by lcm on 2017/3/14.
 * 数据库的操作工具类
 */
object DBUtil {

  
  //插入数据库操作
  def insert(prep: PreparedStatement, params: Any*): Boolean = {

    try {

      for (i <- params.indices) {

        val param = params(i)

        param match {

          case param: String =>
            prep.setString(i + 1, param)
          case param: Int =>
            prep.setInt(i + 1, param)
          case param: Boolean =>
            prep.setBoolean(i + 1, param)
          case param: Long =>
            prep.setLong(i + 1, param)
          case param: Double =>
            prep.setDouble(i + 1, param)
          case _ =>

        }
      }
      prep.executeUpdate

      true
    } catch {

      case e: Exception =>
        false
    }
  }

  //清空表数据
  def clearTable(companyConn: Connection, tableName: String): Unit ={

    val selSql = companyConn.prepareStatement("select id from " + tableName)
    val selRes = selSql.executeQuery()

    val delSql = companyConn.prepareStatement("delete from " + tableName + " where id=?")

    while (selRes.next()){
      delSql.setInt(1,selRes.getInt(1))
      delSql.addBatch()
    }

    delSql.executeBatch()

    selSql.close()
    delSql.close()
  }

}
