package com.amir.alzheimer.base

import android.app.Application
import com.amir.alzheimer.infrastructure.database.AlzhimerDatabase

class AlzheimerApplication : Application() {
    var database: AlzhimerDatabase? = null
    override fun onCreate() {
        super.onCreate()
        database = AlzhimerDatabase.getDatabase(this)
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
