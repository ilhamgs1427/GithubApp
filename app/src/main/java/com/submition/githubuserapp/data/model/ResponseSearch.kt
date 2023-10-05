package com.Dicoding.appgithubuser.model

import com.google.gson.annotations.SerializedName

data class ResponseSearch(

    @field:SerializedName("total_count")
    val total_count: Int,

    @field:SerializedName("incomplete_results")
    val incomplete_results: Boolean,

    @field:SerializedName("items")
    val items: List<Items>
)

data class Items(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatar_url: String,

)
