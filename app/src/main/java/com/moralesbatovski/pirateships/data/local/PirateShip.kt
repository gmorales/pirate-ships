package com.moralesbatovski.pirateships.data.local

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Gustavo Morales
 *
 * Pirate Ship Room Entity.
 */
@Parcelize
@SuppressLint("ParcelCreator")
@Entity(indices = [(Index("id"))])
data class PirateShip(@SerializedName("id") @PrimaryKey val id: Long,
                      @SerializedName("title") val title: String?,
                      @SerializedName("description") val description: String,
                      @SerializedName("price") val price: Int,
                      @SerializedName("image") val image: String,
                      @SerializedName("greeting_type") val greetingType: String?) : Parcelable
