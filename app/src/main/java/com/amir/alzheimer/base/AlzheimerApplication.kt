package com.amir.alzheimer.base

import android.app.Application
import android.util.Log
import com.amir.alzheimer.infrastructure.database.AlzhimerDatabase
import com.amir.alzheimer.infrastructure.database.user.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class AlzheimerApplication : Application() {
    var database: AlzhimerDatabase? = null
    var compositeDisposable = CompositeDisposable()
    var user: User? = null

    override fun onCreate() {
        super.onCreate()
        database = AlzhimerDatabase.getDatabase(this)
        updateUser()

    }

    fun updateUser() {
        database!!.userDao().getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { user ->
                    Log.wtf(TAG, user.toString())
                    this.user = user
                }.addTo(compositeDisposable)
    }

    companion object {
        private const val TAG = "AlzhimerApplication"
    }

}
