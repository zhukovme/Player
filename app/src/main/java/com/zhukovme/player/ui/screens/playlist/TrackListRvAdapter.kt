package com.zhukovme.player.ui.screens.playlist

import com.zhukovme.player.R
import com.zhukovme.player.ui.base.BindingSingleRvAdapter

/**
 * Created by Michael Zhukov on 06.05.2019.
 * email: zhukovme@gmail.com
 */
class TrackListRvAdapter(
        private val presenter: PlaylistPresenter
) : BindingSingleRvAdapter(R.layout.item_track) {

    private val trackList: MutableList<Track> = ArrayList()

    fun setTrackList(trackList: MutableList<Track>) {
        this.trackList.clear()
        this.trackList.addAll(trackList)
        notifyDataSetChanged()
    }

    override fun getItemForPosition(position: Int): Track? {
        return trackList.getOrNull(position)
    }

    override fun getItemCount(): Int = trackList.size

    override fun onInitViewHolder(viewHolder: ViewHolder) {
        viewHolder.itemView.setOnClickListener {
            val track = getItemForPosition(viewHolder.adapterPosition)
            track?.let { presenter.dispatch(OnTrackClick(it)) }
        }
    }
}
