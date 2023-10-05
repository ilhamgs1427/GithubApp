package com.submition.githubuserapp.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "username")
    var login: String,
    @ColumnInfo(name = "photo")
    var avatarUrl: String
): Parcelable