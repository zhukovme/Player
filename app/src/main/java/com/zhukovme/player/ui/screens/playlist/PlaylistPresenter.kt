package com.zhukovme.player.ui.screens.playlist

import com.zhukovme.player.ui.base.BasePresenter
import com.zhukovme.rxelm.program.*
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by Michael Zhukov on 20.03.2018.
 * email: zhukovme@gmail.com
 */
class PlaylistPresenter(
        programBuilder: ProgramBuilder
) : BasePresenter<PlaylistState>(programBuilder) {

    @Suppress("MagicNumber", "UnderscoresInNumericLiterals")
    override fun setupInitialState(): PlaylistState {
        return PlaylistState(
                title = "Track list",
                subtitle = null,
                trackList = mutableListOf(
                        Track("Track 1", "Author 1", formatSeconds(86555), 86555),
                        Track("Track 2", "Author 2", formatSeconds(86666), 86666),
                        Track("Track 3", "Author 3", formatSeconds(86777), 86777),
                        Track("Track 4", "Author 4", formatSeconds(86888), 86888),
                        Track("Track 5", "Author 5", formatSeconds(86999), 86999)
                )
        )
    }

    override fun update(msg: Msg, state: PlaylistState): Update<PlaylistState> {
        return when (msg) {
            is OnTrackClick -> Update.idle<PlaylistState>()
                    .also { view?.showSnackbar("${msg.track.name} - ${msg.track.author}") }
            else -> super.update(msg, state)
        }
    }

    override fun call(cmd: Cmd): Single<Msg> {
        return Single.just(Idle)
    }

    override fun handleError(msg: ErrorMsg, state: PlaylistState): Update<PlaylistState> {
        view?.showSnackbar("Error")
        return super.handleError(msg, state)
    }

    private fun formatSeconds(seconds: Int): String {
        val hours = TimeUnit.SECONDS.toHours(seconds.toLong())
        val min = TimeUnit.SECONDS.toMinutes(seconds.toLong()) - TimeUnit.HOURS.toMinutes(hours)
        val sec = seconds - TimeUnit.MINUTES.toSeconds(min)
        return if (hours == 0L) String.format("%02d:%02d", min, sec)
        else String.format("%02d:%02d:%02d", hours, min, sec)
    }
}
