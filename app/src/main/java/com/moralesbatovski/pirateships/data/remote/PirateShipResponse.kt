package com.moralesbatovski.pirateships.data.remote

import com.google.gson.annotations.SerializedName
import com.moralesbatovski.pirateships.data.local.PirateShip

/**
 * @author Gustavo Morales
 *
 * Pirate Ships service response parser class.
 */
data class PirateShipResponse(@SerializedName("success") val success: Boolean,
                              @SerializedName("ships") val ships: List<PirateShip>)
