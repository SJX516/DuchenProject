package com.duchen.template.usage.Kotlin

import android.app.Application
import android.util.Log
import android.widget.Toast

class TestGramer{

    val i3 = 12 // An Int
    val iHex = 0x0f // 一个十六进制的Int类型
    val l = 3L // A Long
    val d2 = 3.5 // A Double
    val f = 3.5F // A Float

    var i:Int = 7
    //所有类型都是对象,不同对象需要转换
    var d:Double = i.toDouble()

    var c:Char = 'c'
    var i2 = c.toInt()

    //位运算的关键字与 java 不同
    var flag = true and false
    var flag2 = true or false

    init {
        logE("123")
    }

    fun logE(message : String , data : String = "123" , length : Int = Toast.LENGTH_SHORT){
        Log.e( "$message" , "data is $data length is $length")
    }

    fun add(x: Int, y: Int): Int = x + y

    fun add2(x: Int, y: Int): Int{
        return x + y
    }
}