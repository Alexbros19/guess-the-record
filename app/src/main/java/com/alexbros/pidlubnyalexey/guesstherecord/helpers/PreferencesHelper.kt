package com.alexbros.pidlubnyalexey.guesstherecord.helpers

import android.content.Context
import android.preference.PreferenceManager

class PreferencesHelper(private val context: Context) {

    protected var preference = PreferenceManager.getDefaultSharedPreferences(context)
        private set

    companion object {
        private const val HIGHSCORE = "highscore"
        private const val HIGHSCORE_PERCENT_LEVEL = "highscore_percent_level"
    }

    private fun setInt(key: String, value: Int) {
        preference.edit().putInt(key, value).apply()
    }

    private fun getInt(key: String, value: Int): Int {
        return preference.getInt(key, value)
    }

    fun getHighscore(level: Int = 0): Int {
        return getInt(HIGHSCORE_PERCENT_LEVEL, level)
    }

}