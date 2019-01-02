package com.moralesbatovski.pirateships.mvvm.viewmodel

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.mvvm.model.ListDataContract
import com.moralesbatovski.pirateships.networking.Outcome
import com.moralesbatovski.pirateships.testing.DummyData
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * @author Gustavo Morales
 *
 * Testing for [ListViewModel]
 */
@RunWith(AndroidJUnit4::class)
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel

    private val repository: ListDataContract.Repository = mock()
    private val outcome: Observer<Outcome<List<PirateShip>>> = mock()

    @Before
    fun init() {
        viewModel = ListViewModel(repository, CompositeDisposable())
        whenever(repository.pirateShipFetchOutcome).doReturn(PublishSubject.create())
        viewModel.pirateShipsOutcome.observeForever(outcome)
    }

    @Test
    fun getPirateShips_nonNullOutcome_shouldCallFetchPirateShips() {
        // Test
        viewModel.getPirateShips()

        // Verify
        verify(repository).fetchPirateShips()
    }

    @Test
    fun onNext_loadingTrue_shouldCallOnNextWithLoadingTrue() {
        // Test
        repository.pirateShipFetchOutcome.onNext(Outcome.loading(true))

        // Verify
        verify(outcome).onChanged(Outcome.loading(true))
    }

    @Test
    fun onNext_loadingFalse_shouldCallOnNextWithLoadingFalse() {
        // Test
        repository.pirateShipFetchOutcome.onNext(Outcome.loading(false))

        // Verify
        verify(outcome).onChanged(Outcome.loading(false))
    }

    @Test
    fun onNext_withSuccess_shouldCallOnChangeWithData() {
        // Setup
        val data = listOf(DummyData.getPirateShip(1, 100), DummyData.getPirateShip(2, 1500))

        // Test
        repository.pirateShipFetchOutcome.onNext(Outcome.success(data))

        // Verify
        verify(outcome).onChanged(Outcome.success(data))
    }

    @Test
    fun onNext_throwsIOException_shouldCallOutcomeFailure() {
        // Setup
        val exception = IOException()

        // Test
        repository.pirateShipFetchOutcome.onNext(Outcome.failure(exception))

        // Verify
        verify(outcome).onChanged(Outcome.failure(exception))
    }

    @Test
    fun refreshPirateShips_nonNullRepository_shouldTriggersRefreshPirateShipsOnRepository() {
        // Test
        viewModel.refreshPirateShips()

        // Verify
        verify(repository).refreshPirateShips()
    }
}
