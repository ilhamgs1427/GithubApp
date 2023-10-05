package com.submition.githubuserapp.ui
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.Dicoding.appgithubuser.model.ResponseDetailUser
import com.submition.githubuserapp.adapter.FollowAdapter
import com.submition.githubuserapp.viewmodel.MainViewModel
import com.submition.githubuserapp.databinding.FragmentFollowersBinding
import com.submition.githubuserapp.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private val viewModel: FollowersViewModel by viewModels()
    private var adapter = FollowAdapter()
    private  var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USER).toString()
        _binding = FragmentFollowersBinding.bind(view)
        adapter = FollowAdapter()
        viewModel.getIsLoading.observe(viewLifecycleOwner, this::showLoading)
        showRecyclerView()
        showViewModel()
    }

    private fun showViewModel() {
        viewModel.followers(username)
        viewModel.getFollowers.observe(viewLifecycleOwner) { followers ->
            if (followers.size != 0) {
                adapter.setData(followers)
                Log.d("followers", followers.size.toString())
            } else {
                Toast.makeText(context, "Followers Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showRecyclerView() {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.setHasFixedSize(true)
        binding.rvFollowers.adapter = adapter
        adapter.setOnItemClickCallback { data -> selectedUser(data) }
    }



    private fun selectedUser(user: ResponseDetailUser) {
        Toast.makeText(context, "You choose ${user.login}", Toast.LENGTH_SHORT).show()
        val i = Intent(activity, UserDetailActivity::class.java)
        i.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(i)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
        viewModel.followers(username)
    }
}