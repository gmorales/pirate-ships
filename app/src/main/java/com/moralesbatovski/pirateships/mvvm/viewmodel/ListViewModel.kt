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
class ListViewModel(private val repository: ListDataContract.Repository,
                    private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val pirateShipsOutcome: LiveData<Outcome<List<PirateShip>>> by lazy {
        val data = MutableLiveData<Outcome<List<PirateShip>>>()
        compositeDisposable.add(repository.pirateShipFetchOutcome.subscribe({ t -> data.value = t }))
        data
    }

    fun getPirateShips() {
        if (pirateShipsOutcome.value == null) {
            repository.fetchPirateShips()
        }
    }

    fun refreshPirateShips() {
        repository.refreshPirateShips()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        PirateShipDependencyHierarchy.destroyListComponent()
    }
}
