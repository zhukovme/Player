package com.zhukovme.player.ui.screens.playback

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import com.zhukovme.player.R
import com.zhukovme.player.databinding.ActivityPlaybackBinding
import com.zhukovme.player.ui.base.BaseActivity
import com.zhukovme.player.ui.base.Store
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackActivity : BaseActivity(), PlaybackView {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PlaybackActivity::class.java)
            context.startActivity(starter)
        }
    }

    private var presenter: PlaybackPresenter? = null
    private var binding: ActivityPlaybackBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playback)
        setStatusBarTranslucent()
        setupToolbar(toolbar, true)

        presenter = PlaybackPresenter(this, Store())
        binding?.presenter = presenter
        presenter?.onCreate()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun renderState(playbackState: PlaybackState) {
        binding?.state = playbackState
    }
}
