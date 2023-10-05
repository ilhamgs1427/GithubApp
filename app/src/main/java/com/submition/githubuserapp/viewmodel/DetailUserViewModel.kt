package com.submition.githubuserapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.Dicoding.appgithubuser.model.ResponseDetailUser
import com.submition.githubuserapp.api.ApiConfig
import com.submition.githubuserapp.data.local.FavoritUserDao
import com.submition.githubuserapp.data.local.FavoriteUser
import com.submition.githubuserapp.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDetail = MutableLiveData<ResponseDetailUser>()
    val getUserDetail: LiveData<ResponseDetailUser> = userDetail

    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading

    private var userDao: FavoritUserDao?
    private var userDb:  UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun detailUser(username: String) {
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().detailUser(username)
            client.enqueue(object : Callback<ResponseDetailUser> {
                override fun onResponse(
                    call: Call<ResponseDetailUser>,
                    response: Response<ResponseDetailUser>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        userDetail.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun addToFavorite(username: String,id: Int,avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                id,
                username,
                avatarUrl

            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)
    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    companion object {
        private const val TAG = "DetailUserViewModel"
    }

}