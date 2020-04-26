package com.jfalck.hopworld.interfaces

import android.text.Editable
import android.text.TextWatcher

interface HopWorldTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        //do nothing
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        //do nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s?.toString())
    }

    fun onTextChanged(text: String?)
}