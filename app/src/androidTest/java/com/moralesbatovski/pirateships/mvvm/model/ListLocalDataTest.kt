package com.moralesbatovski.pirateships.mvvm.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.moralesbatovski.pirateships.data.local.PirateShipDB
import com.moralesbatovski.pirateships.testing.DummyData
import com.moralesbatovski.pirateships.testing.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Gustavo Morales
 *
 * Testing class for [ListLocalData]
 */
@RunWith(AndroidJUnit4::class)
class ListLocalDataTest {

    private lateinit var pirateShipDB: PirateShipDB

    private val listLocalData: ListLocalData by lazy { ListLocalData(pirateShipDB, TestScheduler()) }
    private val dummyPirateShips = listOf(DummyData.getPirateShip(1, 2000), DummyData.getPirateShip(2, 50000))

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        pirateShipDB = Room
                .inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), PirateShipDB::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun clean() {
        pirateShipDB.close()
    }

    @Test
    fun upsertAll_withValidaData_shouldCallLocalGetPirateShips() {
        // Setup
        val pirateShips = listOf(DummyData.getPirateShip(1, 2000), DummyData.getPirateShip(2, 50000))

        // Test
        pirateShipDB.pirateShipDao().upsertAll(dummyPirateShips)

        // Verify
        listLocalData.getPirateShips().test().assertValue(pirateShips)
    }

    @Test
    fun savePirateShips_withDataStoredOnDataBase_shouldReturnDataWithoutErrors() {
        // Setup
        val pirateShips = pirateShipDB.pirateShipDao().getAll()

        // Test
        listLocalData.savePirateShips(dummyPirateShips)

        // Verify
        pirateShips.test().assertNoErrors().assertValue(dummyPirateShips)
    }
}
