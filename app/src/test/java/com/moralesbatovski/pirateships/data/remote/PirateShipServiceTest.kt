package com.moralesbatovski.pirateships.data.remote

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moralesbatovski.pirateships.testing.DependencyProvider
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

/**
 * @author Gustavo Morales
 *
 * API call tests for [PirateShipService]
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PirateShipServiceTest {

    private lateinit var pirateShipService: PirateShipService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        pirateShipService = DependencyProvider
                .getRetrofit(mockWebServer.url("/"))
                .create(PirateShipService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getPirateShips_withResponseCode200_shouldRetrieveApiResponse() {
        // Setup
        val mockedResponse = MockResponse()
        mockedResponse.setResponseCode(200)
        mockedResponse.setBody(DependencyProvider.getResponseFromJson("pirateShips"))
        mockWebServer.enqueue(mockedResponse)

        // Test
        pirateShipService.getPirateShips()
                .test()
                .run {
                    // Verify
                    assertNoErrors()
                    assertValueCount(1)
                    Assert.assertEquals(values().size, 1)
                    Assert.assertEquals(values()[0].success, true)
                    Assert.assertEquals(values()[0].ships.size, 11)
                    Assert.assertEquals(values()[0].ships[1].id, 46347657)
                    Assert.assertEquals(values()[0].ships[4].greetingType, "yo")
                    Assert.assertNull(values()[0].ships[3])
                    Assert.assertNotNull(values()[0].ships[10])
                }
    }
}
