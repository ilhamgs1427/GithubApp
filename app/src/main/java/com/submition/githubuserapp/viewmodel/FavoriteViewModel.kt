package com.submition.githubuserapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submition.githubuserapp.data.local.FavoritUserDao
import com.submition.githubuserapp.data.local.FavoriteUser
import com.submition.githubuserapp.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoritUserDao?
    private var userDb: UserDatabase?

    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }


    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        isLoading.value = true
        return userDao?.getFavoriteUser()
        isLoading.value = false
    }
}