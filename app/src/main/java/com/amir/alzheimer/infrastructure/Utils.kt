package com.amir.alzheimer.infrastructure

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.amir.alzheimer.base.BaseActivity

object Utils {
    private val TAG = "Utils"

    fun expand(v: View, expandButton: View?, height: Int) {
        //        Log.e(TAG, "expand: targetHeight before");
        //        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //        final int targetHeight = v.getMeasuredHeight();
        //        Log.e(TAG, "expand: targetHeight " + targetHeight);


        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    ViewGroup.LayoutParams.WRAP_CONTENT
                else
                    (height * interpolatedTime).toInt()//                                : (int) (targetHeight * interpolatedTime);
                v.requestLayout()
                if (expandButton != null)
                    expandButton.rotation = interpolatedTime * 180 + 180

            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        //        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.duration = Constants.ANIMATION_DURATION - 100

        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

                v.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {}

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        v.startAnimation(a)
    }

    fun collapse(v: View, expandButton: View?, callback: CollapseCallback?) {
        val initialHeight = v.measuredHeight

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()

                    if (expandButton != null)
                        expandButton.rotation = interpolatedTime * 180

                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        //        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.duration = Constants.ANIMATION_DURATION - 100
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                callback?.collapseEnded()
                v.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        v.startAnimation(a)
    }

    fun isPersian(string: String): Boolean {
        for (i in 0 until string.length) {
            val charAsciiNum = string[i].toInt()
            if (charAsciiNum in 1576..1640 || charAsciiNum == 1662 || charAsciiNum == 1711 || charAsciiNum == 1670 || charAsciiNum == 1688)
                return true
        }
        return false
    }

    fun gainPermission(activity: BaseActivity, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    fun checkPermission(activity: BaseActivity): Boolean {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED else true
    }
}
