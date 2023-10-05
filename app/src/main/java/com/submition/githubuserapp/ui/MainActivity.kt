package com.submition.githubuserapp.ui

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.submition.githubuserapp.databinding.ActivityMainBinding
import android.content.Intent
import android.content.res.Configuration
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.submition.githubuserapp.adapter.UserAdapter
import com.Dicoding.appgithubuser.model.Items
import com.submition.githubuserapp.viewmodel.MainViewModel
import com.submition.githubuserapp.R
import com.submition.githubuserapp.setting.SettingPreferences
import com.submition.githubuserapp.setting.dataStore

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(SettingPreferences.getInstance(dataStore))
    }
    private val adapter = UserAdapter()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showViewModel()
        showRecyclerView()
        viewModel.getIsLoading.observe(this, this::showLoading)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.favorite_menu -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                            startActivity(intent)
                            true
                        }
                    R.id.menu2 -> {
                        val intent = Intent(this@MainActivity,SwitchModeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                        else -> false
                }
            }
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBar.text = searchView.text
                    searchView.hide()
                    viewModel.setkeyword(binding.searchView.text.toString())
                    return@setOnEditorActionListener true
                }
                    false
                }
        }

    }

    private fun showViewModel() {
        viewModel.getSearchList.observe(this) { searchList ->
            if (searchList.size != 0) {
                binding.rvUser.visibility = View.VISIBLE
                adapter.setData(searchList)
            } else {
                binding.rvUser.visibility = View.GONE
                Toast.makeText(this, "User Not Found!", Toast.LENGTH_SHORT).show()
            }
            showLoading(false)
        }
        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showRecyclerView() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
        adapter.setOnItemClickCallback { data -> selectedUser(data) }
    }

    private fun selectedUser(user: Items) {
        Toast.makeText(this, "You choose ${user.login}", Toast.LENGTH_SHORT).show()
        val i = Intent(this, UserDetailActivity::class.java)
        i.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        i.putExtra(UserDetailActivity.EXTRA_ID, user.id)
        i.putExtra(UserDetailActivity.EXTRA_AVATAR,user.avatar_url)
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.searchView).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                viewModel.setkeyword(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}