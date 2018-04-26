package com.moralesbatovski.pirateships.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Gustavo Morales
 *
 * Storage Module for DI.
 */
@Module
class StorageModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}