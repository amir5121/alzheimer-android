package com.amir.alzheimer.infrastructure.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE id = 1")
    fun getUser(): Flowable<User>

    @Insert
    fun insert(relative: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()
}