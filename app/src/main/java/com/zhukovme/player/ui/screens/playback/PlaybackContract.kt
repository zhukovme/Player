package com.zhukovme.player.ui.screens.playback

import com.zhukovme.player.ui.base.Action
import com.zhukovme.player.ui.base.MvpView
import com.zhukovme.player.ui.base.State

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
data class PlaybackState(val title: String = "",
                         val subtitle: String = "",
                         val shuffleMode: Boolean = false,
                         val repeatMode: RepeatMode = RepeatMode.Off,
                         val trackNumber: String = "",
                         val trackCover: Int = 0,
                         val progress: Int = 0,
                         val progressMax: Int = 0,
                         val isPlaying: Boolean = false,

                         val snackbarMessage: String? = null) : State()

sealed class RepeatMode {
    abstract val next: RepeatMode

    object Off : RepeatMode() {
        override val next = Repeat
    }

    object Repeat : RepeatMode() {
        override val next = RepeatOnce
    }

    object RepeatOnce : RepeatMode() {
        override val next = Off
    }
}

object ShuffleAction : Action()
object RepeatAction : Action()
class ChangeProgressAction(val progress: Int) : Action()
object PreviousTrackAction : Action()
object NextTrackAction : Action()
object PlayPauseAction : Action()

interface PlaybackView : MvpView {
    fun renderState(playbackState: PlaybackState)

    fun showSnackbar(message: String)
}
