package com.zhukovme.player.ui.screens.playback

import com.zhukovme.player.ui.base.State

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
data class PlaybackState(val title: String = "",
                         val subtitle: String = "") : State()

interface PlaybackView {
    fun renderState(playbackState: PlaybackState)
}
