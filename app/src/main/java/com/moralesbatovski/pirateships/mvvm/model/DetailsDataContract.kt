package com.moralesbatovski.pirateships.mvvm.model

import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.networking.Outcome
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

/**
 * @author Gustavo Morales
 *
 * Detail Data Contract interface.
 */
interface DetailsDataContract {

    interface Repository {
        val pirateShipFetchOutcome: PublishSubject<Outcome<PirateShip>>
        fun fetchPirateShipFor(id: Long?)
        fun handleError(error: Throwable)
    }

    interface Local {
        fun getPirateShipForId(id: Long): Flowable<PirateShip>
    }
}