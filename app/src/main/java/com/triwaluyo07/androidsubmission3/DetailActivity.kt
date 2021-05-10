package com.triwaluyo07.androidsubmission3

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.triwaluyo07.androidsubmission3.adapter.SectionPagerAdapter
import com.triwaluyo07.androidsubmission3.database.DatabaseContract
import com.triwaluyo07.androidsubmission3.database.DatabaseContract.FavColums.Companion.CONTENT_URI
import com.triwaluyo07.androidsubmission3.database.FavoriteHelper
import com.triwaluyo07.androidsubmission3.databinding.ActivityDetailBinding
import com.triwaluyo07.androidsubmission3.model.UserData
import com.triwaluyo07.androidsubmission3.model.UserDataFavorite
import com.triwaluyo07.androidsubmission3.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )


    }

    private lateinit var userData: UserData
    private lateinit var uriWithId : Uri

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData



        detailViewModel()
        pagerAdapter()
        setStatusFavorite()

        binding.btnFavorite.setOnClickListener {
            changeStatusFavorit(!isFav())
        }

    }


    fun detailViewModel()
    {
        detailViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.setUserDetail(userData.login.toString())

        detailViewModel.getUserDetail().observe(this,{
            if (it != null)
            {
                with(binding){
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl).apply(RequestOptions())
                        .override(80,80)
                        .into(imgAvatar)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = it.followers
                    tvFollowing.text = it.following
                    tvRepository.text = it.publicRepos
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                }
            }
        })
    }


    fun pagerAdapter()
    {

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = userData.login.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager)
        {
                tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }


    private fun setStatusFavorite()
    {

        val check = isFav()

        if(check)
        {
            binding.btnFavorite.setImageResource(R.drawable.ic_fav_red)
        }
        else
        {
            binding.btnFavorite.setImageResource(R.drawable.ic_fav_grey)
        }
    }

    private fun isFav() : Boolean{
        val cursor = contentResolver.query(CONTENT_URI,null, null, null, null)
        if(cursor != null)
        {
            val userList = MappingHelper.mapCursortoArrayList(cursor)
            for(n in userList) {
                if (n.login == userData.login){
                    return true
                }
            }
            cursor.close()
        }
        return false
    }

    private fun changeStatusFavorit(state: Boolean){
        val values = ContentValues()

        if(state){
            values.put(DatabaseContract.FavColums.COLUMN_AVATAR_URL, userData.avatarUrl)
            values.put(DatabaseContract.FavColums.COLUMN_USERNAME,userData.login)
            values.put(DatabaseContract.FavColums.COLUMN_URL,userData.url)

            contentResolver.insert(CONTENT_URI,values)
            Toast.makeText(this@DetailActivity,"FAVORITE SUCCESSED", Toast.LENGTH_SHORT).show()

            binding.btnFavorite.setImageResource(R.drawable.ic_fav_red)
        }

        else
        {
            uriWithId = Uri.parse(CONTENT_URI.toString()+"/${userData.login}")
            contentResolver.delete(uriWithId,null,null)
            Toast.makeText(this@DetailActivity,"FAVORITE CANCELED", Toast.LENGTH_SHORT).show()

            binding.btnFavorite.setImageResource(R.drawable.ic_fav_grey)
        }
    }
}