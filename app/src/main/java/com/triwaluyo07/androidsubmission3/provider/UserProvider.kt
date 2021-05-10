package com.triwaluyo07.androidsubmission3.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.AUTHORITY
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.CONTENT_URI
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.TABLE_NAME
import com.triwaluyo07.androidsubmission3.database.FavoriteHelper

class UserProvider : ContentProvider() {

    companion object{
        private const val FAV = 1
        private const val FAV_ID = 2
        private const val CHECK_FAV = 3
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favHelper: FavoriteHelper
    }

    init {

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)

        sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAV_ID)
        sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", CHECK_FAV)


    }

    override fun onCreate(): Boolean {
        favHelper = FavoriteHelper.getInstance(context as Context)
        favHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)){
            FAV -> favHelper.queryAll()
            FAV_ID -> favHelper.queryById(uri.lastPathSegment.toString())

            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when(FAV){
            sUriMatcher.match(uri) -> favHelper.insert(values)
            else-> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }



    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        val updated: Int = when (FAV_ID){
            sUriMatcher.match(uri) -> favHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = favHelper.deleteById(uri.lastPathSegment.toString())
        Log.d("User Provider","Belum muncul : ${uri.lastPathSegment} ${sUriMatcher.match(uri)}")
        context?.contentResolver?.notifyChange(CONTENT_URI,null)

        return deleted
    }


}