package com.alexbros.pidlubnyalexey.guesstherecord;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.alexbros.pidlubnyalexey.guesstherecord.R.string.about;

public class MainMenu extends AppCompatActivity {
	private TextView rating;
	private ButtonPlus playButton;
	private ImageButton aboutButton;
	private ImageButton musicButton;
	private ImageButton languageButton;
    private ImageButton englishLanguageButton;
    private ImageButton russianLanguageButton;
	private boolean musicFlag = false;
    private boolean isChangeLanguage = true;
	private boolean isContinueMusic;
	private final int LEVELS_COUNT = 24;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		rating = (TextView) findViewById(R.id.rating);
		playButton = (ButtonPlus) findViewById(R.id.playbtn);
		aboutButton = (ImageButton) findViewById(R.id.aboutbtn);
		musicButton = (ImageButton) findViewById(R.id.musicbtn);
		languageButton = (ImageButton) findViewById(R.id.languagebtn);
        englishLanguageButton = (ImageButton) findViewById(R.id.change_on_english);
        russianLanguageButton = (ImageButton) findViewById(R.id.change_on_russian);

        englishLanguageButton.setVisibility(View.INVISIBLE);
        russianLanguageButton.setVisibility(View.INVISIBLE);

		// Play button animation
		TranslateAnimation translateAnim = new TranslateAnimation(-20, 20, 0, 0);
		translateAnim.setDuration(3000);
		translateAnim.setRepeatCount(Animation.INFINITE);
		translateAnim.setRepeatMode(Animation.REVERSE);
		playButton.startAnimation(translateAnim);

		// set a saved highscore value
		int percent = 0;
		SharedPreferences sharedPreferences = getSharedPreferences("highscore", MODE_PRIVATE);
		for (int i = 1; i <= LEVELS_COUNT; i++){percent += sharedPreferences.getInt("percent" + i, 0);}
		rating.setText(percent + "");

		SharedPreferences musicSharedPreferences = getSharedPreferences("musicflag", MODE_PRIVATE);
		musicFlag = musicSharedPreferences.getBoolean("flag", Boolean.parseBoolean(null));
		if(musicFlag){
			MusicManager.start(this, MusicManager.MUSIC_MENU);
			musicButton.setImageResource(R.drawable.musicon);
		}else if(!musicFlag){
			MusicManager.release();
			musicButton.setImageResource(R.drawable.musicoff);
		}
		musicFlag = !musicFlag;
	}
    //-------------------PAUSE AND RESUME MUSIC WHEN ACTIVITY LIFECYCLE IS CHANGED------------------
    @Override
	protected void onPause() {
		super.onPause();
		if (!isContinueMusic) {
			MusicManager.pause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences sharedPreferences = getSharedPreferences("musicflag", MODE_PRIVATE);
		musicFlag = sharedPreferences.getBoolean("flag", Boolean.parseBoolean(null));
		if(musicFlag){
			MusicManager.start(this, MusicManager.MUSIC_MENU);
			musicButton.setImageResource(R.drawable.musicon);
		}else if(!musicFlag){
			MusicManager.release();
			musicButton.setImageResource(R.drawable.musicoff);
		}
		musicFlag = !musicFlag;
	}

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

	public void ButtonMusic(View view){
		if(musicFlag){
			MusicManager.start(this, MusicManager.MUSIC_MENU);
			musicButton.setImageResource(R.drawable.musicon);
			musicFlag = false;
		}else if(!musicFlag){
			MusicManager.release();
			musicButton.setImageResource(R.drawable.musicoff);
			musicFlag = true;
		}
	}

	public void LoadLevelsMenu(View view){
		SharedPreferences sharedPreferences = getSharedPreferences("musicflag", MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean("flag", !musicFlag);
		editor.apply();

		isContinueMusic = true;
		Intent i = new Intent(MainMenu.this, LevelsLoading.class);
		startActivity(i);
	}
    //----------------------------------ON CLICK ABOUT BUTTON---------------------------------------
    public void buttonAboutClick(View view){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(about);
		builder.setMessage(R.string.aboutText);
		builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
    //--------------------------CHANGE LANGUAGE BUTTON FUNCTIONALITY--------------------------------
	public void buttonChangeLanguageClick(View view){
        if(isChangeLanguage){
            englishLanguageButton.setVisibility(View.VISIBLE);
            russianLanguageButton.setVisibility(View.VISIBLE);
            isChangeLanguage = false;
            languageButton.setImageResource(R.drawable.pressed_language_btn);
        }else if (!isChangeLanguage){
            englishLanguageButton.setVisibility(View.INVISIBLE);
            russianLanguageButton.setVisibility(View.INVISIBLE);
            isChangeLanguage = true;
            languageButton.setImageResource(R.drawable.change_language_btn);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public void onChangeToRUClicked(View view) {updateViews("ru");}

    public void onChangeToENClicked(View view) {
        updateViews("en");
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();

        playButton.setText(resources.getString(R.string.play_btn));
    }
}
