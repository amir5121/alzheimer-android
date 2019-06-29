package com.amir.alzheimer.infrastructure.database.relative

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface RelativeDao {

    @Query("SELECT * FROM relative_table")
    fun allRelatives(): Flowable<List<Relative>>

    @Insert
    fun insert(relative: Relative)

    @Query("DELETE FROM relative_table")
    fun deleteAll()
}