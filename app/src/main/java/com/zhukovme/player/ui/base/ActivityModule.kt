package com.zhukovme.player.ui.base

import com.zhukovme.player.ui.screens.playback.PlaybackActivity
import com.zhukovme.player.ui.screens.playback.PlaybackPresenter
import com.zhukovme.player.ui.screens.playlist.PlaylistActivity
import com.zhukovme.player.ui.screens.playlist.PlaylistPresenter
import com.zhukovme.player.ui.screens.playlist.TrackListRvAdapter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Created by Michael Zhukov on 14.12.2018.
 * email: zhukovme@gmail.com
 */
fun playbackModule(activity: PlaybackActivity): Kodein.Module {
    return Kodein.Module(PlaybackActivity::class.java.simpleName) {
        bind<PlaybackPresenter>() with singleton { PlaybackPresenter(instance()) }
    }
}

fun playlistModule(activity: PlaylistActivity): Kodein.Module {
    return Kodein.Module(PlaylistActivity::class.java.simpleName) {
        bind<TrackListRvAdapter>() with singleton { TrackListRvAdapter(instance()) }
        bind<PlaylistPresenter>() with singleton { PlaylistPresenter(instance()) }
    }
}
