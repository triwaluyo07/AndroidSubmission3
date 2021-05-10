package com.triwaluyo07.androidsubmission3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.triwaluyo07.androidsubmission3.adapter.UserAdapter
import com.triwaluyo07.androidsubmission3.databinding.ActivityMainBinding
import com.triwaluyo07.androidsubmission3.model.UserData
import com.triwaluyo07.androidsubmission3.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private var listUser: ArrayList<UserData> = ArrayList()
    private lateinit var viewModel: MainViewModel


    //by Tri Waluyo A0070678 ( a0070678@bangkit.academy )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        showRecyclerList()


    }

    private fun showRecyclerList() {
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        binding.rvMain.setHasFixedSize(true)
        adapter = UserAdapter(listUser)
        adapter.notifyDataSetChanged()

        binding.rvMain.adapter = adapter

        btnSearchUser()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                ///tuliskan kode intent parcelable disini
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: UserData) {
        val moveToDetail = Intent(this@MainActivity, DetailActivity::class.java)
        moveToDetail.putExtra(DetailActivity.EXTRA_DETAIL, data)
        startActivity(moveToDetail)
        }

    private fun btnSearchUser(){
        binding.btnSearch.setOnClickListener {
            searchUser()
        }
        binding.etSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
            {
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        viewModel.getSearchUser().observe(this,
                {
                    if (it!=null)
                    {
                        adapter.setList(it)
                        showLoading(false)
                    }
                })

    }

    private fun searchUser() {
        val query = binding.etSearch.text.toString()

        if (query.isNotEmpty())
        {
            showLoading(false)
            viewModel.setSearchUser(query)
            showLoading(true)
        }
        
    }

    private fun showLoading(state: Boolean){
        if(state)
        {
            binding.mainProgress.visibility = View.VISIBLE
        }
        else
        {
            binding.mainProgress.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId)
        {
            R.id.changeLanguage -> {
                Intent(Settings.ACTION_LOCALE_SETTINGS).also {
                    startActivity(it)
                }
            }

            R.id.fav_menu -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.setting_alarm -> {
                Intent(this,SettingActivity::class.java).also {
                    startActivity(it)
                }
            }

        }

        return super.onOptionsItemSelected(item)
    }
}


