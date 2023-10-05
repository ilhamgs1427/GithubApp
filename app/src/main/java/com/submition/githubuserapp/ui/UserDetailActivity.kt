package com.submition.githubuserapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.submition.githubuserapp.adapter.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submition.githubuserapp.R
import com.submition.githubuserapp.databinding.ActivityUserDetailBinding
import com.submition.githubuserapp.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    private val viewModel : DetailUserViewModel by viewModels()
    private lateinit var binding: ActivityUserDetailBinding


    private lateinit var username: String // Variabel untuk menampung username
    private var id = 0 // Variabel untuk menampung ID
    private lateinit var avatarUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        username = intent.getStringExtra(EXTRA_USER).toString()
        id = intent.getIntExtra(EXTRA_ID, 0)
        avatarUrl = intent.getStringExtra(EXTRA_AVATAR).toString()
        showViewModel()

        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    isChecked = count > 0
                    binding.toggleFavorite.isChecked = isChecked
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isChecked = !isChecked
            binding.toggleFavorite.isChecked = isChecked
            if (isChecked) {
                viewModel.addToFavorite(username, id, avatarUrl)
            } else {
                viewModel.removeFromFavorite(id)
            }
        }


        viewModel.getIsLoading.observe(this, this::showLoading)
    }


    private fun showViewModel() {
        viewModel.detailUser(username)
        viewModel.getUserDetail.observe(this) { detailUser ->
            Glide.with(this)
                .load(detailUser.avatar_url)
                .skipMemoryCache(true)
                .into(binding.circleImageView)
            binding.tvName.text = detailUser.name
            binding.tvUsername.text = detailUser.login
            binding.tvFollowers.text = "${detailUser.followers} Followers"
            binding.tvFollowing.text = "${detailUser.following} Following"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
//        var username = String()

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}