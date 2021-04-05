package com.my

import java.io.File

object Sample {

  def main(args: Array[String]): Unit = {

    println(System.getProperty("user.home"))
    val path = new File(System.getProperty("user.home"))
    val file = new File(path+ File.separator + "config.properties")
    println(file.getAbsolutePath)
    println (file.exists())
  }
}
