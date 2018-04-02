package com.zhukovme.player.ui.screens.playback

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import com.zhukovme.player.R
import com.zhukovme.player.databinding.ActivityPlaybackBinding
import com.zhukovme.player.ui.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_playback.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

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

    @Inject
    lateinit var presenter: PlaybackPresenter
    private var binding: ActivityPlaybackBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playback)
        setupToolbar(toolbar, true)

        binding?.presenter = presenter
        presenter.onCreate()
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
        presenter.onDestroy()
    }

    override fun renderState(playbackState: PlaybackState) {
        binding?.state = playbackState
    }

    override fun showSnackbar(message: String) {
        Snackbar.make(coordl_main, message, Snackbar.LENGTH_LONG)
                .show()
    }
}
