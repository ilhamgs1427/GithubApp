package com.submition.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.submition.githubuserapp.api.ApiConfig
import com.Dicoding.appgithubuser.model.Items
import com.Dicoding.appgithubuser.model.ResponseSearch
import com.submition.githubuserapp.setting.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {

    private val searchList = MutableLiveData<ArrayList<Items>>()
    val getSearchList: LiveData<ArrayList<Items>> = searchList

    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading

    private var filterData: MutableLiveData<String> = MutableLiveData()
    private var selectedUserId
            : String? = null

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    init{
        searchUser()
    }
    fun setSelectedUserId(username: String) {
        selectedUserId = username
    }

    private fun searchUser() {
        val keyword = filterData.value ?: "ilham"
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().search(keyword,1,100)
            client.enqueue(object : Callback<ResponseSearch> {
                override fun onResponse(
                    call: Call<ResponseSearch>,
                    response: Response<ResponseSearch>
                ) {
                    isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        searchList.value = ArrayList(responseBody.items)
                        if (searchList.value?.isNotEmpty() == true ){
                            setSelectedUserId(searchList.value!![0].login)
                        }
                    }
                    else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }
    fun setkeyword(Key: String){
        filterData.value = Key
        searchUser()
    }

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }

    companion object {
        internal const val TAG = "MainViewModel"
    }
}