package com.submition.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities =  [FavoriteUser :: class],
    version =  1
)
abstract class UserDatabase: RoomDatabase(){
    abstract fun favoriteUserDao(): FavoritUserDao

    companion object{
        var INSTANCE : UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase?{
            if(INSTANCE==null){
                synchronized(UserDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database").build()

                }
            }
            return INSTANCE
        }
    }
    }
