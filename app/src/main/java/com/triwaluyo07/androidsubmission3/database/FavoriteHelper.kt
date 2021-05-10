package com.triwaluyo07.androidsubmission3.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.COLUMN_USERNAME
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.TABLE_NAME
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion._ID

class FavoriteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE : FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper = INSTANCE ?: synchronized(this){
            INSTANCE ?: FavoriteHelper(context)
        }
    }

    // get access to write database
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    // close the database connection
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    //get all data in database
    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                null)
    }

    //get data by id
    fun queryById(id: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$_ID = ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }

    //save data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    //update data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$COLUMN_USERNAME = '$id'", null)
    }

}