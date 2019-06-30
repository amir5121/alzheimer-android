package com.amir.alzheimer.infrastructure.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amir.alzheimer.infrastructure.database.AlzhimerDatabase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Entity(tableName = "user_table")
data class User(
        var username: String,
        var password: String,
        var firstName: String,
        var lastName: String,
        var jobTitle: String,
        var education: String,
        var birthYear: String,
        var childCount: Int
) {
    @PrimaryKey
    var id: Int = 1

    companion object {

//        fun addUser(user: User) {
//            Single.fromCallable {
//                if (AlzhimerDatabase.getDatabase().userDao().getUser() == null) {
//                    AlzhimerDatabase.getDatabase().userDao().insert(user)
//                }
//            }.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread()).subscribe()
//        }

    }
}