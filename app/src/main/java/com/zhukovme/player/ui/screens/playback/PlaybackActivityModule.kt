package com.zhukovme.player.ui.screens.playback

import dagger.Module
import dagger.Provides

/**
 * Created by Michael Zhukov on 02.04.2018.
 * email: zhukovme@gmail.com
 */
@Module
class PlaybackActivityModule {

    @Provides
    fun providePlaybackView(playbackActivity: PlaybackActivity): PlaybackView = playbackActivity
}
