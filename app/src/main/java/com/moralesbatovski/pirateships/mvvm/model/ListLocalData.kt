package com.moralesbatovski.pirateships.mvvm.model

import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.data.local.PirateShipDB
import com.moralesbatovski.pirateships.networking.Scheduler
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * @author Gustavo Morales
 *
 * List Local Data class.
 */
class ListLocalData(private val pirateShipDB: PirateShipDB, private val scheduler: Scheduler) : ListDataContract.Local {

    override fun getPirateShips(): Flowable<List<PirateShip>> {
        return pirateShipDB.pirateShipDao().getAll()
    }

    override fun savePirateShips(pirateShips: List<PirateShip>) {

        Completable.fromAction {
            pirateShipDB.pirateShipDao().upsertAll(pirateShips)
        }
                .subscribeOn(scheduler.io())
                .subscribe()
    }
}
