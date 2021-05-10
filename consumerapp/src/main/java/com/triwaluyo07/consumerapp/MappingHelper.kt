package com.triwaluyo07.consumerapp

import android.database.Cursor
import com.triwaluyo07.consumerapp.database.DatabaseContract
import com.triwaluyo07.consumerapp.model.UserDataFavorite

object MappingHelper {
    fun mapCursortoArrayList(userCursor: Cursor?):ArrayList<UserDataFavorite>{
        val userList = ArrayList<UserDataFavorite>()

        userCursor?.apply {
            while(moveToNext()){
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavColums.COLUMN_USERNAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavColums.COLUMN_AVATAR_URL))
                val url = getString(getColumnIndexOrThrow(DatabaseContract.FavColums.COLUMN_URL))
                userList.add(UserDataFavorite(username,avatar,url))
            }
        }


        return userList
    }

//    fun mapCursorToObject(notesCursor: Cursor) : ArrayList<UserDataFavorite>
//    {
//        val userList = ArrayList<UserDataFavorite>()
//        notesCursor?.apply {
//            moveToFirst()
//            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavColums.COLUMN_USERNAME))
//            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavColums.COLUMN_AVATAR_URL))
//            val url = getString(getColumnIndexOrThrow(DatabaseContract.FavColums.COLUMN_URL))
//            userList.add(UserDataFavorite(username,avatar,url))
//        }
//        return userList
//    }

}