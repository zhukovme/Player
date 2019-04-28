package com.zhukovme.player

import com.factorymarket.rxelm.log.LogType
import com.factorymarket.rxelm.log.RxElmLogger
import com.factorymarket.rxelm.program.ProgramBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import timber.log.Timber

/**
 * Created by Michael Zhukov on 02.04.2018.
 * email: zhukovme@gmail.com
 */
fun appModule() = Kodein.Module("App") {
    bind<ProgramBuilder>() with singleton { programBuilder() }
}

private fun programBuilder(): ProgramBuilder {
    return ProgramBuilder()
            .outputScheduler(AndroidSchedulers.mainThread())
            .handleCmdErrors(true)
            .logger(object : RxElmLogger {
                override fun logType(): LogType {
                    return LogType.All
                }

                override fun error(stateName: String, t: Throwable) {
                    Timber.tag(stateName).e(t)
                }

                override fun log(stateName: String, message: String) {
                    Timber.tag(stateName).d(message)
                }
            })
}
