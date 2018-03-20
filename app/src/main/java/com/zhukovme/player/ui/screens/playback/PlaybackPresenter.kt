package com.zhukovme.player.ui.screens.playback

import com.zhukovme.player.ui.base.*
import io.reactivex.Single
import io.reactivex.disposables.Disposable

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackPresenter(private val view: PlaybackView,
                        private val store: Store) : Component {

    private var elmDisposable: Disposable? = store.create(PlaybackState(), this)

    fun onCreate() {
        store.accept(Init)
    }

    fun onDestroy() {
        elmDisposable?.dispose()
    }

    override fun update(action: Action, state: State): Pair<State, Command> {
        val playbackState = state as PlaybackState
        return when (action) {
            is Init -> Pair(playbackState.copy(title = "The Elm", subtitle = "Architecture"), None)
            else -> Pair(playbackState, None)
        }
    }

    override fun render(state: State) {
        view.renderState(state as PlaybackState)
    }

    override fun call(cmd: Command): Single<Action> = when (cmd) {
        else -> Single.just(Idle)
    }
}
