package com.triwaluyo07.androidsubmission3.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.triwaluyo07.androidsubmission3.DetailActivity
import com.triwaluyo07.androidsubmission3.R
import com.triwaluyo07.androidsubmission3.adapter.UserAdapter
import com.triwaluyo07.androidsubmission3.databinding.FragmentFollowerBinding
import com.triwaluyo07.androidsubmission3.model.UserData
import com.triwaluyo07.androidsubmission3.viewmodel.FollowerViewModel
import com.triwaluyo07.androidsubmission3.viewmodel.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_follower) {

    companion object {
        const val EXTRA_DATA = "extra_data"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_DATA,username)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private var listUser: ArrayList<UserData> = ArrayList()
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mengambil username
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_DETAIL).toString()

        //binding
        _binding = FragmentFollowerBinding.bind(view)

        //inisialisasi
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)

        showRecyclerList()

        showLoading(false)
        viewModel.setListFollowing(args?.getString(FollowerFragment.EXTRA_DATA).toString())
        showLoading(true)

        viewModel.getListFollowing().observe(viewLifecycleOwner,{
            if(it != null)
            {
                showLoading(false)
                adapter.setList(it)

            }
        })

    }

    private fun showRecyclerList() {
        binding.rvMain.layoutManager = LinearLayoutManager(activity)

        binding.rvMain.setHasFixedSize(true)
        adapter = UserAdapter(listUser)
        adapter.notifyDataSetChanged()

        binding.rvMain.adapter = adapter


        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                ///tuliskan kode intent parcelable disini
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: UserData) {
        val moveToDetail = Intent(activity, DetailActivity::class.java)
        moveToDetail.putExtra(DetailActivity.EXTRA_DETAIL, data)
        startActivity(moveToDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.followProgress.visibility = View.VISIBLE
        } else {
            binding.followProgress.visibility = View.GONE
        }

    }

}