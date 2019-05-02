package com.factorymarket.rxelm.interceptor

import com.factorymarket.rxelm.cmd.Cmd
import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.msg.Msg

/**
 * Created by Michael Zhukov on 02.05.2019.
 * email: zhukovme@gmail.com
 */
interface RxElmInterceptor {
    //    fun onEvent(eventType: EventType)

    fun onInit(state: State)
    fun onMsgReceived(msg: Msg, state: State)
    fun onUpdate(msg: Msg, cmd: Cmd, newState: State?, state: State)
    fun onRender(state: State)
    fun onCmdReceived(cmd: Cmd, state: State)
    fun onCmdSuccess(cmd: Cmd, resultMsg: Msg, state: State)
    fun onCmdError(cmd: Cmd, error: Throwable, state: State)
    fun onCmdCancelled(cmd: Cmd, state: State)
    fun onStop(state: State)

//    sealed class EventType {
//        data class OnInit(val state: State) : EventType()
//
//        data class OnMsgReceived(val msg: Msg, val state: State) : EventType()
//
//        data class OnUpdate(val msg: Msg,
//                            val newState: State?,
//                            val cmd: Cmd, val state: State
//        ) : EventType()
//
//        data class OnRender(val state: State) : EventType()
//
//        data class OnCmdReceived(val cmd: Cmd, val state: State) : EventType()
//
//        data class OnCmdSuccess(val cmd: Cmd, val resultMsg: Msg, val state: State) : EventType()
//
//        data class OnCmdError(val cmd: Cmd, val error: Throwable, val state: State) : EventType()
//
//        data class OnCmdDisposed(val cmd: Cmd, val state: State) : EventType()
//
//        data class OnStop(val state: State) : EventType()
//    }
}
