package com.factorymarket.rxelm.cmd

import io.reactivex.Scheduler
import kotlin.reflect.KClass

sealed class AbstractCmd {
    override fun toString(): String {
        return this.javaClass.simpleName
    }
}
open class Cmd(val cmdScheduler: Scheduler? = null) : AbstractCmd()
open class SwitchCmd : Cmd()
data class CancelCmd(val cancelCmd: Cmd) : Cmd()
data class CancelByClassCmd<T : Cmd>(val cmdClass: KClass<T>) : Cmd()
object None : Cmd()

data class BatchCmd(val cmds: MutableSet<Cmd>) : Cmd() {
    constructor(vararg commands: Cmd) : this(commands.toMutableSet())

    fun merge(cmd: Cmd): BatchCmd {
        when (cmd) {
            is BatchCmd -> cmds.addAll(cmd.cmds)
            is None -> {
            }
            else -> cmds.add(cmd)
        }
        return this
    }
}
