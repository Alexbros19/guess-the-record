package com.alexbros.pidlubnyalexey.guesstherecord;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class ProgressBarDisplay extends Activity {

	private InterstitialAd interstitial;
	private int stopDownload;
	private int levelNumber;
	private int paused;
	private final int NUMBER_OF_LEVELS = 12;
	private final static int MAX_VOLUME = 10;
    private boolean continueMusic;
	private ImageView bronzeImage;
	private ImageView silverImage;
	private ImageView goldImage;
	private ImageButton restartButton;
	private ImageButton levelMenuButton;
	private TextView highScore;
	private MediaPlayer progressBarSound;
	private MediaPlayer starsAppearingSound;
	private MediaPlayer winSound;
	private MediaPlayer loseSound;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar_result);
		//-----------------------ADD MOBILE INTERSTITIAL AD--------------------------------------------------------
		MobileAds.initialize(getApplicationContext(), "ca-app-pub-3889824584026064~5350305632");
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-3889824584026064/7098920435");
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		interstitial.loadAd(adRequest);

		bronzeImage = (ImageView) findViewById(R.id.bronzeStar);
		silverImage = (ImageView) findViewById(R.id.silverStar);
		goldImage = (ImageView) findViewById(R.id.goldStar);
		restartButton = (ImageButton) findViewById(R.id.restartBtn);
		levelMenuButton = (ImageButton) findViewById(R.id.levelListBtn);
		highScore = (TextView) findViewById(R.id.levelHighScore);
		final MeterView meter = (MeterView) this.findViewById(R.id.meter);
		meter.runAsync(false);

		//-----------------------ONCLICK RESTART AND LEVEL MENU BUTTONS--------------------------------------------------
		restartButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				RestartButtonFunctionality();
			}
		});

		levelMenuButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				if (interstitial.isLoaded()) {
					interstitial.show();
                    //BackToMenuFunctionality();
				}else {
                    BackToMenuFunctionality();
				}
			}
		});

		stopDownload = getIntent().getIntExtra("percent", 0);
		Function(meter, stopDownload);
		SaveLevelsHighscore();

		// pause main menu music
		MusicManager.pause();
	}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(LocaleHelper.onAttach(base));
	}
	//-------------------------------RESTART BUTTON FUNCTIONALITY-----------------------------------
	public void RestartButtonFunctionality(){
        levelNumber = getIntent().getIntExtra("lvlNumToProgressBar", 0);
		stopProgressBarSound();

        ScaleAnimation anim = new ScaleAnimation(1.0f, 0.75f, 1.0f, 0.75f, 50, 50);
        anim.setDuration(100);
        anim.setFillAfter(true);
        restartButton.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (levelNumber){
                    case 1: LoadDataRestartLevel(40, 1); break;
                    case 2: LoadDataRestartLevel(2016, 2); break;
                    case 3: LoadDataRestartLevel(218792, 3); break;
                    case 4: LoadDataRestartLevel(855, 4); break;
                    case 5: LoadDataRestartLevel(35, 5); break;
                    case 6: LoadDataRestartLevel(1940, 6); break;
                    case 7: LoadDataRestartLevel(500, 7); break;
                    case 8: LoadDataRestartLevel(42173, 8); break;
                    case 9: LoadDataRestartLevel(1668, 9); break;
                    case 10: LoadDataRestartLevel(923, 10); break;
                    case 11: LoadDataRestartLevel(26, 11); break;
                    case 12: LoadDataRestartLevel(3737, 12); break;
                    case 13: LoadDataRestartLevel(500000, 13); break;
                    case 14: LoadDataRestartLevel(148, 14); break;
                    case 15: LoadDataRestartLevel(77, 15); break;
                    case 16: LoadDataRestartLevel(120, 16); break;
                    case 17: LoadDataRestartLevel(200, 17); break;
                    case 18: LoadDataRestartLevel(2164, 18); break;
                    case 19: LoadDataRestartLevel(19, 19); break;
                    case 20: LoadDataRestartLevel(121, 20); break;
                    case 21: LoadDataRestartLevel(8273, 21); break;
                    case 22: LoadDataRestartLevel(72585, 22); break;
                    case 23: LoadDataRestartLevel(50, 23); break;
                    case 24: LoadDataRestartLevel(56980, 24); break;
                    default:break;
                }
            }
        }, 100);
    }

    public void BackToMenuFunctionality(){
		stopProgressBarSound();

        ScaleAnimation anim = new ScaleAnimation(1.0f, 0.75f, 1.0f, 0.75f, 50, 50);
        anim.setDuration(100);
        anim.setFillAfter(true);
        levelMenuButton.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//------------------------------------CHECK BACK IN THE MENU----------------------------------------
				continueMusic = true;
                levelNumber = getIntent().getIntExtra("lvlNumToProgressBar", 0);
                if(levelNumber > 0 && levelNumber <= NUMBER_OF_LEVELS){
                    Intent intent = new Intent(ProgressBarDisplay.this, LevelsLoading.class);
                    startActivity(intent);
                } else{
                    Intent intent = new Intent(ProgressBarDisplay.this, LevelLoadingWorld2.class);
                    startActivity(intent);
                }

            }
        }, 100);
    }

	public void BackToLevelList(View view){
		stopProgressBarSound();

		if (interstitial.isLoaded()) {
			interstitial.show();
		}else {
			levelNumber = getIntent().getIntExtra("lvlNumToProgressBar", 0);
			if (levelNumber > 0 && levelNumber <= NUMBER_OF_LEVELS) {
				Intent intent = new Intent(ProgressBarDisplay.this, LevelsLoading.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(ProgressBarDisplay.this, LevelLoadingWorld2.class);
				startActivity(intent);
			}
		}
	}

	//-----------------------WORK WITH SOUNDS--------------------------------------------------------
	public void playProgressBarSound(){
		if(progressBarSound == null){
			progressBarSound = MediaPlayer.create(ProgressBarDisplay.this, R.raw.percent_sound);
			progressBarSound.setLooping(true);

			final float volume = (float) (1 - (Math.log(MAX_VOLUME - 5) / Math.log(MAX_VOLUME)));
			progressBarSound.setVolume(volume, volume);

			progressBarSound.start();
		}
	}

	public void playStarsAppearingSound(){
		if(starsAppearingSound == null){
			starsAppearingSound = MediaPlayer.create(ProgressBarDisplay.this, R.raw.star_sound);
			//starsAppearingSound.setLooping(true);
			starsAppearingSound.start();
		}
	}

	public void stopProgressBarSound(){
		progressBarSound.release();
	}

	//-----------------------PROGRESS BAR FUNCTION--------------------------------------------------------
	public void Function(final MeterView meterview, final int stop){
		Thread thread = new Thread(new Runnable() {
			public void run() {

				Intent intent = new Intent();

				for (int i = 0; i <= stop; i++) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) { }

					final int n = i;
					runOnUiThread(new Runnable() {
						public void run() {
							meterview.setValue(n, 100);
						}
					});

					if(i == 75){
						runOnUiThread(new Runnable() {
							public void run() {
								bronzeImage.setImageResource(R.drawable.bronzestar);
								playStarsAppearingSound();
							}
						});
					}

					if(i == 85){
						runOnUiThread(new Runnable() {
							public void run() {
								silverImage.setImageResource(R.drawable.silverstar);
								starsAppearingSound.start();
							}
						});
					}

					if(i == 95){
						runOnUiThread(new Runnable() {
							public void run() {
								goldImage.setImageResource(R.drawable.goldstar);
								starsAppearingSound.start();
							}
						});
					}
					playProgressBarSound();
				}

				stopProgressBarSound();

				if(stop >= 75){
					if(winSound == null){
						winSound = MediaPlayer.create(ProgressBarDisplay.this, R.raw.win_sound);
						winSound.start();
					}
				}else {
					if(loseSound == null){
						loseSound = MediaPlayer.create(ProgressBarDisplay.this, R.raw.lose_sound);
						loseSound.start();
					}
				}
			}
		});

		thread.start();
	}

	private void LoadDataRestartLevel(int dataValue, int numLvl){
		Intent intent = new Intent(ProgressBarDisplay.this, GameActivity.class);
		intent.putExtra("realrecord", dataValue);
		intent.putExtra("numLvlToGameActivity", numLvl);
		startActivity(intent);
	}
	//-----------------------WORK WITH HIGHSCORE--------------------------------------------------------
	public void SaveLevelsHighscore(){

		levelNumber = getIntent().getIntExtra("lvlNumToProgressBar", 0);

		switch (levelNumber){
			case 1: Highscore("percent1"); break;
			case 2: Highscore("percent2"); break;
			case 3: Highscore("percent3"); break;
			case 4: Highscore("percent4"); break;
			case 5: Highscore("percent5"); break;
			case 6: Highscore("percent6"); break;
			case 7: Highscore("percent7"); break;
			case 8: Highscore("percent8"); break;
			case 9: Highscore("percent9"); break;
			case 10: Highscore("percent10"); break;
			case 11: Highscore("percent11"); break;
			case 12: Highscore("percent12"); break;
			case 13: Highscore("percent13"); break;
			case 14: Highscore("percent14"); break;
			case 15: Highscore("percent15"); break;
			case 16: Highscore("percent16"); break;
			case 17: Highscore("percent17"); break;
			case 18: Highscore("percent18"); break;
			case 19: Highscore("percent19"); break;
			case 20: Highscore("percent20"); break;
			case 21: Highscore("percent21"); break;
			case 22: Highscore("percent22"); break;
			case 23: Highscore("percent23"); break;
			case 24: Highscore("percent24"); break;
			default:break;
		}
	}

	private void Highscore(String string){

		SharedPreferences sharedPreferences = getSharedPreferences("highscore", MODE_PRIVATE);
		int percent1 = sharedPreferences.getInt(string, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		if(percent1 < stopDownload){
			editor.putInt(string, stopDownload);
			editor.apply();
			String str = String.valueOf(stopDownload);
			highScore.setText(str);
		}else {
			String str = String.valueOf(percent1);
			highScore.setText(str);
		}
	}
}
