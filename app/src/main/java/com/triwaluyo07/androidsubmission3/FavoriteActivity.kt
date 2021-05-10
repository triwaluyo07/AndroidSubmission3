package com.triwaluyo07.androidsubmission3

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.triwaluyo07.androidsubmission3.adapter.FavoriteAdapter
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.CONTENT_URI
import com.triwaluyo07.androidsubmission3.database.FavoriteHelper
import com.triwaluyo07.androidsubmission3.databinding.ActivityFavoriteBinding
import com.triwaluyo07.androidsubmission3.model.UserData
import com.triwaluyo07.androidsubmission3.model.UserDataFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private var listUser: ArrayList<UserData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerList()


        if(savedInstanceState == null)
        {
            loadFavAsync()
        }else
        {
            val list = savedInstanceState.getParcelableArrayList<UserDataFavorite>(DetailActivity.EXTRA_DETAIL)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }

    private fun showRecyclerList() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)

        binding.rvFavorite.setHasFixedSize(true)
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFavorite.adapter = adapter


        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserDataFavorite) {
                ///tuliskan kode intent parcelable disini
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: UserDataFavorite) {
        val moveToDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
        val userData  = UserData(data.login, data.avatarUrl, data.url)
        moveToDetail.putExtra(DetailActivity.EXTRA_DETAIL, userData)
        startActivity(moveToDetail)
    }

    private fun loadFavAsync(){
        GlobalScope.launch (Dispatchers.Main){

            val defferedFavs = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI,null, null, null, null)
                MappingHelper.mapCursortoArrayList(cursor)
            }

            val faves = defferedFavs.await()
            if(faves.size > 0 ){
                adapter.listFavorite = faves
            }
            else{
                adapter.listFavorite = ArrayList()
                Toast.makeText(this@FavoriteActivity,"Empty Favorite", Toast.LENGTH_SHORT).show()
            }

        }
    }

    // run this func when open again for refresh data
    override fun onResume() {
        super.onResume()
        loadFavAsync()
    }
}