package com.zhukovme.player.ui.screens.playback

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import com.zhukovme.player.R
import com.zhukovme.player.databinding.ActivityPlaybackBinding
import com.zhukovme.player.ui.base.BaseActivity
import com.zhukovme.player.ui.base.playbackModule
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_playback.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaybackActivity : BaseActivity<UiEvent>(), Consumer<PlaybackVm> {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PlaybackActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun depsModule(): Kodein.Module = playbackModule(this)

    private val bindings: PlaybackBindings by instance()
    private var binding: ActivityPlaybackBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playback)
        mainLayout = coordl_main
        setupToolbar(toolbar)
        setupClickListeners()
        bindings.setup(this)

//        presenter.onCreate()
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
//        presenter.onDestroy()
    }

    override fun accept(viewModel: PlaybackVm?) {
        binding?.viewModel = viewModel
    }

    private fun setupClickListeners() {
        iv_shuffle.setOnClickListener { onNext(UiEvent.OnShuffleCLick) }
        iv_repeat.setOnClickListener { onNext(UiEvent.OnRepeatClick) }
        iv_previous_track.setOnClickListener { onNext(UiEvent.OnPreviousTrackClick) }
        iv_next_track.setOnClickListener { onNext(UiEvent.OnNextTrackClick) }
        iv_play_pause.setOnClickListener { onNext(UiEvent.OnPlayPauseClick) }
        sb_progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) onNext(UiEvent.OnProgressChanged(progress))
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
