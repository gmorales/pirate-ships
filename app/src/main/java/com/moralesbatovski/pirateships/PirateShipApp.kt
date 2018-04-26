package com.moralesbatovski.pirateships

import android.app.Application
import com.moralesbatovski.pirateships.di.AppModule
import com.moralesbatovski.pirateships.di.CoreComponent
import com.moralesbatovski.pirateships.di.DaggerCoreComponent

/**
 * @author Gustavo Morales
 *
 * Application class.
 */
class PirateShipApp : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
