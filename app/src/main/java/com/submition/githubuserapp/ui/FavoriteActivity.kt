package com.submition.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submition.githubuserapp.adapter.FavoriteAdapter
import com.submition.githubuserapp.data.local.FavoriteUser
import com.submition.githubuserapp.databinding.ActivityFavoriteBinding
import com.submition.githubuserapp.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : FavoriteAdapter
    private  lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemClickCallback (
            object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: FavoriteUser) {
                    val i = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                    i.putExtra(UserDetailActivity.EXTRA_USER, data.login)
                    i.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    i.putExtra(UserDetailActivity.EXTRA_AVATAR,data.avatarUrl)
                    startActivity(i)
                }
            })
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }
        showViewModel()
        viewModel.getIsLoading.observe(this, this::showLoading)

    }

    private fun showViewModel() {
        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
             adapter.setData(list)
            } else {
                binding.rvUser.visibility = View.GONE
                Toast.makeText(this, "User Not Found!", Toast.LENGTH_SHORT).show()
            }
            showLoading(false)
        }
    }
    private  fun mapList(users: List<FavoriteUser>): ArrayList<FavoriteUser>{
        val listUsers = ArrayList<FavoriteUser>()
        for (user in users) {
            val userMapped = FavoriteUser(
                user.id,
                user.login,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}