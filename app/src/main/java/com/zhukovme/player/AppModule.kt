package com.zhukovme.player

import com.zhukovme.rxelm.interceptor.LoggingInterceptor
import com.zhukovme.rxelm.program.ProgramBuilder
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
            .msgScheduler(AndroidSchedulers.mainThread())
            .interceptor(object : LoggingInterceptor() {
                override fun log(tag: String, message: String, error: Throwable?) {
                    Timber.tag(tag).d(message)
                    error?.let { Timber.tag(tag).e(error) }
                }
            })
}
