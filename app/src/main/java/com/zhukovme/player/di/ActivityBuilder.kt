package com.zhukovme.player.di

import com.zhukovme.player.ui.screens.playback.PlaybackActivity
import com.zhukovme.player.ui.screens.playback.PlaybackActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Michael Zhukov on 02.04.2018.
 * email: zhukovme@gmail.com
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(PlaybackActivityModule::class)])
    internal abstract fun bindPlaybackActivity(): PlaybackActivity
}
