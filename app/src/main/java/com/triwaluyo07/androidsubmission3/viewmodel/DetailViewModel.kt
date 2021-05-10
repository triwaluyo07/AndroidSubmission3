package com.triwaluyo07.androidsubmission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.triwaluyo07.androidsubmission3.model.UserDataDetail
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class DetailViewModel: ViewModel() {

    val userDetail = MutableLiveData<UserDataDetail>()

    fun setUserDetail(username: String) {
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "token your_token")
        client.addHeader("User-Agent", "request")
        client.get(url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                //jika koneksi berhasil
                val result = String(responseBody)
                Log.d("success",result)
                try {
                    val jsonObject = JSONObject(result)
                    val login = jsonObject.getString("login")
                    val name = jsonObject.getString("name")
                    val avatar = jsonObject.getString("avatar_url")
                    val company= jsonObject.getString("company")
                    val location= jsonObject.getString("location")
                    val repository = jsonObject.getString("public_repos")
                    val followers = jsonObject.getString("followers")
                    val following = jsonObject.getString("following")

                     val UserDetail = UserDataDetail(
                                login,
                                avatar,
                                name,
                                following,
                                followers,
                                repository,
                                company,
                                location
                        )
                        userDetail.postValue(UserDetail)
                        Log.d("TidakAda",UserDetail.toString())

                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                //jika koneksi gagal
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

    fun getUserDetail() : LiveData<UserDataDetail>{
        return userDetail
    }
}
