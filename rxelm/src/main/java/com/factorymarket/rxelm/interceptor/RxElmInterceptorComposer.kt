package com.factorymarket.rxelm.interceptor

import com.factorymarket.rxelm.cmd.Cmd
import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.msg.Msg

/**
 * Created by Michael Zhukov on 02.05.2019.
 * email: zhukovme@gmail.com
 */
class RxElmInterceptorComposer : RxElmInterceptor {

    private val interceptors: MutableSet<RxElmInterceptor> = HashSet()

    fun add(interceptor: RxElmInterceptor) {
        interceptors.add(interceptor)
    }

//    override fun onEvent(eventType: RxElmInterceptor.EventType) {
//        interceptors.forEach { it.onEvent(eventType) }
//    }

    override fun onInit(state: State) {
        interceptors.forEach { it.onInit(state) }
    }

    override fun onMsgReceived(msg: Msg, state: State) {
        interceptors.forEach { it.onMsgReceived(msg, state) }
    }

    override fun onUpdate(msg: Msg, cmd: Cmd, newState: State?, state: State) {
        interceptors.forEach { it.onUpdate(msg, cmd, newState, state) }
    }

    override fun onRender(state: State) {
        interceptors.forEach { it.onRender(state) }
    }

    override fun onCmdReceived(cmd: Cmd, state: State) {
        interceptors.forEach { it.onCmdReceived(cmd, state) }
    }

    override fun onCmdSuccess(cmd: Cmd, resultMsg: Msg, state: State) {
        interceptors.forEach { it.onCmdSuccess(cmd, resultMsg, state) }
    }

    override fun onCmdError(cmd: Cmd, error: Throwable, state: State) {
        interceptors.forEach { it.onCmdError(cmd, error, state) }
    }

    override fun onCmdCancelled(cmd: Cmd, state: State) {
        interceptors.forEach { it.onCmdCancelled(cmd, state) }
    }

    override fun onStop(state: State) {
        interceptors.forEach { it.onStop(state) }
    }
}
