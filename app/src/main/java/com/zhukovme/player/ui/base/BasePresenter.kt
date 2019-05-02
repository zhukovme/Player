package com.zhukovme.player.ui.base

import android.os.Bundle
import android.os.Parcelable
import com.factorymarket.rxelm.contract.RenderableComponent
import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.contract.Update
import com.factorymarket.rxelm.msg.ErrorMsg
import com.factorymarket.rxelm.msg.Msg
import com.factorymarket.rxelm.program.Program
import com.factorymarket.rxelm.program.ProgramBuilder
import com.factorymarket.rxelm.sub.RxElmSubscriptions

/**
 * Created by Michael Zhukov on 30.04.2019.
 * email: zhukovme@gmail.com
 */
abstract class BasePresenter<T>(
        programBuilder: ProgramBuilder
) : RenderableComponent<T> where T : State, T : Parcelable {

    companion object {
        private const val BUNDLE_STATE = "BUNDLE_STATE"
    }

    protected var view: MvpView<T>? = null

    private val program: Program<T> = programBuilder.build(this)

    open fun onAttachView(view: MvpView<T>) {
        this.view = view
    }

    open fun onCreate(savedInstanceState: Bundle?) {
        val state = savedInstanceState?.getParcelable(BUNDLE_STATE) ?: setupInitialState()
        if (program.isRunning) program.render(state)
        else program.run(state, setupSubscriptions())
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

    protected abstract fun setupInitialState(): T

    protected open fun setupSubscriptions(): RxElmSubscriptions<T>? = null

    override fun render(state: T) {
        view?.render(state)
    }

    override fun update(msg: Msg, state: T): Update<T> {
        return when (msg) {
            is ErrorMsg -> handleError(msg, state)
            else -> Update.idle()
        }
    }

    protected open fun handleError(msg: ErrorMsg, state: T): Update<T> {
        return Update.idle()
    }
}
