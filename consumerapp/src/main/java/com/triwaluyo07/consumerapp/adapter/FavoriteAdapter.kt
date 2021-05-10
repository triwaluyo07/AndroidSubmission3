package com.triwaluyo07.consumerapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.triwaluyo07.consumerapp.databinding.ItemRowUserBinding
import com.triwaluyo07.consumerapp.model.UserData
import com.triwaluyo07.consumerapp.model.UserDataFavorite

class FavoriteAdapter() : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {

    var listFavorite = ArrayList<UserDataFavorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    private var onItemClickCallback: FavoriteAdapter.OnItemClickCallback? = null


    fun setOnItemClickCallback(onItemClickCallback: FavoriteAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        //mencocokan data dengan komponen
        fun bind(userDataFavorite: UserDataFavorite) {
            with(binding){
                Glide.with(itemView)
                        .load(userDataFavorite.avatarUrl)
                        .centerCrop()
                        .apply(RequestOptions().override(60,60))
                        .into(imgAvatar)

                mainName.text = userDataFavorite.login
                mainUrl.text = userDataFavorite.url
            }
        }

    }
    interface OnItemClickCallback {
        fun onItemClicked(data: UserDataFavorite)
    }

    fun setList(users: ArrayList<UserDataFavorite>){
        listFavorite.clear()
        listFavorite.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        //menghubungkan layout dengan item
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        //memberi data viewholder sesuai posisinya
        holder.bind(listFavorite[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listFavorite[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int =listFavorite.size


}