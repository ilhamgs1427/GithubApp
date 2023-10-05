package com.submition.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Dicoding.appgithubuser.model.ResponseDetailUser
import com.submition.githubuserapp.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val following = MutableLiveData<ArrayList<ResponseDetailUser>>()
    val getFollowing: LiveData<ArrayList<ResponseDetailUser>> = following

    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading

    fun following(username: String) {
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().following(username,1,Int.MAX_VALUE)
            client.enqueue(object : Callback<ArrayList<ResponseDetailUser>> {
                override fun onResponse(
                    call: Call<ArrayList<ResponseDetailUser>>,
                    response: Response<ArrayList<ResponseDetailUser>>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        following.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseDetailUser>>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }
    companion object {
        private const val TAG = "FollowingViewModel"
    }
}