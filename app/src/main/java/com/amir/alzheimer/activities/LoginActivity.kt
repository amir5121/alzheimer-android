package com.amir.alzheimer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.amir.alzheimer.R
import com.amir.alzheimer.base.BaseActivity
import com.amir.alzheimer.infrastructure.database.user.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            activity_login_button_login -> {
                var failed = false
                val inputs = arrayOf(activity_login_text_username,
                        activity_login_text_password,
                        activity_login_text_first_name,
                        activity_login_text_last_name,
                        activity_login_text_job_title,
                        activity_login_text_education_level,
                        activity_login_text_birth_year,
                        activity_login_text_child_count)
                for (child in inputs) {
                    if (child.text!!.isBlank()) {
                        child.error = getString(R.string.this_field_can_not_be_empty)
                        failed = true
                    }
                }

                if (application.user != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else if (!failed) {
                    Single.fromCallable {
                        application.database!!.userDao().insert(User(
                                username = activity_login_text_username.text.toString(),
                                password = activity_login_text_password.text.toString(),
                                firstName = activity_login_text_first_name.text.toString(),
                                lastName = activity_login_text_last_name.text.toString(),
                                jobTitle = activity_login_text_job_title.text.toString(),
                                education = activity_login_text_education_level.text.toString(),
                                birthYear = activity_login_text_birth_year.text.toString(),
                                childCount = activity_login_text_child_count.text.toString().toInt()
                        )
                        )
                    }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                                Log.wtf(TAG, it.toString())
                                application.updateUser()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            },
                                    {
                                        Log.wtf(TAG, it.toString())
                                    })
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        activity_login_button_login.setOnClickListener(this)

        if (application.user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }

}
