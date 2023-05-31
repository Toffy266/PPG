package com.example.predictpopularityofgame.util

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.predictpopularityofgame.MainActivity
import com.example.predictpopularityofgame.R

fun Fragment.getLoading() : Dialog {
    val builder = AlertDialog.Builder((activity as MainActivity))
    builder.setView(R.layout.progress)
    builder.setCancelable(false)
    val dialog = builder.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog
}