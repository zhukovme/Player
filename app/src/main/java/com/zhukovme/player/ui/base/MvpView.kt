package com.zhukovme.player.ui.base

import androidx.annotation.StringRes
import com.factorymarket.rxelm.contract.State

/**
 * Created by Michael Zhukov on 28.04.2019.
 * email: zhukovme@gmail.com
 */
interface MvpView<T : State> {

    fun render(state: T)

    fun showSnackbar(@StringRes message: Int)

    fun showSnackbar(message: CharSequence)

    fun showToast(@StringRes message: Int)

    fun showToast(message: CharSequence)
}
