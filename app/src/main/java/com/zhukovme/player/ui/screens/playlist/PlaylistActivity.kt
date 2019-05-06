package com.zhukovme.player.ui.screens.playlist

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhukovme.player.R
import com.zhukovme.player.databinding.ActivityPlaylistBinding
import com.zhukovme.player.ui.base.BaseActivity
import com.zhukovme.player.ui.base.playlistModule
import kotlinx.android.synthetic.main.activity_playback.coordl_main
import kotlinx.android.synthetic.main.activity_playback.toolbar
import kotlinx.android.synthetic.main.activity_playlist.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaylistActivity : BaseActivity<PlaylistState>() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PlaylistActivity::class.java)
            context.startActivity(starter)
        }
    }

    override val presenter: PlaylistPresenter by instance()
    private val trackListRvAdapter: TrackListRvAdapter by instance()

    private var binding: ActivityPlaylistBinding? = null

    override fun depsModule(): Kodein.Module = playlistModule(this)

    override fun setupViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playlist)
        mainView = coordl_main
        setupToolbar(toolbar, withBackBtn = true)
        setupRv()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun render(state: PlaylistState) {
        binding?.state = state
        trackListRvAdapter.setTrackList(state.trackList)
    }

    private fun setupRv() {
        rv_track_list.layoutManager = LinearLayoutManager(this)
        rv_track_list.adapter = trackListRvAdapter
    }
}
