package com.zhukovme.player.ui.screens.playback

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import com.zhukovme.player.R
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
data class PlaybackVm(val title: String = "Init title",
                      val subtitle: String = "Init subtitle",
                      val shuffleMode: Boolean = false,
                      val shuffleIcon: Int = 0,
                      val repeatMode: RepeatMode = RepeatMode.Off,
                      val repeatIcon: Int = 0,
                      val trackNumber: String = "0/0",
                      val trackCover: Int = 0,
                      val progress: Int = 0,
                      val progressMax: Int = 0,
                      val progressTime: String = "",
                      val restTime: String = "",
                      val isPlaying: Boolean = false,
                      val playIcon: Int = 0,
                      val snackbarMessage: String? = null)

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

sealed class UiEvent {
    object OnShuffleCLick : UiEvent()
    object OnRepeatClick : UiEvent()
    object OnPlayPauseClick : UiEvent()
    object OnNextTrackClick : UiEvent()
    object OnPreviousTrackClick : UiEvent()
    data class OnProgressChanged(val progress: Int) : UiEvent()
}

class PlaybackBindings(view: PlaybackActivity,
                       private val playbackFeature: PlaybackFeature
) : AndroidBindings<PlaybackActivity>(view) {
    override fun setup(view: PlaybackActivity) {
        binder.bind(view to playbackFeature using UiEventToWish())
        binder.bind(playbackFeature to view using StateToVm())
    }
}

class UiEventToWish : (UiEvent) -> PlaybackFeature.Wish? {
    override fun invoke(event: UiEvent): PlaybackFeature.Wish? = when (event) {
        is UiEvent.OnShuffleCLick -> PlaybackFeature.Wish.Shuffle
        is UiEvent.OnRepeatClick -> PlaybackFeature.Wish.Repeat
        is UiEvent.OnPlayPauseClick -> PlaybackFeature.Wish.PlayPause
        is UiEvent.OnNextTrackClick -> PlaybackFeature.Wish.NextTrack
        is UiEvent.OnPreviousTrackClick -> PlaybackFeature.Wish.PreviousTrack
        is UiEvent.OnProgressChanged -> PlaybackFeature.Wish.ChangeProgress(event.progress)
    }
}

class StateToVm : (PlaybackFeature.State) -> PlaybackVm? {
    override fun invoke(state: PlaybackFeature.State): PlaybackVm? {
        return PlaybackVm(
                shuffleMode = state.shuffleMode,
                shuffleIcon = getShuffleIcon(state.shuffleMode),
                repeatMode = state.repeatMode,
                repeatIcon = getRepeatIcon(state.repeatMode),
                progress = state.progress,
                progressMax = state.progressMax,
                progressTime = getProgressTime(state.progress),
                restTime = getRestTime(state.progress, state.progressMax),
                isPlaying = state.isPlaying,
                playIcon = getPlayIcon(state.isPlaying)
        )
    }

    private fun getShuffleIcon(shuffleMode: Boolean?): Int {
        return if (shuffleMode == true) R.drawable.shuffle_accent else R.drawable.shuffle_white
    }

    private fun getRepeatIcon(repeatMode: RepeatMode?): Int {
        return when (repeatMode) {
            is RepeatMode.Off -> R.drawable.repeat_white
            is RepeatMode.Repeat -> R.drawable.repeat_accent
            is RepeatMode.RepeatOnce -> R.drawable.repeat_once_accent
            else -> R.drawable.repeat_white
        }
    }

    private fun getProgressTime(progress: Int?): String =
            if (progress == null) "00:00"
            else formatSeconds(progress.toLong())

    private fun getRestTime(progress: Int?, progressMax: Int?): String =
            if (progress == null || progressMax == null) "-00:00"
            else "-${formatSeconds((progressMax - progress).toLong())}"

    private fun formatSeconds(seconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(seconds)
        val min = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours)
        val sec = seconds - TimeUnit.MINUTES.toSeconds(min)
        return if (hours == 0L) String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        else String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, min, sec)
    }

    private fun getPlayIcon(isPlaying: Boolean): Int {
        return if (isPlaying) R.drawable.play else R.drawable.pause
    }
}
