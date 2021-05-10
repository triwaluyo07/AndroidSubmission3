package com.triwaluyo07.androidsubmission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.triwaluyo07.androidsubmission3.model.UserData
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<UserData>>()

    fun setListFollowing(username: String){
        val listUsers = ArrayList<UserData>()
        val url = "https://api.github.com/users/$username/following"
        val client = AsyncHttpClient()

        client.addHeader("Authorization", "token your_token")
        client.addHeader("User-Agent","request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray)
            {
                //jika berhasil
                val result = String(responseBody)
                Log.d("success",result)

                try
                {
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length())
                    {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val login = jsonObject.getString("login")
                        val avatarUrl = jsonObject.getString("avatar_url")
                        val url = jsonObject.getString("url")

                        val listFollowing = UserData(login,avatarUrl,url)
                        listUsers.add(listFollowing)
                    }
                    listFollowing.postValue(listUsers)
                    Log.d("TidakAda",listUsers.toString())
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable)
            {
                //jika gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("Failure", errorMessage)
            }

        })

    }

    fun getListFollowing() : LiveData<ArrayList<UserData>> {
        return listFollowing
    }

}
