package com.xing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

    fun main(args: Array<String>){
        runApplication<Application>(*args)
        val users: List<Int>? = null
        infix fun Int.times(str: String) = str.repeat(this)
        val str = "asasf"
        2 times str
    }

