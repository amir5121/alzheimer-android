package com.amir.alzheimer.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseFragment
import com.amir.alzheimer.dialogFragment.ReminderDialogFragment
import com.amir.alzheimer.infrastructure.Utils
import com.amir.alzheimer.infrastructure.database.relative.Relative
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.setting_fragment.*
import kotlinx.android.synthetic.main.setting_fragment.view.*
import java.io.File


class SettingsFragment : BaseFragment(), View.OnClickListener, BSImagePicker.ImageLoaderDelegate, BSImagePicker.OnMultiImageSelectedListener {
    override fun loadImage(imageFile: File?, ivImage: ImageView?) {
        ivImage?.let { Glide.with(this).load(imageFile).into(it) }
    }

    override fun onMultiImageSelected(uriList: MutableList<Uri>?, tag: String?) {
        setting_fragment_relative_s_name.visibility = View.GONE
        setting_fragment_relatives_button.let {
            Utils.expand(setting_fragment_reminder_button, null, it.height)
            Utils.expand(setting_fragment_specialist_button, null, it.height)
            Utils.expand(setting_fragment_add_new_header, null, it.height)
        }

        if (uriList != null && setting_fragment_relative_s_name.text.isNotBlank()) {
            Relative.addRelative(setting_fragment_relative_s_name.text.toString(), uriList.joinToString(","))
        }
    }

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
                        //https://github.com/siralam/BSImagePicker?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6925
                        val multiSelectionPicker = BSImagePicker.Builder("com.amir.alzhimer.fileprovider")
                                .isMultiSelect
                                .disableOverSelectionMessage()
                                .build()

                        multiSelectionPicker.show(childFragmentManager, "picker")

                    }
                }

            }
        }
    }


    companion object {

        private const val REMINDER_DIALOG_FRAGMENT = "reminder_dialog_fragment"
        private const val TAG = "SettingsFragment"
    }
}
