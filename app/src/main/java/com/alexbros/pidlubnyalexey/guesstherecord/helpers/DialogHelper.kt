package com.alexbros.pidlubnyalexey.guesstherecord.helpers

import android.app.AlertDialog
import android.content.Context
import com.alexbros.pidlubnyalexey.guesstherecord.R

class DialogHelper(private val context: Context) {

    companion object {
        private const val TAG = "DialogHelper"
    }

    fun showAboutDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.about)
        builder.setMessage(R.string.aboutText)
        builder.setNegativeButton(android.R.string.ok) { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

}