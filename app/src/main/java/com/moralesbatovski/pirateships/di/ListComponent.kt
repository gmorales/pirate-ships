package com.moralesbatovski.pirateships.di

import android.arch.persistence.room.Room
import android.content.Context
import com.moralesbatovski.pirateships.commons.Constants
import com.moralesbatovski.pirateships.data.local.PirateShipDB
import com.moralesbatovski.pirateships.data.remote.PirateShipService
import com.moralesbatovski.pirateships.mvvm.model.ListDataContract
import com.moralesbatovski.pirateships.mvvm.model.ListLocalData
import com.moralesbatovski.pirateships.mvvm.model.ListRemoteData
import com.moralesbatovski.pirateships.mvvm.model.ListRepository
import com.moralesbatovski.pirateships.mvvm.view.activities.ListActivity
import com.moralesbatovski.pirateships.mvvm.view.adapters.ListAdapter
import com.moralesbatovski.pirateships.mvvm.viewmodel.ListViewModelFactory
import com.moralesbatovski.pirateships.networking.Scheduler
import com.squareup.picasso.Picasso
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

/**
 * @author Gustavo Morales
 *
 * List Component for DI.
 */
@ListScope
@Component(dependencies = [CoreComponent::class], modules = [ListModule::class])
interface ListComponent {

    fun pirateShipDB(): PirateShipDB

    fun pirateShipService(): PirateShipService

    fun picasso(): Picasso

    fun scheduler(): Scheduler

    fun inject(listActivity: ListActivity)
}

@Module
class ListModule {

    @Provides
    @ListScope
    fun adapter(picasso: Picasso): ListAdapter = ListAdapter(picasso)

    @Provides
    @ListScope
    fun listViewModelFactory(repository: ListDataContract.Repository, compositeDisposable: CompositeDisposable): ListViewModelFactory = ListViewModelFactory(repository, compositeDisposable)

    @Provides
    @ListScope
    fun listRepo(local: ListDataContract.Local, remote: ListDataContract.Remote, scheduler: Scheduler, compositeDisposable: CompositeDisposable): ListDataContract.Repository = ListRepository(local, remote, scheduler, compositeDisposable)

    @Provides
    @ListScope
    fun remoteData(postService: PirateShipService): ListDataContract.Remote = ListRemoteData(postService)

    @Provides
    @ListScope
    fun localData(postDb: PirateShipDB, scheduler: Scheduler): ListDataContract.Local = ListLocalData(postDb, scheduler)

    @Provides
    @ListScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @ListScope
    fun pirateShipDB(context: Context): PirateShipDB = Room.databaseBuilder(context, PirateShipDB::class.java, Constants.PirateShips.DATA_BASE_NAME).build()

    @Provides
    @ListScope
    fun pirateShipService(retrofit: Retrofit): PirateShipService = retrofit.create(PirateShipService::class.java)
}
