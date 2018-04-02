package com.zhukovme.player.ui.base

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.*

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
sealed class AbstractState

open class State : AbstractState()

sealed class AbstractAction
open class Action : AbstractAction()
object Idle : Action()
object Init : Action()
class ErrorAction(val err: Throwable, val cmd: Command) : Action()


sealed class AbstractCommand
open class Command : AbstractCommand()
object None : Command()

interface Component {
    fun reduce(state: State, action: Action): Pair<State, Command>
    fun render(state: State)
    fun call(cmd: Command): Single<Action>
}

class Store {

    companion object {
        fun init(initialState: State, component: Component): Store {
            val store = Store()
            store.create(initialState, component)
            return store
        }
    }

    var enableLogs = false
    var state: State? = null
        private set

    private val actionRelay: BehaviorRelay<Pair<Action, State>> = BehaviorRelay.create()
    private var actionDisposable: Disposable? = null
    private var actionQueue = ArrayDeque<Action>()
    private var component: Component? = null

    fun dispatch(action: Action) {
        log("---------- Dispatch action '${action.javaClass.simpleName}'")
        actionQueue.addLast(action)
        if (actionQueue.size == 1) {
            state?.let { actionRelay.accept(Pair(actionQueue.first, it)) }
        }
    }

    fun render() {
        state?.let { component?.render(it) }
    }

    fun terminate() {
        actionDisposable?.dispose()
    }

    private fun create(initialState: State, component: Component) {
        this.component = component
        this.state = initialState
        actionDisposable = actionRelay
                .map { (action, state) ->
                    log("---------- Reduce action '${action.javaClass.simpleName}' with prev state '$state'")
                    component.reduce(state, action)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { (state, _) ->
                    log("---------- Render new state '$state'")
                    component.render(state)
                }
                .doOnNext { (state, _) ->
                    this.state = state
                    if (actionQueue.size > 0) {
                        actionQueue.removeFirst()
                    }
                    loop()
                }
                .filter { (_, cmd) -> cmd !== None }
                .flatMap { (state, cmd) ->
                    log("---------- Start command '${cmd.javaClass.simpleName}' with state '$state'")
                    return@flatMap component.call(cmd)
                            .doOnSuccess { log("---------- Command: '${cmd.javaClass.simpleName}' finished") }
                            .doOnError { t -> log("---------- Error on command: '${cmd.javaClass.simpleName}", t) }
                            .onErrorResumeNext { throwable -> Single.just(ErrorAction(throwable, cmd)) }
                            .toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { action ->
                    log("---------- Elm subscribe action '${action.javaClass.simpleName}'")
                    when (action) {
                        is Idle -> {
                        }
                        else -> actionQueue.addLast(action)
                    }
                    loop()
                }
    }

    private fun loop() {
        if (actionQueue.size > 0) {
            state?.let { actionRelay.accept(Pair(actionQueue.first, it)) }
        }
    }

    private fun log(message: String, throwable: Throwable? = null) {
        if (enableLogs) Timber.d(throwable, "$message%s", ". Component '${component?.javaClass?.simpleName}'")
    }
}
