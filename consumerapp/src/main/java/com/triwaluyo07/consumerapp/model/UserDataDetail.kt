package com.triwaluyo07.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDataDetail(
        val login: String?,
        val avatarUrl : String?,
        val name: String?,
        val following: String?,
        val followers: String?,
        val publicRepos: String?,
        val company: String?,
        val location: String?
) : Parcelable