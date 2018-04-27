package com.moralesbatovski.pirateships.testing

import android.support.annotation.VisibleForTesting
import com.moralesbatovski.pirateships.data.local.PirateShip
import com.moralesbatovski.pirateships.data.remote.PirateShipResponse

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {

    fun getPirateShipResponse(success: Boolean, pirateShips: List<PirateShip>) = PirateShipResponse(success, pirateShips)

    fun getPirateShip(id: Long, price: Int) = PirateShip(id,
            "title$id",
            "description$id",
            price,
            "image$id",
            "greetingType$id")
}