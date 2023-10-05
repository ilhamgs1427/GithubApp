package com.submition.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.Dicoding.appgithubuser.model.ResponseDetailUser
import com.submition.githubuserapp.adapter.FollowAdapter
import com.submition.githubuserapp.databinding.FragmentFollowersBinding
import com.submition.githubuserapp.viewmodel.MainViewModel
import com.submition.githubuserapp.databinding.FragmentFollowingBinding

import com.submition.githubuserapp.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private val viewModel: FollowingViewModel by viewModels()
    private val adapter = FollowAdapter()

    private  var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USER).toString()
        _binding = FragmentFollowingBinding.bind(view)
        viewModel.getIsLoading.observe(viewLifecycleOwner, this::showLoading)
        showViewModel()
        showRecyclerView()

    }

    private fun showViewModel() {
        if (username != null) {
            viewModel.following(username)
        }
        viewModel.getFollowing.observe(viewLifecycleOwner) { following ->
            if (following.size != 0) {
                adapter.setData(following)
            } else {
                Toast.makeText(context, "Following Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerView() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.adapter = adapter
        adapter.setOnItemClickCallback { data -> selectedUser(data) }
    }

    private fun selectedUser(user: ResponseDetailUser) {
        Toast.makeText(context, "You choose ${user.login}", Toast.LENGTH_SHORT).show()

        val i = Intent(activity, UserDetailActivity::class.java)
        i.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(i)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        refreshTabLayoutData()

    }
    private fun refreshTabLayoutData() {
        if (username != null) {
            viewModel.following(username)
        }
    }
}