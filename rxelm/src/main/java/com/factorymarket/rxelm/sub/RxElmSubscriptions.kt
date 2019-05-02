package com.factorymarket.rxelm.sub

import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.msg.Msg
import com.factorymarket.rxelm.program.Program
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * Container for storing a collection of [Observables][Observable]
 * and [Disposables][io.reactivex.disposables.Disposable]
 *
 * Initialized with a set of [Observable][Observable] or [Observables][Observable] with Predicates
 *
 * [Program] during it's work calls [subscribe] on every message update, which
 * make all unconditional subscriptions and suitable conditional subscriptions to subscribe
 * and pass their return to [Program.accept]
 */
class RxElmSubscriptions<S : State> {

    private val subs: Queue<Observable<out Msg>> = LinkedList<Observable<out Msg>>()
    private val conditionalSubs: Queue<Pair<(S) -> Boolean, Observable<out Msg>>> =
            LinkedList<Pair<(S) -> Boolean, Observable<out Msg>>>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Adds <[Msg]> observable to collection which result will be passed
     * to [Program's accept(Message)][Program.accept] method, but will be ignored
     * if Predicate will fail on first check
     */
    fun addObservable(observable: Observable<out Msg>,
                      predicate: ((S) -> Boolean)? = null): RxElmSubscriptions<S> {
        predicate?.let { conditionalSubs.add(it to observable) }
                ?: run { subs.add(observable) }
        return this
    }

    /**
     * Subscribe all data sources to [Program.accept(Message)][Program.accept]
     * Checks if conditional data sources' predicate is true.
     * If it is, subscribes them if not - just delete.
     */
    fun subscribe(program: Program<S>, state: S) {
        mergeSubs(state)

        if (subs.isEmpty()) {
            return
        }
        var sub = subs.poll()
        while (sub != null) {
            val disposable = sub
                    .observeOn(program.msgScheduler)
                    .subscribe { msg ->
                        program.accept(msg)
                    }
            compositeDisposable.add(disposable)
            sub = subs.poll()
        }
    }

    /**
     * Dispose all subscriptions
     */
    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun mergeSubs(state: S) {
        if (conditionalSubs.isEmpty()) {
            return
        }

        val iter = conditionalSubs.iterator()
        while (iter.hasNext()) {
            val delayedSub = iter.next()
            if (delayedSub.first.invoke(state)) {
                iter.remove()
                subs.add(delayedSub.second)
            }
        }
    }
}
