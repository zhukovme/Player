package com.zhukovme.player.ui.base

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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

    var state: State? = null
        private set

    private val actionRelay: BehaviorRelay<Pair<Action, State>> = BehaviorRelay.create()
    private var actionDisposable: Disposable? = null
    private var actionQueue = ArrayDeque<Action>()
    private var component: Component? = null

    fun dispatch(action: Action) {
        Timber.d("elm dispatch event:${action.javaClass.simpleName}")
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
                    Timber.d("elm reduce action:$action ")
                    component.reduce(state, action)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { (state, _) ->
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
                .observeOn(Schedulers.io())
                .flatMap { (state, cmd) ->
                    Timber.d("call cmd:$cmd state:$state ")
                    return@flatMap component.call(cmd)
                            .onErrorResumeNext { err -> Single.just(ErrorAction(err, cmd)) }
                            .toObservable()

                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { action ->
                    Timber.d("elm subscribe action:${action.javaClass.simpleName}")
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
}
