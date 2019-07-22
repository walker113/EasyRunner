package com.jiadu.easyrunner.test

class RetryAndFollowUpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) {
        println("Interceptor --- RetryAndFollowUpInterceptor")
        (chain as RealInterceptorChain).proceed()
    }

}