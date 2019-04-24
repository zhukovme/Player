package com.zhukovme.player.ui.screens.playback

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.zhukovme.player.ui.screens.playback.PlaybackFeature.*
import io.reactivex.Observable

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackFeature : ActorReducerFeature<Wish, Effect, State, Nothing>(
        initialState = State(),
        actor = ActorImpl(),
        reducer = ReducerImpl()) {

    data class State(
            val shuffleMode: Boolean = false,
            val repeatMode: RepeatMode = RepeatMode.Off,
            val progress: Int = 0,
            val progressMax: Int = 200,
            val isPlaying: Boolean = false,
            val throwable: Throwable? = null
    )

    sealed class Wish {
        data class ChangeProgress(val progress: Int) : Wish()
        object Shuffle : Wish()
        object Repeat : Wish()
        object PreviousTrack : Wish()
        object NextTrack : Wish()
        object PlayPause : Wish()
    }

    sealed class Effect {
        data class Success(val state: State) : Effect()
        data class Error(val throwable: Throwable) : Effect()
    }

    class ActorImpl : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<Effect> = when (wish) {
            is Wish.ChangeProgress -> Observable.just(Effect.Success(state.copy(
                    progress = wish.progress
            )))
            is Wish.Shuffle -> Observable.just(Effect.Success(state.copy(
                    shuffleMode = !state.shuffleMode
            )))
            is Wish.Repeat -> Observable.just(Effect.Success(state.copy(
                    repeatMode = state.repeatMode.next
            )))
            is Wish.PreviousTrack -> Observable.just(Effect.Success(state))
            is Wish.NextTrack -> Observable.just(Effect.Success(state))
            is Wish.PlayPause -> Observable.just(Effect.Success(state.copy(
                    isPlaying = !state.isPlaying
            )))
        }
    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State = when (effect) {
            is Effect.Success -> effect.state
            is Effect.Error -> state.copy(
                    throwable = effect.throwable
            )
        }
    }
}
