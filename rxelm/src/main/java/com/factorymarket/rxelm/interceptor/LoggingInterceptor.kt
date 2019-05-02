package com.factorymarket.rxelm.interceptor

import com.factorymarket.rxelm.cmd.Cmd
import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.msg.Msg

abstract class LoggingInterceptor : RxElmInterceptor {

    abstract fun log(tag: String = "RxElm", message: String, error: Throwable? = null)

//    override fun onEvent(eventType: RxElmInterceptor.EventType) {
//    }

    override fun onInit(state: State) {
        log(message = "onInit - state: $state")
    }

    override fun onMsgReceived(msg: Msg, state: State) {
        log(message = "onMsgReceived - msg: $msg, state: $state")
    }

    override fun onUpdate(msg: Msg, cmd: Cmd, newState: State?, state: State) {
        log(message = "onUpdate - msg: $msg, cmd: $cmd, newState: $newState, state: $state")
    }

    override fun onRender(state: State) {
        log(message = "onRender - state: $state")
    }

    override fun onCmdReceived(cmd: Cmd, state: State) {
        log(message = "onCmdReceived - cmd: $cmd, state: $state")
    }

    override fun onCmdSuccess(cmd: Cmd, resultMsg: Msg, state: State) {
        log(message = "onCmdSuccess - cmd: $cmd, resultMsg: $resultMsg, state: $state")
    }

    override fun onCmdError(cmd: Cmd, error: Throwable, state: State) {
        log(message = "onCmdError - cmd: $cmd, state: $state", error = error)
    }

    override fun onCmdCancelled(cmd: Cmd, state: State) {
        log(message = "onCmdCancelled - cmd: $cmd, state: $state")
    }

    override fun onStop(state: State) {
        log(message = "onStop - state: $state")
    }
}
