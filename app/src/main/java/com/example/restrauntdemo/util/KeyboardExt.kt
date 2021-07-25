package com.example.restrauntdemo.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardExt {
    fun showKeyboard(context: Context) {
        val inputManager: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
    fun hideKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}