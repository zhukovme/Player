package com.zhukovme.player.ui.screens.playback

import android.os.Parcelable
import com.zhukovme.rxelm.program.Msg
import com.zhukovme.rxelm.program.State
import kotlinx.android.parcel.Parcelize

/**
 * Created by Michael Zhukov on 28.04.2019.
 * email: zhukovme@gmail.com
 */
@Parcelize
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
                         val playPauseIcon: Int = 0
) : State(), Parcelable

enum class RepeatMode {
    Off,
    Repeat,
    RepeatOnce;

    fun next(): RepeatMode {
        return when (this) {
            Off -> Repeat
            Repeat -> RepeatOnce
            RepeatOnce -> Off
        }
    }
}

object OnShuffleClick : Msg()
object OnRepeatClick : Msg()
object OnPlayPauseClick : Msg()
object OnNextTrackClick : Msg()
object OnPreviousTrackClick : Msg()
data class OnProgressChanged(val progress: Int) : Msg()
