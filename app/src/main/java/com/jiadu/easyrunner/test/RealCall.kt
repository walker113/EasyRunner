package com.jiadu.easyrunner.test

class RealCall {

    fun getResponseWithInterceptorChain() {
        val interceptors = ArrayList<Interceptor>()

        interceptors.add(RetryAndFollowUpInterceptor())
        interceptors.add(ConnectInterceptor())


        val chain = RealInterceptorChain(interceptors, 0)
        chain.proceed()
    }
}