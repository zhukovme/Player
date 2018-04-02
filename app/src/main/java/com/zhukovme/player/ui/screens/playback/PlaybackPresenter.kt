package com.zhukovme.player.ui.screens.playback

import com.zhukovme.player.ui.base.*
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackPresenter
@Inject
constructor(private val view: PlaybackView) : Component {

    private val store = Store.init(PlaybackState(), this)

    fun onCreate() {
        store.dispatch(Init)
    }

    fun onDestroy() {
        store.terminate()
    }

    override fun reduce(state: State, action: Action): Pair<State, Command> {
        val playbackState = state as PlaybackState
        return when (action) {
            is Init -> Pair(playbackState.copy(
                    title = "The Elm",
                    subtitle = "Architecture",
                    snackbarMessage = null), None)
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
