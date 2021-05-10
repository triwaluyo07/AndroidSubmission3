package com.triwaluyo07.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserData(
    val login: String?,
    val avatarUrl : String?,
    val url : String?

): Parcelable