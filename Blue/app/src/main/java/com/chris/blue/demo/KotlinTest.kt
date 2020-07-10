package com.chris.blue.demo

import android.content.Context
import android.util.Log
import android.widget.LinearLayout
import kotlin.math.log

//顶层声明
val address = "BeiJing"
fun topFun() = println(address)


class KotlinTest private constructor(){

    // a ?: b if a!=null 执行a if a==null 执行b

    //
    var count = 1

    //Kotlin 沒有默認值
    lateinit var text : String ;

    // 可以为空
    var name : String? = null

    val strList = arrayOf("1","2","3")

    companion object{

        // const 常量
        const val age : Int = 30

        val instance : KotlinTest by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            KotlinTest()
        }
    }

    fun test1(context : Context) : Int{

        var linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
        }

        Log.d("1111", "test1: ")
        text.length +age
        address.length
        return 2
    }

    fun test2(){

        // 如果空跳过
        name?.length

        // 如果空拋异常
        name!!.length

        topFun()
    }

    /**
     * run:扩展函数,with：普通函数
     */
    fun test3(){
        with(address){
            println("length is {${this.length}")
        }

        // 使用之前可以检查它的可空性
        name?.run {

        }

        name?.let {
            println("length is {${it.length}}")
        }

        text.also {
            count++
            "yuwen"
        }
        print("text is : ${text}")
    }

}