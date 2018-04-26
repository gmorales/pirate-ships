package com.moralesbatovski.pirateships.data.remote

import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * @author Gustavo Morales
 *
 * Pirate Ship Rest API service call.
 */
interface PirateShipService {

    @GET("/pirateships")
    fun getPirateShips(): Flowable<PirateShipResponse>
}