package com.moralesbatovski.pirateships.di

import com.moralesbatovski.pirateships.data.local.PirateShipDB
import com.moralesbatovski.pirateships.mvvm.model.DetailsDataContract
import com.moralesbatovski.pirateships.mvvm.model.DetailsLocalData
import com.moralesbatovski.pirateships.mvvm.model.DetailsRepository
import com.moralesbatovski.pirateships.mvvm.view.activities.DetailsActivity
import com.moralesbatovski.pirateships.mvvm.viewmodel.DetailsViewModelFactory
import com.moralesbatovski.pirateships.networking.Scheduler
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Gustavo Morales
 *
 * Detail Component for DI.
 */
@DetailsScope
@Component(dependencies = [ListComponent::class], modules = [DetailsModule::class])
interface DetailsComponent {
    fun inject(detailsActivity: DetailsActivity)
}

@Module
class DetailsModule {

    @Provides
    @DetailsScope
    fun detailsViewModelFactory(compositeDisposable: CompositeDisposable): DetailsViewModelFactory {
        return DetailsViewModelFactory(compositeDisposable)
    }

    @Provides
    @DetailsScope
    fun detailsRepo(local: DetailsDataContract.Local, scheduler: Scheduler, compositeDisposable: CompositeDisposable)
            : DetailsDataContract.Repository = DetailsRepository(local, scheduler, compositeDisposable)

    @Provides
    @DetailsScope
    fun localData(pirateShipDB: PirateShipDB): DetailsDataContract.Local = DetailsLocalData(pirateShipDB)

    @Provides
    @DetailsScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()
}
