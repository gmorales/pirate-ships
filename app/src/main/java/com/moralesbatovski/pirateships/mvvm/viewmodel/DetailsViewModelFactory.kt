package com.moralesbatovski.pirateships.mvvm.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Gustavo Morales
 *
 * Details View Model Factory class.
 */
@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val compositeDisposable: CompositeDisposable)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(compositeDisposable) as T
    }
}