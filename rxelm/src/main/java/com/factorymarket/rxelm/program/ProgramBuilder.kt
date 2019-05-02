package com.factorymarket.rxelm.program

import com.factorymarket.rxelm.contract.Component
import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.interceptor.RxElmInterceptor
import com.factorymarket.rxelm.interceptor.RxElmInterceptorComposer
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ProgramBuilder {

    private var msgScheduler: Scheduler = Schedulers.io()
    private var cmdScheduler: Scheduler = Schedulers.io()
    private val interceptors = RxElmInterceptorComposer()

    fun msgScheduler(scheduler: Scheduler): ProgramBuilder {
        this.msgScheduler = scheduler
        return this
    }

    fun cmdScheduler(scheduler: Scheduler): ProgramBuilder {
        this.cmdScheduler = scheduler
        return this
    }

    fun interceptor(interceptor: RxElmInterceptor): ProgramBuilder {
        interceptors.add(interceptor)
        return this
    }

    fun <S : State> build(component: Component<S>): Program<S> {
        return Program(msgScheduler, cmdScheduler, interceptors, component)
    }
}
