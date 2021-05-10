package com.triwaluyo07.androidsubmission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.triwaluyo07.androidsubmission3.model.UserData
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

    val listUsersData = MutableLiveData<ArrayList<UserData>>()

    fun setSearchUser(username: String){
        val listUsers = ArrayList<UserData>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"
        client.addHeader("Authorization", "token your_token")
        client.addHeader("User-Agent","request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray)
            {
                //jika koneksi berhasil
                val result = String(responseBody)
                Log.d("success",result)

                try {
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length())
                    {
                        val jsonObject = item.getJSONObject(i)
                        val userItem = UserData(
                            jsonObject.getString("login"),
                            jsonObject.getString("avatar_url"),
                            jsonObject.getString("url")
                        )
                        listUsers.add(userItem)
                    }
                    listUsersData.postValue(listUsers)
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable)
            {
                //jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("failure", errorMessage)
            }

        })
    }

    fun getSearchUser() : LiveData<ArrayList<UserData>>{
        return listUsersData
    }
}
