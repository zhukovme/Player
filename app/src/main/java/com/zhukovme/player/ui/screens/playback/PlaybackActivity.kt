package com.zhukovme.player.ui.screens.playback

import android.content.Context
import android.content.Intent
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.zhukovme.player.R
import com.zhukovme.player.databinding.ActivityPlaybackBinding
import com.zhukovme.player.ui.base.BaseActivity
import com.zhukovme.player.ui.base.playbackModule
import kotlinx.android.synthetic.main.activity_playback.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackActivity : BaseActivity<PlaybackState>() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PlaybackActivity::class.java)
            context.startActivity(starter)
        }
    }

    override val presenter: PlaybackPresenter by instance()

    private var binding: ActivityPlaybackBinding? = null

    override fun depsModule(): Kodein.Module = playbackModule(this)

    override fun setupViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playback)
        mainView = coordl_main
        setupToolbar(toolbar)
        setupClickListeners()
    }

    override fun render(state: PlaybackState) {
        binding?.state = state
    }

    private fun setupClickListeners() {
        iv_shuffle.setOnClickListener { presenter.dispatch(OnShuffleClick) }
        iv_repeat.setOnClickListener { presenter.dispatch(OnRepeatClick) }
        iv_previous_track.setOnClickListener { presenter.dispatch(OnPreviousTrackClick) }
        iv_next_track.setOnClickListener { presenter.dispatch(OnNextTrackClick) }
        iv_play_pause.setOnClickListener { presenter.dispatch(OnPlayPauseClick) }
        sb_progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) presenter.dispatch(OnProgressChanged(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Do nothing
            }
        })
    }
}
