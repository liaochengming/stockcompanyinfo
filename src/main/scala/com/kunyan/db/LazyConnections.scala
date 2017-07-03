package com.kunyan.db

import java.sql.{DriverManager, PreparedStatement, Connection}

import scala.xml.Elem

/**
 * Created by lcm on 2017/3/14.
 * 连接控制
 */
class LazyConnections (createMySQLConnection: () => Connection,
                       createMySQLFSHPS: () => PreparedStatement,
                       createMySQLSHPS: () => PreparedStatement,
                       createCompany: () => Connection) extends Serializable{


  lazy val mysqlConn = createMySQLConnection()

  lazy val floatStockHolderPreparedStatement = createMySQLFSHPS()

  lazy val stockHolderPreparedStatement = createMySQLSHPS()

  lazy val mysqlCompanyConn = createCompany()

}

object LazyConnections {
  def apply(configFile: Elem): LazyConnections = {


    //连接股票代码表
    val createMySQLConnection = () => {

      Class.forName("com.mysql.jdbc.Driver")
      val connection = DriverManager.getConnection((configFile \ "mysqlStock" \ "url").text, (configFile \ "mysqlStock" \ "username").text, (configFile \ "mysqlStock" \ "password").text)

      sys.addShutdownHook {
        connection.close()
      }

      connection
    }

    val createFSHPs = () => {

      Class.forName("com.mysql.jdbc.Driver")
      val connection = DriverManager.getConnection((configFile \ "mysqlCompany" \ "url").text, (configFile \ "mysqlCompany" \ "username").text, (configFile \ "mysqlCompany" \ "password").text)

      sys.addShutdownHook {
        connection.close()
      }

      connection.prepareStatement("INSERT INTO top10_float_stockholder (stock_code, date, rank, stockholder_name, stockholder_nature, share_type, shares_number,total_ratio,change_share,change_ratio) VALUES (?,?,?,?,?,?,?,?,?,?)")
    }

    val createSHPs = () => {

      Class.forName("com.mysql.jdbc.Driver")
      val connection = DriverManager.getConnection((configFile \ "mysqlCompany" \ "url").text, (configFile \ "mysqlCompany" \ "username").text, (configFile \ "mysqlCompany" \ "password").text)

      sys.addShutdownHook {
        connection.close()
      }

      connection.prepareStatement("INSERT INTO top10_stockholder (stock_code, date, rank, stockholder_name, share_type, shares_number,total_ratio,change_share,change_ratio) VALUES (?,?,?,?,?,?,?,?,?)")
    }

    val createCompany = () => {

      Class.forName("com.mysql.jdbc.Driver")
      val connection = DriverManager.getConnection((configFile \ "mysqlCompany" \ "url").text, (configFile \ "mysqlCompany" \ "username").text, (configFile \ "mysqlCompany" \ "password").text)

      sys.addShutdownHook {
        connection.close()
      }
      connection
    }
    new LazyConnections(createMySQLConnection,createFSHPs,createSHPs,createCompany)
  }


}