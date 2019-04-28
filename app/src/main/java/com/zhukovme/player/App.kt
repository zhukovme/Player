package com.zhukovme.player

import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import timber.log.Timber

/**
 * Created by Michael Zhukov on 25.12.2017.
 * email: zhukovme@gmail.com
 */
class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        bind<Context>() with singleton { applicationContext }
        import(appModule())
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) debugInit()
    }

    private fun debugInit() {
        Timber.plant(Timber.DebugTree())
    }
}
