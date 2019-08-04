package com.amir.alzheimer.infrastructure.dto

import android.view.View

data class DuplicateItem(val image: Int, var visibility: Array<Int>, var index: Array<Int>) {

    fun setCoverVisible() {
        visibility = arrayOf(View.VISIBLE, View.VISIBLE)
    }

    fun isFound(): Boolean {
        return visibility.none { it == View.VISIBLE }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DuplicateItem

        if (image != other.image) return false
        if (!visibility.contentEquals(other.visibility)) return false
        if (!index.contentEquals(other.index)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = image
        result = 31 * result + visibility.contentHashCode()
        result = 31 * result + index.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "ind: ${index[0]} ${index[1]} - vis: ${visibility[0]} ${visibility[1]} -- $image"
    }

    companion object {
        const val TAG = "DuplicateItem"
    }
}