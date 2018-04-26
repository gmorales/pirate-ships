package com.moralesbatovski.pirateships.mvvm.model

import com.moralesbatovski.pirateships.data.remote.PirateShipResponse
import com.moralesbatovski.pirateships.data.remote.PirateShipService
import io.reactivex.Flowable

/**
 * @author Gustavo Morales
 *
 * List remote Data class.
 */
class ListRemoteData(private val pirateShipService: PirateShipService) : ListDataContract.Remote {

    override fun getPirateShips(): Flowable<PirateShipResponse> {
        return pirateShipService.getPirateShips()
    }
}