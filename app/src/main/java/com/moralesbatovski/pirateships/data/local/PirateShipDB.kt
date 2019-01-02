package com.moralesbatovski.pirateships.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Gustavo Morales
 *
 * Pirate Ship Data Base.
 */
@Database(entities = [PirateShip::class], version = 1, exportSchema = false)
abstract class PirateShipDB : RoomDatabase() {

    abstract fun pirateShipDao(): PirateShipDao
}
