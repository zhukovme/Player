package com.zhukovme.player.ui.screens.playback

import androidx.annotation.StringRes

/**
 * Created by Michael Zhukov on 28.04.2019.
 * email: zhukovme@gmail.com
 */
interface MvpView {

    fun render(state: PlaybackState)

    fun showSnackbar(@StringRes message: Int)

    fun showSnackbar(message: CharSequence)

    fun showToast(@StringRes message: Int)

    fun showToast(message: CharSequence)
}
