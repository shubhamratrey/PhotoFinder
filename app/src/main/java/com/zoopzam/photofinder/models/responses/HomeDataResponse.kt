package com.zoopzam.photofinder.models.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.zoopzam.photofinder.models.HomeItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  HomeDataResponse(
        @SerializedName("items") var items: ArrayList<HomeItem>?,
        @SerializedName("has_more") var hasMore: Boolean?) : Parcelable