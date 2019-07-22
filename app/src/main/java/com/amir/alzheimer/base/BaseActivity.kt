package com.amir.alzheimer.base

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.util.*

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var application: AlzheimerApplication
    protected var displayWidth: Int = 0
    protected var displayHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = getApplication() as AlzheimerApplication

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayHeight = displayMetrics.heightPixels
        displayWidth = displayMetrics.widthPixels


        val locale = Locale("fa")
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.locale = locale
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(locale)
            config.setLocale(locale)
//            baseContext.createConfigurationContext(config)
        }

        baseContext.resources.updateConfiguration(config,
                baseContext.resources.displayMetrics)

    }
}
