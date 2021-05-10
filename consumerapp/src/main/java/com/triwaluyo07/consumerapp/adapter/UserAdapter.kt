package com.triwaluyo07.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.triwaluyo07.consumerapp.model.UserData
import com.triwaluyo07.consumerapp.databinding.ItemRowUserBinding

class UserAdapter(private var listUser: ArrayList<UserData>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {


    private var onItemClickCallback: OnItemClickCallback? = null


     var listFavorite = ArrayList<UserData>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        //mencocokan data dengan komponen
        fun bind(userData: UserData) {
            with(binding){
                Glide.with(itemView)
                    .load(userData.avatarUrl)
                    .centerCrop()
                    .apply(RequestOptions().override(60,60))
                    .into(imgAvatar)

                mainName.text = userData.login
                mainUrl.text = userData.url
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        //menghubungkan layout dengan item
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        //memberi data viewholder sesuai posisinya
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    //untuk jumlah item yang ditampilkan
    override fun getItemCount() : Int = listUser.size

    fun setList(users: ArrayList<UserData>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

}