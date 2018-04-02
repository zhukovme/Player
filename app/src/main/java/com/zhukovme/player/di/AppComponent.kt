package com.zhukovme.player.di

import android.app.Application
import com.zhukovme.player.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * Created by Michael Zhukov on 02.04.2018.
 * email: zhukovme@gmail.com
 */
@Component(modules = [
    (AndroidInjectionModule::class),
    (AppModule::class),
    (ActivityBuilder::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
