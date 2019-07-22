package com.jiadu.easyrunner.test

interface Interceptor {

    fun intercept(chain: Chain)


    interface Chain {
        fun proceed();
    }
}