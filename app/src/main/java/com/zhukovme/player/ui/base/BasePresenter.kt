package com.zhukovme.player.ui.base

import android.os.Bundle
import android.os.Parcelable
import com.zhukovme.rxelm.program.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Created by Michael Zhukov on 30.04.2019.
 * email: zhukovme@gmail.com
 */
abstract class BasePresenter<S>(
        programBuilder: ProgramBuilder
) : RenderableComponent<S> where S : State, S : Parcelable {

    companion object {
        private const val BUNDLE_STATE = "BUNDLE_STATE"
    }

    protected var view: MvpView<S>? = null

    private val program: Program<S> = programBuilder.build(this)

    //region From view

    open fun onAttachView(view: MvpView<S>) {
        this.view = view
    }

    open fun onCreate(savedInstanceState: Bundle?) {
        val state = savedInstanceState?.getParcelable(BUNDLE_STATE) ?: setupInitialState()
        if (program.isRunning) program.render(state)
        else program.run(state)
    }

    open fun dispatch(msg: Msg) {
        program.accept(msg)
    }

    open fun onSaveState(outState: Bundle?) {
        outState?.putParcelable(BUNDLE_STATE, program.getState())
    }

    open fun onDetachView() {
        view = null
    }

    open fun onDestroy(isFinishing: Boolean) {
        if (isFinishing) program.stop()
    }

    //endregion

    protected abstract fun setupInitialState(): S

    protected fun <T : Msg> subscribeTo(observable: Observable<T>): Disposable {
        return program.addEventObservable(observable)
    }

    override fun render(state: S) {
        view?.render(state)
    }

    override fun update(msg: Msg, state: S): Update<S> {
        return when (msg) {
            is ErrorMsg -> handleError(msg, state)
            else -> Update.idle()
        }
    }

    protected open fun handleError(msg: ErrorMsg, state: S): Update<S> {
        return Update.idle()
    }
}
