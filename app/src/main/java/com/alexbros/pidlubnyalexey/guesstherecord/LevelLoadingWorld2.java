package com.alexbros.pidlubnyalexey.guesstherecord;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

public class LevelLoadingWorld2 extends LevelsFunctionsClass {

	private TextView starsCountDisplay;
	private int numberStarsWorld2;
	private ImageButton level13Image;
	private ImageButton level14Image;
	private ImageButton level15Image;
	private ImageButton level16Image;
	private ImageButton level17Image;
	private ImageButton level18Image;
	private ImageButton level19Image;
	private ImageButton level20Image;
	private ImageButton level21Image;
	private ImageButton level22Image;
	private ImageButton level23Image;
	private ImageButton level24Image;
	private ImageButton backWorld1Btn;
	private final int levelsCount = 24;
	private boolean continueMusic;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels_world2);
		backWorld1Btn = (ImageButton) findViewById(R.id.backToWorld1);
		// Run function adding banner ad
		AddingMobileAd();

		starsCountDisplay = (TextView) findViewById(R.id.starsCounterWorld2);
		level13Image = (ImageButton) findViewById(R.id.btn_level13);
		level14Image = (ImageButton) findViewById(R.id.btn_level14);
		level15Image = (ImageButton) findViewById(R.id.btn_level15);
		level16Image = (ImageButton) findViewById(R.id.btn_level16);
		level17Image = (ImageButton) findViewById(R.id.btn_level17);
		level18Image = (ImageButton) findViewById(R.id.btn_level18);
		level19Image = (ImageButton) findViewById(R.id.btn_level19);
		level20Image = (ImageButton) findViewById(R.id.btn_level20);
		level21Image = (ImageButton) findViewById(R.id.btn_level21);
		level22Image = (ImageButton) findViewById(R.id.btn_level22);
		level23Image = (ImageButton) findViewById(R.id.btn_level23);
		level24Image = (ImageButton) findViewById(R.id.btn_level24);

		ChangeImage(level13Image, 13);
		ChangeImage(level14Image, 14);
		ChangeImage(level15Image, 15);
		ChangeImage(level16Image, 16);
		ChangeImage(level17Image, 17);
		ChangeImage(level18Image, 18);
		ChangeImage(level19Image, 19);
		ChangeImage(level20Image, 20);
		ChangeImage(level21Image, 21);
		ChangeImage(level22Image, 22);
		ChangeImage(level23Image, 23);
		ChangeImage(level24Image, 24);

		UpdateStarsScore();
	}

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(LocaleHelper.onAttach(base));
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		continueMusic = true;
		Intent i = new Intent(LevelLoadingWorld2.this, LevelsLoading.class);
		startActivity(i);
	}

	@Override
	protected void onResume(){
		super.onResume();
		mAdView.resume();

		SharedPreferences sharedPreferences = getSharedPreferences("musicflag", MODE_PRIVATE);

		if(!sharedPreferences.getBoolean("flag", Boolean.parseBoolean(null))){
			MusicManager.release();
		}else{
			continueMusic = false;
			MusicManager.start(this, MusicManager.MUSIC_MENU);
		}
	}

	@Override
	protected void onPause(){
		super.onPause();
		mAdView.pause();
		if (!continueMusic) {
			MusicManager.pause();
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		mAdView.destroy();
	}

	public void OnClickBackToMenu(View view){
		continueMusic = true;
		Intent i = new Intent(LevelLoadingWorld2.this, MainMenu.class);
		startActivity(i);
	}

	public void BackToWorld1(View view){
		ScaleAnimation anim = new ScaleAnimation(1.0f, 0.75f, 1.0f, 0.75f, 50, 50);
		anim.setDuration(100);
		anim.setFillAfter(true);
		anim.setRepeatCount(1);
		anim.setRepeatMode(Animation.REVERSE);
		backWorld1Btn.startAnimation(anim);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				continueMusic = true;
			Intent i = new Intent(LevelLoadingWorld2.this, LevelsLoading.class);
			startActivity(i);
			}
		}, 100);
	}

	private void UpdateStarsScore(){

		int temp = 0;
		numberStarsWorld2 = 0;

		for(int i = 13; i <= levelsCount; i++){
			SharedPreferences sharedPreferences = getSharedPreferences("level" + i, MODE_PRIVATE);
			temp = sharedPreferences.getInt("number" + i, 0);
			if(temp == 1){
				numberStarsWorld2 += 3;}
			if(temp == 2){
				numberStarsWorld2 += 2;}
			if(temp == 3){
				numberStarsWorld2 += 1;}
		}
		starsCountDisplay.setText(numberStarsWorld2 + "");
	}
	//-------------------------DATA TRANSMISSION ON CLICK LEVEL BUTTON------------------------------
	public void Level13(View view){LevelButton(500000, 13, level13Image);}
	public void Level14(View view){LevelButton(148, 14, level14Image);}
	public void Level15(View view){LevelButton(77, 15, level15Image);}
	public void Level16(View view){
		LevelButton(120, 16, level16Image);
	}
	public void Level17(View view){
		LevelButton(200, 17, level17Image);
	}
	public void Level18(View view){
		LevelButton(2164, 18, level18Image);
	}
	public void Level19(View view){
		LevelButton(19, 19, level19Image);
	}
	public void Level20(View view){
		LevelButton(121, 20, level20Image);
	}
	public void Level21(View view){
		LevelButton(8273, 21, level21Image);
	}
	public void Level22(View view){
		LevelButton(72585, 22, level22Image);
	}
	public void Level23(View view){
		LevelButton(50, 23, level23Image);
	}
	public void Level24(View view){LevelButton(56980, 24, level24Image);}
	//-----------------------FUNCTION WHICH SETS TO GAME MODE REAL RECORD AND NUMBER LEVEL VALUES----------------------
	private void LevelButton(final int recordValue, final int numLvl, ImageButton imgBtn){

		ScaleAnimation anim = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, 50, 50);
		anim.setDuration(100);
		anim.setFillAfter(true);
		imgBtn.startAnimation(anim);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				continueMusic = true;
				Intent intent = new Intent(LevelLoadingWorld2.this, GameActivity.class);
				intent.putExtra("realrecord", recordValue);
				intent.putExtra("numLvlToGameActivity", numLvl);
				startActivity(intent);
			}
		}, 100);
	}
}
