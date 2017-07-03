package com.kunyan.util

/**
  * Created by lcm on 2017/4/12.
  * 字符串处理工具
  */
object StringUtil {

  def arrToStr(data:Array[Array[String]]):String={

    var str = "["

    for(lineArr <- data ){

      str = str + "["

      for(field <- lineArr){
        str = str + "\"" + field + "\","
      }

      str = str.substring(0,str.length-1) + "],"
    }

    "{\"result\":" + str.substring(0,str.length-1) + "]}"
  }

 }
