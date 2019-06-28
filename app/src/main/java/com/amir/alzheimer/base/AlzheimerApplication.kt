package com.amir.alzheimer.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build

import com.amir.alzheimer.infrastructure.Database
import java.util.*

class AlzheimerApplication : Application() {

    lateinit var database: Database

    override fun onCreate() {
        super.onCreate()
        database = Database(this)
    }

//    fun updateResources(context: Context) {
//        val locale = Locale("fa")
//        Locale.setDefault(locale)
//
//        val res = context.resources
//        val config = Configuration(res.configuration)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLocale(locale)
//        } else {
//            config.locale = locale
//            res.updateConfiguration(config, res.displayMetrics)
//        }
//    }

}
