package com.moralesbatovski.pirateships.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moralesbatovski.pirateships.mvvm.model.ListDataContract
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Gustavo Morales
 *
 * List View Model Factory class.
 */
@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(private val repository: ListDataContract.Repository,
                           private val compositeDisposable: CompositeDisposable)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository, compositeDisposable) as T
    }
}