package com.moralesbatovski.pirateships.mvvm.model

import android.os.Build
import com.moralesbatovski.pirateships.BuildConfig
import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.networking.Outcome
import com.moralesbatovski.pirateships.testing.DummyData
import com.moralesbatovski.pirateships.testing.TestScheduler
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

/**
 * @author Gustavo Morales
 *
 * Testing class for [ListRepository]
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [Build.VERSION_CODES.O_MR1])
class ListRepositoryTest {

    private lateinit var repository: ListRepository

    private val localData: ListDataContract.Local = mock()
    private val remoteData: ListDataContract.Remote = mock()
    private val compositeDisposable = CompositeDisposable()

    @Before
    fun init() {
        repository = ListRepository(localData, remoteData, TestScheduler(), compositeDisposable)
        whenever(localData.getPirateShips()).doReturn(Flowable.just(emptyList()))
        whenever(remoteData.getPirateShips()).doReturn(Flowable.just(DummyData.getPirateShipResponse(true, emptyList())))
    }

    @Test
    fun fetchPirateShips_withValidData_shouldCallSuccess() {
        // Setup
        val testObserver = TestObserver<Outcome<List<PirateShip>>>()
        val pirateShipsSuccess = listOf(DummyData.getPirateShip(1, 999), DummyData.getPirateShip(2, 1500))
        whenever(localData.getPirateShips()).doReturn(Flowable.just(pirateShipsSuccess))

        // Test
        repository.pirateShipFetchOutcome.subscribe(testObserver)
        repository.fetchPirateShips()

        // Verify
        verify(localData).getPirateShips()
        testObserver.assertValueAt(0, Outcome.loading(true))
        testObserver.assertValueAt(1, Outcome.loading(false))
        testObserver.assertValueAt(2, Outcome.success(pirateShipsSuccess))
    }

    @Test
    fun fetchPirateShips_remoteFetchTrue_shouldCallRemoteGetPirateShips() {
        // Setup
        repository.remoteFetch = true

        // Test
        repository.fetchPirateShips()

        // Verify
        verify(remoteData).getPirateShips()
    }

    @Test
    fun fetchPirateShips_remoteFetchFalse_shouldNotCallRemoteGetPirateShips() {
        // Setup
        repository.remoteFetch = false

        // Test
        repository.fetchPirateShips()

        // Verify
        verify(remoteData, never()).getPirateShips()
    }

    @Test
    fun refreshPirateShips_retrievingUpdatedData_shouldCallSavePirateShips() {
        // Setup
        val dummyPirateShips = listOf(DummyData.getPirateShip(1, 1))
        val dummyPirateShipResponse = DummyData.getPirateShipResponse(true, dummyPirateShips)
        whenever(remoteData.getPirateShips()).doReturn(Flowable.just(dummyPirateShipResponse))

        // Test
        repository.refreshPirateShips()

        // Verify
        verify(localData).savePirateShips(dummyPirateShips)
    }

    @Test
    fun refreshPirateShips_returningIOException_shouldCallFailureException() {
        // Setup
        val testObserver = TestObserver<Outcome<List<PirateShip>>>()
        val exception = IOException()
        whenever(remoteData.getPirateShips()).doReturn(Flowable.error(exception))

        // Test
        repository.pirateShipFetchOutcome.subscribe(testObserver)
        repository.refreshPirateShips()

        // Verify
        testObserver.assertValueAt(0, Outcome.loading(true))
        testObserver.assertValueAt(1, Outcome.loading(false))
        testObserver.assertValueAt(2, Outcome.failure(exception))
    }
}
