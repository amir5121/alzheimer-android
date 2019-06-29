package com.amir.alzheimer.infrastructure.database.relative

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amir.alzheimer.infrastructure.database.AlzhimerDatabase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Entity(tableName = "relative_table")
data class Relative(

        var title: String,

        var imagesDir: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {

        fun addPerson(firstName: String, lastName: String) {
            val person = Relative(firstName, lastName)

            Single.fromCallable {
                AlzhimerDatabase.getDatabase(null).relativeDao().insert(person)
            }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe()
        }

    }
}