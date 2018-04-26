package com.moralesbatovski.pirateships.di

import android.content.Context
import android.content.SharedPreferences
import com.moralesbatovski.pirateships.networking.Scheduler
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Gustavo Morales
 *
 * Core Component for DI.
 */
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class, ImageModule::class])
interface CoreComponent {

    fun context(): Context

    fun retrofit(): Retrofit

    fun picasso(): Picasso

    fun sharedPreferences(): SharedPreferences

    fun scheduler(): Scheduler
}