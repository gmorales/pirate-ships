package com.moralesbatovski.pirateships.mvvm.model

import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.data.local.PirateShipDB
import io.reactivex.Flowable

/**
 * @author Gustavo Morales
 *
 * Details Local Data class.
 */
class DetailsLocalData(private val pirateShipDB: PirateShipDB) : DetailsDataContract.Local {

    override fun getPirateShipForId(id: Long): Flowable<PirateShip> {
        return pirateShipDB.pirateShipDao().getForId(id)
    }
}
