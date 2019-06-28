package com.amir.alzheimer.base

import android.os.Bundle
import android.util.DisplayMetrics
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
        config.locale = locale
        baseContext.resources.updateConfiguration(config,
                baseContext.resources.displayMetrics)
    }
}
