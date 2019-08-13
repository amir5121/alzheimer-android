package com.amir.alzheimer.infrastructure.dto

import android.view.View

data class IndexedItem(val image: Int, var index: Int) {
    var text: String = ""
    var visibility: Int = View.VISIBLE

    constructor(text: String, index: Int) : this(0, index) {
        this.text = text
    }

    companion object {
        const val TAG = "DuplicateItem"
    }
}