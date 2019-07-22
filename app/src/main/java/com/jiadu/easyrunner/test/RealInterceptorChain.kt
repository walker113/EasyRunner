package com.jiadu.easyrunner.test


class RealInterceptorChain(val interceptors: List<Interceptor>, val index : Int) : Interceptor.Chain {

    override fun proceed() {

        val next = RealInterceptorChain(interceptors, index + 1)
        val interceptor = interceptors[index]
        interceptor.intercept(next)


    }

}