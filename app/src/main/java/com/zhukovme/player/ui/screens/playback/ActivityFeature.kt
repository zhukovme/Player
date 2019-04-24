package com.zhukovme.player.ui.screens.playback

import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ReducerFeature

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class ActivityFeature : ReducerFeature<ActivityFeature.Wish, ActivityFeature.State, Nothing>(
        initialState = State(),
        reducer = ReducerImpl()) {

    data class State(val inProgress: Boolean = false)

    sealed class Wish {
        object OnCreate : Wish()
        object OnDestroy : Wish()
    }

    class ReducerImpl : Reducer<State, Wish> {
        override fun invoke(state: State, wish: Wish): State =
                when (wish) {
                    is Wish.OnCreate -> state
                    is Wish.OnDestroy -> state
                }
    }
}
