package com.moralesbatovski.pirateships.mvvm.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.moralesbatovski.pirateships.commons.PirateShipDependencyHierarchy
import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.mvvm.model.ListDataContract
import com.moralesbatovski.pirateships.networking.Outcome
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Gustavo Morales
 *
 * List View Model class.
 */
class ListViewModel(private val repo: ListDataContract.Repository,
                    private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val postsOutcome: LiveData<Outcome<List<PirateShip>>> by lazy {
        val data = MutableLiveData<Outcome<List<PirateShip>>>()
        compositeDisposable.add(repo.postFetchOutcome.subscribe({ t -> data.value = t }))
        data
    }

    fun getPirateShips() {
        if (postsOutcome.value == null) {
            repo.fetchPirateShips()
        }
    }

    fun refreshPirateShips() {
        repo.refreshPirateShips()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        PirateShipDependencyHierarchy.destroyListComponent()
    }
}
