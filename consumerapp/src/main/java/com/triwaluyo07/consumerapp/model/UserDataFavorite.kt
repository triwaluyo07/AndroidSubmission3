package com.triwaluyo07.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDataFavorite(
    val login: String?,
    val avatarUrl : String?,
    val url : String?
) : Parcelable