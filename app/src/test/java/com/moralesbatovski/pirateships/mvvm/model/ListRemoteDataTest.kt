package com.moralesbatovski.pirateships.mvvm.model

import android.os.Build
import com.moralesbatovski.pirateships.BuildConfig
import com.moralesbatovski.pirateships.data.remote.PirateShipService
import com.moralesbatovski.pirateships.testing.DummyData
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * @author Gustavo Morales
 *
 * Testing class for [ListRemoteData]
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [Build.VERSION_CODES.O_MR1])
class ListRemoteDataTest {

    @Test
    fun getPirateShips_apiRetrievingResponse_shouldReturnData() {
        // Setup
        val pirateShipService = mock<PirateShipService>()
        val pirateShips = listOf(DummyData.getPirateShip(1, 999), DummyData.getPirateShip(2, 2500))
        whenever(pirateShipService.getPirateShips()).thenReturn(Flowable.just(DummyData.getPirateShipResponse(true, pirateShips)))

        // Test
        ListRemoteData(pirateShipService).getPirateShips().test().run {
            // Verify
            assertNoErrors()
            assertValueCount(1)
            assertEquals(values()[0].success, true)
            assertEquals(values()[0].ships.size, 2)
            assertEquals(values()[0].ships[0].id, 1)
            assertEquals(values()[0].ships[0].price, 999)
            assertEquals(values()[0].ships[1].id, 2)
            assertEquals(values()[0].ships[1].price, 2500)
        }
    }
}
