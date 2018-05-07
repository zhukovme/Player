package com.zhukovme.player.ui.screens.playback

import com.zhukovme.player.R
import com.zhukovme.player.ui.base.*
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackPresenter
@Inject
constructor(private val view: PlaybackView) : Component {

    private val store = Store.init(PlaybackState(), this)

    //region UI callbacks

    fun onCreate() {
        store.dispatch(Init)
    }

    fun onDestroy() {
        store.terminate()
    }

    fun onShuffleClick() {
        store.dispatch(ShuffleAction)
    }

    fun onRepeatClick() {
        store.dispatch(RepeatAction)
    }

    fun onProgressChanged(progress: Int, fromUser: Boolean) {
        if (!fromUser) return
        store.dispatch(ChangeProgressAction(progress))
    }

    fun onPreviousClick() {
        store.dispatch(PreviousTrackAction)
    }

    fun onPlayPauseClick() {
        store.dispatch(PlayPauseAction)
    }

    fun onNextClick() {
        store.dispatch(NextTrackAction)
    }

    //endregion

    // UI Helpers

    fun getShuffleIcon(shuffleMode: Boolean?): Int =
            if (shuffleMode == true) R.drawable.shuffle_accent
            else R.drawable.shuffle_white

    fun getRepeatIcon(repeatMode: RepeatMode?): Int =
            if (repeatMode == null) R.drawable.repeat_white
            else when (repeatMode) {
                is RepeatMode.Off -> R.drawable.repeat_white
                is RepeatMode.Repeat -> R.drawable.repeat_accent
                is RepeatMode.RepeatOnce -> R.drawable.repeat_once_accent
            }

    fun getProgressTime(progress: Int?): String =
            if (progress == null) "00:00"
            else formatSeconds(progress.toLong())

    fun getRestTime(progress: Int?, progressMax: Int?): String =
            if (progress == null || progressMax == null) "-00:00"
            else "-${formatSeconds((progressMax - progress).toLong())}"

    // TODO: Move method to another class
    private fun formatSeconds(seconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(seconds)
        val min = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours)
        val sec = seconds - TimeUnit.MINUTES.toSeconds(min)
        return if (hours == 0L) String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        else String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, min, sec)
    }

    //endregion

    override fun reduce(state: State, action: Action): Pair<State, Command> {
        val playbackState = state as PlaybackState
        return when (action) {
            is Init -> Pair(playbackState.copy(
                    title = "The Deceived",
                    subtitle = "Trivium - Ascendancy",
                    shuffleMode = false,
                    repeatMode = RepeatMode.Off,
                    trackNumber = "#1 - 1/2",
                    trackCover = R.drawable.example_cover,
                    progress = 0,
                    progressMax = 200,
                    isPlaying = false,
                    snackbarMessage = null
            ), None)
            is ShuffleAction -> Pair(playbackState.copy(
                    shuffleMode = !state.shuffleMode
            ), None) // TODO: Add shuffle command
            is RepeatAction -> Pair(playbackState.copy(
                    repeatMode = state.repeatMode.next
            ), None) // TODO: Add repeat command
            is ChangeProgressAction -> Pair(playbackState.copy(
                    progress = action.progress
            ), None) // TODO: Add change progress command
            is PreviousTrackAction -> Pair(playbackState.copy(), None) // TODO: Add previous track command
            is PlayPauseAction -> Pair(playbackState.copy(
                    isPlaying = !state.isPlaying
            ), None) // TODO: Add play/pause command
            is NextTrackAction -> Pair(playbackState.copy(), None) // TODO: Add next track command
            else -> Pair(playbackState, None)
        }
    }

    override fun render(state: State) {
        view.renderState(state as PlaybackState)
        state.snackbarMessage?.let { view.showSnackbar(it) }
    }

    override fun call(cmd: Command): Single<Action> = when (cmd) {
        else -> Single.just(Idle)
    }
}
