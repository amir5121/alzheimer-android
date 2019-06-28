package com.amir.alzheimer.base


import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    protected lateinit var application: AlzheimerApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.application = activity!!.application as AlzheimerApplication
    }
}
