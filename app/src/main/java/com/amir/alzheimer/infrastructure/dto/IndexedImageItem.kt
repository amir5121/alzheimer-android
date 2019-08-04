package com.amir.alzheimer.infrastructure.dto

import android.view.View

data class IndexedImageItem(val image: Int, var visibility: Int, var index: Int) {


    companion object {
        const val TAG = "DuplicateItem"
    }
}