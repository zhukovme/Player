package com.zhukovme.player.ui.base

import com.zhukovme.player.ui.screens.playback.PlaybackActivity
import com.zhukovme.player.ui.screens.playback.PlaybackBindings
import com.zhukovme.player.ui.screens.playback.PlaybackFeature
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Created by Michael Zhukov on 14.12.2018.
 * email: zhukovme@gmail.com
 */
fun playbackModule(activity: PlaybackActivity) = Kodein.Module("Playback") {
    bind<PlaybackBindings>() with singleton { PlaybackBindings(activity, PlaybackFeature()) }
}
