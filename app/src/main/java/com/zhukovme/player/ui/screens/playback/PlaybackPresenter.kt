package com.zhukovme.player.ui.screens.playback

import com.zhukovme.player.R
import com.zhukovme.player.ui.base.BasePresenter
import com.zhukovme.rxelm.program.*
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackPresenter(
        programBuilder: ProgramBuilder
) : BasePresenter<PlaybackState>(programBuilder) {

    @Suppress("MagicNumber")
    override fun setupInitialState(): PlaybackState {
        return PlaybackState(
                title = "Init title",
                subtitle = "Init subtitle",
                shuffleMode = false,
                shuffleIcon = getShuffleIcon(false),
                repeatMode = RepeatMode.Off,
                repeatIcon = getRepeatIcon(RepeatMode.Off),
                trackNumber = "0/0",
                trackCover = 0,
                progress = 0,
                progressMax = 0,
                progressTime = getProgressTime(0),
                restTime = getRestTime(0, 0),
                isPlaying = false,
                playPauseIcon = getPlayPauseIcon(false)
        )
    }

    override fun update(msg: Msg, state: PlaybackState): Update<PlaybackState> {
        return when (msg) {
            is OnShuffleClick -> Update(state.copy(
                    shuffleMode = !state.shuffleMode,
                    shuffleIcon = getShuffleIcon(!state.shuffleMode)
            ))
            is OnRepeatClick -> Update(state.copy(
                    repeatMode = state.repeatMode.next(),
                    repeatIcon = getRepeatIcon(state.repeatMode.next())
            ))
            is OnPlayPauseClick -> Update(state.copy(
                    isPlaying = !state.isPlaying,
                    playPauseIcon = getPlayPauseIcon(!state.isPlaying)
            ))
            is OnNextTrackClick -> Update.idle()
            is OnPreviousTrackClick -> Update.idle()
            is OnProgressChanged -> Update(state.copy(
                    progress = msg.progress,
                    progressTime = getProgressTime(msg.progress),
                    restTime = getRestTime(msg.progress, state.progressMax)
            ))
            else -> super.update(msg, state)
        }
    }

    override fun call(cmd: Cmd): Single<Msg> {
        return Single.just(Idle)
    }

    override fun handleError(msg: ErrorMsg, state: PlaybackState): Update<PlaybackState> {
        view?.showSnackbar("Error")
        return super.handleError(msg, state)
    }

    private fun getShuffleIcon(shuffleMode: Boolean?): Int {
        return if (shuffleMode == true) R.drawable.shuffle_accent else R.drawable.shuffle
    }

    private fun getRepeatIcon(repeatMode: RepeatMode?): Int {
        return when (repeatMode) {
            RepeatMode.Off -> R.drawable.repeat
            RepeatMode.Repeat -> R.drawable.repeat_accent
            RepeatMode.RepeatOnce -> R.drawable.repeat_once_accent
            else -> R.drawable.repeat
        }
    }

    private fun getProgressTime(progress: Int): String = formatSeconds(progress)

    private fun getRestTime(progress: Int, progressMax: Int): String =
            "-${formatSeconds(progressMax - progress)}"

    private fun formatSeconds(seconds: Int): String {
        val hours = TimeUnit.SECONDS.toHours(seconds.toLong())
        val min = TimeUnit.SECONDS.toMinutes(seconds.toLong()) - TimeUnit.HOURS.toMinutes(hours)
        val sec = seconds - TimeUnit.MINUTES.toSeconds(min)
        return if (hours == 0L) String.format("%02d:%02d", min, sec)
        else String.format("%02d:%02d:%02d", hours, min, sec)
    }

    private fun getPlayPauseIcon(isPlaying: Boolean): Int {
        return if (isPlaying) R.drawable.pause else R.drawable.play
    }
}
