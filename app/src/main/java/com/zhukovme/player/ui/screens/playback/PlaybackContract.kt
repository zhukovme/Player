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
data class PlaybackState(
        val title: String,
        val subtitle: String?,
        val shuffleMode: Boolean,
        val shuffleIcon: Int,
        val repeatMode: RepeatMode,
        val repeatIcon: Int,
        val trackNumber: String,
        val trackCover: Int,
        val progress: Int,
        val progressMax: Int,
        val progressTime: String,
        val restTime: String,
        val isPlaying: Boolean,
        val playPauseIcon: Int
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
