package com.zhukovme.player.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Michael Zhukov on 02.04.2018.
 * email: zhukovme@gmail.com
 */
@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application
}
