package com.zoopzam.photofinder.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeItem(
        @SerializedName("label") var label: String? = null,
        @SerializedName("item_type") var itemType: String? = null,
        @SerializedName("folder_path") var folderPath: String? = null,
        @SerializedName("photo_url") var photoUrl: String? = null
) : Parcelable
