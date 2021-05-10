package com.triwaluyo07.androidsubmission3.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.triwaluyo07.androidsubmission3"
    const val SCHEME = "content"

    class FavColums : BaseColumns {
        companion object{
            const val TABLE_NAME = "favorite_user"
            const val _ID = "id"
            const val COLUMN_AVATAR_URL = "avatar_url"
            const val COLUMN_USERNAME = "username"
            const val COLUMN_URL = "url"


            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}