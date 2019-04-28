package com.zhukovme.player.ui.screens.playback

import com.factorymarket.rxelm.cmd.Cmd
import com.factorymarket.rxelm.contract.RenderableComponent
import com.factorymarket.rxelm.contract.Update
import com.factorymarket.rxelm.msg.ErrorMsg
import com.factorymarket.rxelm.msg.Idle
import com.factorymarket.rxelm.msg.Msg
import com.factorymarket.rxelm.program.Program
import com.factorymarket.rxelm.program.ProgramBuilder
import com.zhukovme.player.R
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackPresenter(
        private val view: MvpView,
        programBuilder: ProgramBuilder
) : RenderableComponent<PlaybackState> {

    private val program: Program<PlaybackState> = programBuilder.build(this)

    fun onCreate() {
        program.run(initialState = buildInitState())
    }

    fun dispatch(msg: Msg) {
        program.accept(msg)
    }

    fun onDestroy() {
        program.stop()
    }

    override fun update(msg: Msg, state: PlaybackState): Update<PlaybackState> {
        return when (msg) {
            is OnShuffleClick -> Update.state(state.copy(
                    shuffleMode = !state.shuffleMode,
                    shuffleIcon = getShuffleIcon(!state.shuffleMode)
            ))
            is OnRepeatClick -> Update.state(state.copy(
                    repeatMode = state.repeatMode.next,
                    repeatIcon = getRepeatIcon(state.repeatMode.next)
            ))
            is OnPlayPauseClick -> Update.state(state.copy(
                    isPlaying = !state.isPlaying,
                    playPauseIcon = getPlayPauseIcon(!state.isPlaying)
            ))
            is OnNextTrackClick -> Update.idle()
            is OnPreviousTrackClick -> Update.idle()
            is OnProgressChanged -> Update.state(state.copy(
                    progress = msg.progress,
                    progressTime = getProgressTime(msg.progress),
                    restTime = getRestTime(msg.progress, state.progressMax)
            ))
            is ErrorMsg -> Update.state(state.copy(snackbarMessage = "Error"))
            else -> Update.idle()
        }
    }

    override fun render(state: PlaybackState) {
        view.render(state)
        state.snackbarMessage?.let { view.showSnackbar(it) }
    }

    override fun call(cmd: Cmd): Single<Msg> {
        return Single.just(Idle)
    }

    @Suppress("MagicNumber")
    private fun buildInitState(): PlaybackState {
        return PlaybackState(
                title = "Init title",
                subtitle = "Init subtitle",
                shuffleMode = false,
                shuffleIcon = getShuffleIcon(false),
                repeatMode = RepeatMode.Off,
                repeatIcon = getRepeatIcon(RepeatMode.Off),
                trackNumber = "1/1",
                trackCover = 0,
                progress = 0,
                progressMax = 250,
                progressTime = getProgressTime(0),
                restTime = getRestTime(0, 250),
                isPlaying = false,
                playPauseIcon = getPlayPauseIcon(false)
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

    private fun getPlayPauseIcon(isPlaying: Boolean): Int {
        return if (isPlaying) R.drawable.pause else R.drawable.play
    }
}
