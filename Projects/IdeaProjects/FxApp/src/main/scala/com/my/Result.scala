package com.my

import scala.reflect.ClassTag
import scalafx.beans.property.StringProperty
import scalafx.scene.control.TableColumn

class Result(_env1TableName: String,_env2TableName: String ,_env1:String, _env2:String,_tableType:String, _result:String)
  {
    val env1TableName = new StringProperty(this, "Env1 TableName", _env1TableName)
    val env2TableName = new StringProperty(this, "Env2 TableName", _env2TableName)
    val env1 = new StringProperty(this, "Env1", _env1)
    val env2 = new StringProperty(this, "Env2", _env2)
    val result = new StringProperty(this, "Result", _result)
    val tableType = new StringProperty(this,"TableType",_tableType)



  }
