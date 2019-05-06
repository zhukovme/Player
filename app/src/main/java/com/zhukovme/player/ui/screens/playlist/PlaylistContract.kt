package com.zhukovme.player.ui.screens.playlist

import android.os.Parcelable
import com.zhukovme.rxelm.program.Msg
import com.zhukovme.rxelm.program.State
import kotlinx.android.parcel.Parcelize

/**
 * Created by Michael Zhukov on 28.04.2019.
 * email: zhukovme@gmail.com
 */
@Parcelize
data class PlaylistState(
        val title: String,
        val subtitle: String?,
        val trackList: MutableList<Track>
) : State(), Parcelable

@Parcelize
data class Track(
        val name: String,
        val author: String,
        val lengthStr: String,
        val length: Int
) : Parcelable

data class OnTrackClick(val track: Track) : Msg()
