package com.amir.alzheimer.infrastructure


import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView

class AlzhimerTextView : AppCompatTextView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        this.typeface = Typeface.createFromAsset(context.assets, "Vazir.ttf")
    }
}
