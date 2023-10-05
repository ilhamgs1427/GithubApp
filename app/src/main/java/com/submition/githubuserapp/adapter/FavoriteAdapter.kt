package com.submition.githubuserapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Dicoding.appgithubuser.model.Items
import com.bumptech.glide.Glide
import com.submition.githubuserapp.data.local.FavoriteUser
import com.submition.githubuserapp.databinding.CartUserBinding
import com.submition.githubuserapp.ui.UserDetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    private val listUser = ArrayList<FavoriteUser>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(data: ArrayList<FavoriteUser>) {
        listUser.clear()
        listUser.addAll(data)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CartUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                listUser[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ListViewHolder(private val _binding: CartUserBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bind(user: FavoriteUser) {
            _binding.tvUsername.text = user.login

            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .skipMemoryCache(true)
                .into(_binding.circleImageView)
        }
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }
}
