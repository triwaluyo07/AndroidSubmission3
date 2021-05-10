package com.triwaluyo07.androidsubmission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDataFavorite(
    val login: String?,
    val avatarUrl : String?,
    val url : String?
) : Parcelable