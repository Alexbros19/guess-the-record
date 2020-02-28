package com.alexbros.pidlubnyalexey.guesstherecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alexbros.pidlubnyalexey.guesstherecord.extensions.invisible
import com.alexbros.pidlubnyalexey.guesstherecord.extensions.visible
import com.alexbros.pidlubnyalexey.guesstherecord.helpers.DialogHelper
import com.alexbros.pidlubnyalexey.guesstherecord.helpers.PreferencesHelper
import kotlinx.android.synthetic.main.main_menu.*

class Menu : AppCompatActivity() {

    private lateinit var ratingTextView: TextView
    private lateinit var playButton: ButtonPlus
    private lateinit var aboutButton: ImageButton
    private lateinit var musicButton: ImageButton
    private lateinit var languageButton: ImageButton
    private lateinit var englishLanguageButton: ImageButton
    private lateinit var russianLanguageButton: ImageButton
    private var musicFlag = false
    private var isChangeLanguage = true
    private var isContinueMusic: Boolean = false
    private val LEVELS_COUNT = 24

    private val preferencesHelper: PreferencesHelper by lazy { PreferencesHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        ratingTextView = findViewById(R.id.rating)
        playButton = findViewById(R.id.play)
        aboutButton = findViewById(R.id.about)
        musicButton = findViewById(R.id.music)
        languageButton = findViewById(R.id.language)
        englishLanguageButton = findViewById(R.id.change_on_english)
        russianLanguageButton = findViewById(R.id.change_on_russian)

        // set a saved highscore value
        var percent = 0
        val sharedPreferences = getSharedPreferences("highscore", Context.MODE_PRIVATE)
        for (i in 1..LEVELS_COUNT) {
            percent += sharedPreferences.getInt("percent$i", 0)
        }
        rating.text = percent.toString()

        val musicSharedPreferences = getSharedPreferences("musicflag", Context.MODE_PRIVATE)
        musicFlag = musicSharedPreferences.getBoolean("flag", java.lang.Boolean.parseBoolean(null))
        if (musicFlag) {
            MusicManager.start(this, MusicManager.MUSIC_MENU)
            musicButton.setImageResource(R.drawable.musicon)
        } else if (!musicFlag) {
            MusicManager.release()
            musicButton.setImageResource(R.drawable.musicoff)
        }
        musicFlag = !musicFlag

        playButtonAnimation()
        playButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("musicflag", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("flag", !musicFlag)
            editor.apply()

            isContinueMusic = true
            val i = Intent(this@Menu, LevelsLoading::class.java)
            startActivity(i)
        }

        aboutButton.setOnClickListener {
            DialogHelper(this).showAboutDialog()
        }

        languageButton.setOnClickListener {
            buttonChangeLanguageClick()
        }

        englishLanguageButton.setOnClickListener {
            updateViews("en")
        }

        russianLanguageButton.setOnClickListener {
            updateViews("ru")
        }
    }

    override fun onPause() {
        super.onPause()
        if (!isContinueMusic) {
            MusicManager.pause()
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences("musicflag", Context.MODE_PRIVATE)
        musicFlag = sharedPreferences.getBoolean("flag", java.lang.Boolean.parseBoolean(null))
        if (musicFlag) {
            MusicManager.start(this, MusicManager.MUSIC_MENU)
            musicButton.setImageResource(R.drawable.musicon)
        } else if (!musicFlag) {
            MusicManager.release()
            musicButton.setImageResource(R.drawable.musicoff)
        }
        musicFlag = !musicFlag
    }

    private fun playButtonAnimation() {
        val translateAnim = TranslateAnimation(-20f, 20f, 0f, 0f)
        translateAnim.duration = 3000
        translateAnim.repeatCount = Animation.INFINITE
        translateAnim.repeatMode = Animation.REVERSE
        playButton.startAnimation(translateAnim)
    }

    fun buttonMusic() {
        if (musicFlag) {
            MusicManager.start(this, MusicManager.MUSIC_MENU)
            musicButton.setImageResource(R.drawable.musicon)
            musicFlag = false
        } else if (!musicFlag) {
            MusicManager.release()
            musicButton.setImageResource(R.drawable.musicoff)
            musicFlag = true
        }
    }

    fun buttonChangeLanguageClick() {
        if (isChangeLanguage) {
            englishLanguageButton.visible()
            russianLanguageButton.visible()
            languageButton.setImageResource(R.drawable.pressed_language_btn)
            isChangeLanguage = false
        } else if (!isChangeLanguage) {
            englishLanguageButton.invisible()
            russianLanguageButton.invisible()
            languageButton.setImageResource(R.drawable.change_language_btn)
            isChangeLanguage = true
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    private fun updateViews(languageCode: String) {
        val context = LocaleHelper.setLocale(this, languageCode)
        val resources = context.resources

        playButton.text = resources.getString(R.string.play_btn)
    }

}
