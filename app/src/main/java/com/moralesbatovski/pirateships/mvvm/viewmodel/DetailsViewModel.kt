package com.moralesbatovski.pirateships.mvvm.viewmodel

import android.arch.lifecycle.ViewModel
import com.moralesbatovski.pirateships.commons.PirateShipDependencyHierarchy
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Gustavo Morales
 *
 * Details View Model class.
 */
class DetailsViewModel(private val compositeDisposable: CompositeDisposable) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        PirateShipDependencyHierarchy.destroyDetailsComponent()
    }
}