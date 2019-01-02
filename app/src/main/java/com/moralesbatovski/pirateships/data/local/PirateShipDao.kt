package com.moralesbatovski.pirateships.data.local

import androidx.room.*
import io.reactivex.Flowable

/**
 * @author Gustavo Morales
 *
 * Pirate Ship DAO class.
 */
@Dao
interface PirateShipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(pirateShips: List<PirateShip>)

    @Query("SELECT * from pirateShip where id = :id")
    fun getForId(id: Long): Flowable<PirateShip>

    @Delete
    fun delete(pirateShip: PirateShip)

    @Query("SELECT * FROM PirateShip")
    fun getAll(): Flowable<List<PirateShip>>
}
