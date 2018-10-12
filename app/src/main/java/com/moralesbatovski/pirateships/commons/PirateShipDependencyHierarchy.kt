package com.moralesbatovski.pirateships.commons

import com.moralesbatovski.pirateships.PirateShipApp
import com.moralesbatovski.pirateships.di.DaggerDetailsComponent
import com.moralesbatovski.pirateships.di.DaggerListComponent
import com.moralesbatovski.pirateships.di.DetailsComponent
import com.moralesbatovski.pirateships.di.ListComponent
import javax.inject.Singleton

/**
 * @author Gustavo Morales
 *
 * Dependency Hierarchy class.
 */
@Singleton
object PirateShipDependencyHierarchy {

    private var listComponent: ListComponent? = null
    private var detailsComponent: DetailsComponent? = null

    fun listComponent(): ListComponent {
        if (listComponent == null) {
            listComponent = DaggerListComponent.builder()
                    .coreComponent(PirateShipApp.coreComponent)
                    .build()
        }
        return listComponent as ListComponent
    }

    fun destroyListComponent() {
        listComponent = null
    }

    fun detailsComponent(): DetailsComponent {
        if (detailsComponent == null) {
            detailsComponent = DaggerDetailsComponent.builder().
                    listComponent(listComponent())
                    .build()
        }
        return detailsComponent as DetailsComponent
    }

    fun destroyDetailsComponent() {
        detailsComponent = null
    }
}