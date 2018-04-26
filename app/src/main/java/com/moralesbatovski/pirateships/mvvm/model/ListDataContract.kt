package com.moralesbatovski.pirateships.mvvm.model

import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.data.remote.PirateShipResponse
import com.moralesbatovski.pirateships.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * @author Gustavo Morales
 *
 * List Data Contract interface.
 */
interface ListDataContract {

    interface Repository {
        val postFetchOutcome: PublishSubject<Outcome<List<PirateShip>>>
        fun fetchPirateShips()
        fun refreshPirateShips()
        fun savePirateShips(pirateShips: List<PirateShip>)
        fun handleError(error: Throwable)
    }

    interface Local {
        fun getPirateShips(): Flowable<List<PirateShip>>
        fun savePirateShips(pirateShips: List<PirateShip>)
    }

    interface Remote {
        fun getPirateShips(): Flowable<PirateShipResponse>
    }
}