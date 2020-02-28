package com.alexbros.pidlubnyalexey.guesstherecord;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LevelsLoading extends LevelsFunctionsClass{

	private TextView starsCountDisplay;
	private int numberStars;
	private ImageButton nextLevelMenu;
	private ImageButton level1Image;
	private ImageButton level2Image;
	private ImageButton level3Image;
	private ImageButton level4Image;
	private ImageButton level5Image;
	private ImageButton level6Image;
	private ImageButton level7Image;
	private ImageButton level8Image;
	private ImageButton level9Image;
	private ImageButton level10Image;
	private ImageButton level11Image;
	private ImageButton level12Image;
	private boolean toastWorld2Flag = false;
	private boolean continueMusic;
	private final int LEVELS_COUNT = 12;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels);
		// Run function adding banner ad
		AddingMobileAd();

		starsCountDisplay = (TextView) findViewById(R.id.starsCounter);
		nextLevelMenu = (ImageButton) findViewById(R.id.nextLevelMenu);
		level1Image = (ImageButton) findViewById(R.id.btn_level1);
		level2Image = (ImageButton) findViewById(R.id.btn_level2);
		level3Image = (ImageButton) findViewById(R.id.btn_level3);
		level4Image = (ImageButton) findViewById(R.id.btn_level4);
		level5Image = (ImageButton) findViewById(R.id.btn_level5);
		level6Image = (ImageButton) findViewById(R.id.btn_level6);
		level7Image = (ImageButton) findViewById(R.id.btn_level7);
		level8Image = (ImageButton) findViewById(R.id.btn_level8);
		level9Image = (ImageButton) findViewById(R.id.btn_level9);
		level10Image = (ImageButton) findViewById(R.id.btn_level10);
		level11Image = (ImageButton) findViewById(R.id.btn_level11);
		level12Image = (ImageButton) findViewById(R.id.btn_level12);
		level1Image.setSoundEffectsEnabled(false);

		ChangeImage(level1Image, 1);
		ChangeImage(level2Image, 2);
		ChangeImage(level3Image, 3);
		ChangeImage(level4Image, 4);
		ChangeImage(level5Image, 5);
		ChangeImage(level6Image, 6);
		ChangeImage(level7Image, 7);
		ChangeImage(level8Image, 8);
		ChangeImage(level9Image, 9);
		ChangeImage(level10Image, 10);
		ChangeImage(level11Image, 11);
		ChangeImage(level12Image, 12);

		UpdateStarsScore();
	}

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(LocaleHelper.onAttach(base));
	}

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
		continueMusic = true;
        Intent i = new Intent(LevelsLoading.this, Menu.class);
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
	//--------------------------------------DESTROY AD----------------------------------------------
	@Override
	protected void onDestroy(){
		super.onDestroy();
		mAdView.destroy();
	}

	private void UpdateStarsScore(){

		int temp = 0;
		numberStars = 0;

		for(int i = 1; i <= LEVELS_COUNT; i++){
			SharedPreferences sharedPreferences = getSharedPreferences("level" + i, MODE_PRIVATE);
			temp = sharedPreferences.getInt("number" + i, 0);
			if(temp == 1){numberStars += 3;}
			if(temp == 2){numberStars += 2;}
			if(temp == 3){numberStars += 1;}
		}
		starsCountDisplay.setText(numberStars + "");
	}

	public void OnClickBackToMenu(View view){
        continueMusic = true;
		Intent i = new Intent(LevelsLoading.this, Menu.class);
		startActivity(i);
	}
	//-------------------------DATA TRANSMISSION ON CLICK LEVEL BUTTON------------------------------
	public void Level1(View view){LevelButton(40, 1, level1Image);}
	public void Level2(View view){LevelButton(2016, 2, level2Image);}
	public void Level3(View view){LevelButton(218792, 3, level3Image);}
	public void Level4(View view){
		LevelButton(855, 4, level4Image);
	}
	public void Level5(View view){
		LevelButton(35, 5, level5Image);
	}
	public void Level6(View view){
		LevelButton(1940, 6, level6Image);
	}
	public void Level7(View view){
		LevelButton(500, 7, level7Image);
	}
	public void Level8(View view){
		LevelButton(42173, 8, level8Image);
	}
	public void Level9(View view){
		LevelButton(1668, 9, level9Image);
	}
	public void Level10(View view){
		LevelButton(923, 10, level10Image);
	}
	public void Level11(View view){
		LevelButton(26, 11, level11Image);
	}
	public void Level12(View view){LevelButton(3737, 12, level12Image);}
	//-------------FUNCTION WHICH SETS TO GAME MODE REAL RECORD AND NUMBER LEVEL VALUES-------------
	private void LevelButton(final int recordValue, final int numLvl, ImageButton imgBtn){

		ScaleAnimation anim = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, 50, 50);
		anim.setDuration(100);
		anim.setFillAfter(true);
		imgBtn.startAnimation(anim);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				continueMusic = true;
				Intent intentGameActivity = new Intent(LevelsLoading.this, GameActivity.class);
				intentGameActivity.putExtra("realrecord", recordValue);
				intentGameActivity.putExtra("numLvlToGameActivity", numLvl);
				startActivity(intentGameActivity);
			}
		}, 100);
	}
	//----------------------------------LOADING SECOND WORLD----------------------------------------
	public void LoadingWorld2(View view){

		ScaleAnimation anim = new ScaleAnimation(1.0f, 0.75f, 1.0f, 0.75f, 50, 50);
		anim.setDuration(100);
		anim.setFillAfter(true);
		anim.setRepeatCount(1);
		anim.setRepeatMode(Animation.REVERSE);
		nextLevelMenu.startAnimation(anim);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

			if(numberStars >= 30){
				continueMusic = true;
				Intent intent = new Intent(LevelsLoading.this, LevelLoadingWorld2.class);
				startActivity(intent);
				toastWorld2Flag = false;
			}else {
				if(!toastWorld2Flag){
					Toast toast = Toast.makeText(getApplicationContext(),
							R.string.unlock_world2,
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();
					toastWorld2Flag = true;
				}
			}
			}
		}, 100);
	}
}
