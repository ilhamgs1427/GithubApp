package com.submition.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.Dicoding.appgithubuser.model.Items
import com.bumptech.glide.Glide
import com.submition.githubuserapp.databinding.CartUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    private val listUser = ArrayList<Items>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(data: ArrayList<Items>) {
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
        fun bind(user: Items) {
            _binding.tvUsername.text = user.login

            Glide.with(itemView.context)
                .load(user.avatar_url)
                .skipMemoryCache(true)
                .into(_binding.circleImageView)
        }
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(data: Items)
    }
}