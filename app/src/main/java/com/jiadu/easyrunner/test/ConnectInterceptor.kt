package com.jiadu.easyrunner.test

class ConnectInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain) {
        println("Interceptor --- ConnectInterceptor")
    }

}