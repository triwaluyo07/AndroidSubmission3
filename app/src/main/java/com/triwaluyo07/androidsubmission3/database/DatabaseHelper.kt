package com.triwaluyo07.androidsubmission3.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.TABLE_NAME
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.COLUMN_AVATAR_URL
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.COLUMN_URL
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.COLUMN_USERNAME
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion._ID

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "dbuserfavorit"
        private const val DATABASE_VERSION = 4

        private const val SQL_CREATE_TABLE_USER_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " ($COLUMN_AVATAR_URL TEXT NOT NULL," +
                " $COLUMN_USERNAME TEXT PRIMARY KEY," +
                " $COLUMN_URL TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}