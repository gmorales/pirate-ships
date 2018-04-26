package com.moralesbatovski.pirateships.di

import android.content.Context
import com.moralesbatovski.pirateships.networking.AppScheduler
import com.moralesbatovski.pirateships.networking.Scheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Gustavo Morales
 *
 * App Module for DI.
 */
@Module
class AppModule(val context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun scheduler(): Scheduler {
        return AppScheduler()
    }
}
