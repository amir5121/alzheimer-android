package com.amir.alzheimer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseFragment
import com.amir.alzheimer.dialogFragment.ReminderDialogFragment
import com.amir.alzheimer.infrastructure.Constants
import com.amir.alzheimer.infrastructure.Utils
import com.tangxiaolv.telegramgallery.GalleryActivity
import com.tangxiaolv.telegramgallery.GalleryConfig
import kotlinx.android.synthetic.main.setting_fragment.*
import kotlinx.android.synthetic.main.setting_fragment.view.*

class SettingsFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.setting_fragment, container, false)
        view.setting_fragment_reminder_button.setOnClickListener(this)
        view.setting_fragment_relatives_button.setOnClickListener(this)
        view.setting_fragment_specialist_button.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View) {

        when (v) {
            setting_fragment_reminder_button -> {
                ReminderDialogFragment().show(fragmentManager!!, REMINDER_DIALOG_FRAGMENT)
            }
            setting_fragment_specialist_button -> {
            }
            setting_fragment_relatives_button -> {
                if (setting_fragment_reminder_button.visibility != View.GONE) {
                    setting_fragment_relative_s_name.visibility = View.VISIBLE
                    Utils.collapse(setting_fragment_reminder_button, null, null)
                    Utils.collapse(setting_fragment_specialist_button, null, null)
                    Utils.collapse(setting_fragment_add_new_header, null, null)


                } else {
                    if (setting_fragment_relative_s_name.text.isEmpty()) {
                        setting_fragment_relative_s_name.error = getString(R.string.fill_relative_name)
                    } else {

                        val config = GalleryConfig.Build()
                                .limitPickPhoto(3)
                                .singlePhoto(false)
//                            .hintOfPick("this is pick hint")
                                .filterMimeTypes(arrayOf("image/jpeg", "image/png", "image/jpg"))
                                .build()

                        GalleryActivity.openActivity(activity, Constants.IMAGE_REQUEST_CODE, config)

                    }
                }

            }
        }
    }


    fun relativesWasAdded(imageDirectories: String) {
        Log.e(TAG, setting_fragment_relative_s_name.text.toString())
        Log.e(TAG, imageDirectories)
        setting_fragment_relative_s_name.visibility = View.GONE
        setting_fragment_relatives_button.let {
            Utils.expand(setting_fragment_reminder_button, null, it.height)
            Utils.expand(setting_fragment_specialist_button, null, it.height)
            Utils.expand(setting_fragment_add_new_header, null, it.height)
        }

    }

    companion object {

        private const val REMINDER_DIALOG_FRAGMENT = "reminder_dialog_fragment"
        private const val TAG = "SettingsFragment"
    }
}
