package com.zhukovme.player.ui.screens.playback

import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.msg.Msg

/**
 * Created by Michael Zhukov on 28.04.2019.
 * email: zhukovme@gmail.com
 */
data class PlaybackState(val title: String = "",
                         val subtitle: String = "",
                         val shuffleMode: Boolean = false,
                         val shuffleIcon: Int = 0,
                         val repeatMode: RepeatMode = RepeatMode.Off,
                         val repeatIcon: Int = 0,
                         val trackNumber: String = "",
                         val trackCover: Int = 0,
                         val progress: Int = 0,
                         val progressMax: Int = 0,
                         val progressTime: String = "",
                         val restTime: String = "",
                         val isPlaying: Boolean = false,
                         val playPauseIcon: Int = 0,
                         val snackbarMessage: String? = null
) : State()

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

class OnShuffleClick : Msg()
class OnRepeatClick : Msg()
class OnPlayPauseClick : Msg()
class OnNextTrackClick : Msg()
class OnPreviousTrackClick : Msg()
data class OnProgressChanged(val progress: Int) : Msg()
