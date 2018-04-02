package com.zhukovme.player

import android.app.Activity
import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.zhukovme.player.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Michael Zhukov on 25.12.2017.
 * email: zhukovme@gmail.com
 */
class App : Application(), HasActivityInjector {

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    private var refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        if (!setupLeakCanary()) return
        setupDagger()
        debugInit()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = activityInjector

    private fun setupDagger() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    private fun setupLeakCanary(): Boolean {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return false
        }
        refWatcher = LeakCanary.install(this)
        return true
    }

    private fun debugInit() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
