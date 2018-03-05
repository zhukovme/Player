package com.zhukovme.player

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber

/**
 * Created by Michael Zhukov on 25.12.2017.
 * email: zhukovme@gmail.com
 */
class App : Application() {

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }

    lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        if (setupLeakCanary()) return

        debugInit()
    }

    private fun setupLeakCanary(): Boolean {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return true
        }
        refWatcher = LeakCanary.install(this)
        return false
    }

    private fun debugInit() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
