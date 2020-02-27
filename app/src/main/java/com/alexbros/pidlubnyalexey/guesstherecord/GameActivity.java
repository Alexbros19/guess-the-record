package com.alexbros.pidlubnyalexey.guesstherecord;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

	private TextView textEnterNumbers;
	private ImageButton firstRecordImage;
	private ImageButton secondRecordImage;
	private TextView recordText;
	private TextView recordTitle;
	private LinearLayout linearLayout;
	private String displayText = "";

	private boolean imageFlag = true;
	private boolean toastFlag = false;
	private boolean continueMusic;

	private int realRecordValue;
	private int levelNumber;

	private final int LENGTH_INPUT_NUMBER = 6;
	private final int PERCENT_FOR_BRONZE = 75;
	private final int PERCENT_FOR_SILVER = 85;
	private final int PERCENT_FOR_GOLD = 95;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		textEnterNumbers = (TextView) findViewById(R.id.txtViewEnterNumbers);
		textEnterNumbers.setText(displayText);
		firstRecordImage = (ImageButton) findViewById(R.id.recordImage1);
		secondRecordImage = (ImageButton) findViewById(R.id.recordImage2);
		recordText = (TextView) findViewById(R.id.recordText);
		recordTitle = (TextView) findViewById(R.id.recordTitle);
		linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
		//-----------------------RECEIVED REAL RECORD AND NUMBER LEVEL VALUES-----------------------
		realRecordValue = getIntent().getIntExtra("realrecord", 0);
		levelNumber = getIntent().getIntExtra("numLvlToGameActivity", 0);

		SetResource(levelNumber);
	}
	//-------------------PAUSE AND RESUME MUSIC WHEN ACTIVITY LIFECYCLE IS CHANGED------------------
	@Override
	protected void onPause() {
		super.onPause();
		if (!continueMusic) {
			MusicManager.pause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences sharedPreferences = getSharedPreferences("musicflag", MODE_PRIVATE);

		if(!sharedPreferences.getBoolean("flag", Boolean.parseBoolean(null))){
			MusicManager.release();
		}else{
			continueMusic = false;
			MusicManager.start(this, MusicManager.MUSIC_MENU);
		}
	}

	protected void attachBaseContext(Context base) {
		super.attachBaseContext(LocaleHelper.onAttach(base));
	}
	//----------------------------------BACK BUTTON FUNCTIONALITY-----------------------------------
	@Override
	public void onBackPressed() {
		continueMusic = true;
		Intent i = new Intent(GameActivity.this, LevelsLoading.class);
		startActivity(i);
	}

	private void UpdateDisplay() {
		textEnterNumbers.setText(displayText);
	}
	//-----------------ON CLICK NUMBER , RESET , OK , CLEAR BUTTONS FUNCTIONALITY-------------------
	public void OnClickNumber(View view) {

		if(displayText.length() <= LENGTH_INPUT_NUMBER){
			Button button = (Button) view;
			displayText += button.getText().toString();
			UpdateDisplay();
			toastFlag = false;
		} else {
			if(!toastFlag){
				Toast toast = Toast.makeText(getApplicationContext(),
						"Limit of numbers is over!",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.TOP, 0, 0);
				toast.show();
				toastFlag = true;
			}
		}
	}

	public void OnClickReset(View view) {
		displayText = "";
		UpdateDisplay();
	}

	public void OnClickButtonOk(View view){

		SetNumberForLevel("level" + levelNumber, "number" + levelNumber);
	}

	public void OnClickClear(View view)
	{
		if(displayText.length() > 0) {
			displayText = displayText.substring(0, displayText.length() - 1);
			UpdateDisplay();
		}
	}
	//------------------------------SET NUMBER FOR LEVEL FUNCTIONALITY------------------------------
	private void SetNumberForLevel(String lvl, String num){

		if(!displayText.isEmpty()) {

			int enterNumber = Integer.parseInt(displayText);
			final MeterView meter = (MeterView) this.findViewById(R.id.meter);
			int calcPercent = CalculationPercent(realRecordValue, enterNumber);

			// translate input number to level loading menu
			SharedPreferences sharedPreferences = getSharedPreferences(lvl, MODE_PRIVATE);
			int number = sharedPreferences.getInt(num, 0);
			SharedPreferences.Editor editor = sharedPreferences.edit();

			if(number != 0){
				if(calcPercent >= PERCENT_FOR_BRONZE && calcPercent < PERCENT_FOR_SILVER && number == 3){

					editor.putInt(num, 3);
					editor.apply();
				}
				if(calcPercent >= PERCENT_FOR_SILVER && calcPercent < PERCENT_FOR_GOLD && (number > 2 || number == 2)){

					editor.putInt(num, 2);
					editor.apply();
				}
				if(calcPercent >= PERCENT_FOR_GOLD && (number > 1 || number == 1)){

					editor.putInt(num, 1);
					editor.apply();
				}
			} else {
				if(calcPercent >= PERCENT_FOR_BRONZE && calcPercent < PERCENT_FOR_SILVER){

					editor.putInt(num, 3);
					editor.apply();
				}
				if(calcPercent >= PERCENT_FOR_SILVER && calcPercent < PERCENT_FOR_GOLD){

					editor.putInt(num, 2);
					editor.apply();
				}
				if(calcPercent >= PERCENT_FOR_GOLD){

					editor.putInt(num, 1);
					editor.apply();
				}
			}

			continueMusic = true;
			// load new activity
			Intent i = new Intent(GameActivity.this, ProgressBarDisplay.class);
			i.putExtra("percent", calcPercent);
			i.putExtra("lvlNumToProgressBar", levelNumber);
			startActivity(i);
		}
	}
	//----------------------INCREASE AND DECREASE RECORD IMAGES ON CLICK----------------------------
	public void OnClickImage1(View view){
		if(imageFlag){
			firstRecordImage.setVisibility(View.INVISIBLE);
			secondRecordImage.setVisibility(View.INVISIBLE);
			recordText.setVisibility(View.INVISIBLE);
			recordTitle.setVisibility(View.INVISIBLE);
			imageFlag = false;

			switch (levelNumber){
				case 1: linearLayout.setBackgroundResource(R.drawable.pizza_img1); break;
				case 2: linearLayout.setBackgroundResource(R.drawable.mattress_img1); break;
				case 3: linearLayout.setBackgroundResource(R.drawable.cards_img1); break;
				case 4: linearLayout.setBackgroundResource(R.drawable.cyclist_img1); break;
				case 5: linearLayout.setBackgroundResource(R.drawable.coconuts_img1); break;
				case 6: linearLayout.setBackgroundResource(R.drawable.pull_ups_img1); break;
				case 7: linearLayout.setBackgroundResource(R.drawable.burnman_img1); break;
				case 8: linearLayout.setBackgroundResource(R.drawable.dominowall_img1); break;
				case 9: linearLayout.setBackgroundResource(R.drawable.poolcue_img1); break;
				case 10: linearLayout.setBackgroundResource(R.drawable.rooftiles_img1); break;
				case 11: linearLayout.setBackgroundResource(R.drawable.handswalk_img1); break;
				case 12: linearLayout.setBackgroundResource(R.drawable.pullups_woman_img1); break;
				case 13: linearLayout.setBackgroundResource(R.drawable.starwars_img1); break;
				case 14: linearLayout.setBackgroundResource(R.drawable.drill_img1); break;
				case 15: linearLayout.setBackgroundResource(R.drawable.crushedrinks_img1); break;
				case 16: linearLayout.setBackgroundResource(R.drawable.golf_img1); break;
				case 17: linearLayout.setBackgroundResource(R.drawable.hulahoops_img1); break;
				case 18: linearLayout.setBackgroundResource(R.drawable.guinnessrecord_img1); break;
				case 19: linearLayout.setBackgroundResource(R.drawable.fourfingers_img1); break;
				case 20: linearLayout.setBackgroundResource(R.drawable.icecream_img1); break;
				case 21: linearLayout.setBackgroundResource(R.drawable.chocolate_img1); break;
				case 22: linearLayout.setBackgroundResource(R.drawable.candles_img1); break;
				case 23: linearLayout.setBackgroundResource(R.drawable.freediver_img1); break;
				case 24: linearLayout.setBackgroundResource(R.drawable.plasticcup_img1); break;
				default:break;
			}
		}
	}

	public void OnClickImage2(View view){
		if(imageFlag){

			firstRecordImage.setVisibility(View.INVISIBLE);
			secondRecordImage.setVisibility(View.INVISIBLE);
			recordText.setVisibility(View.INVISIBLE);
			recordTitle.setVisibility(View.INVISIBLE);
			imageFlag = false;

			switch (levelNumber){
				case 1: linearLayout.setBackgroundResource(R.drawable.pizza_img2); break;
				case 2: linearLayout.setBackgroundResource(R.drawable.mattress_img2); break;
				case 3: linearLayout.setBackgroundResource(R.drawable.cards_img2); break;
				case 4: linearLayout.setBackgroundResource(R.drawable.cyclist_img2); break;
				case 5: linearLayout.setBackgroundResource(R.drawable.coconuts_img2); break;
				case 6: linearLayout.setBackgroundResource(R.drawable.pull_ups_img2); break;
				case 7: linearLayout.setBackgroundResource(R.drawable.burnman_img2); break;
				case 8: linearLayout.setBackgroundResource(R.drawable.dominowall_img2); break;
				case 9: linearLayout.setBackgroundResource(R.drawable.poolcue_img2); break;
				case 10: linearLayout.setBackgroundResource(R.drawable.rooftiles_img2); break;
				case 11: linearLayout.setBackgroundResource(R.drawable.handswalk_img2); break;
				case 12: linearLayout.setBackgroundResource(R.drawable.pullups_woman_img2); break;
				case 13: linearLayout.setBackgroundResource(R.drawable.starwars_img2); break;
				case 14: linearLayout.setBackgroundResource(R.drawable.drill_img2); break;
				case 15: linearLayout.setBackgroundResource(R.drawable.crushedrinks_img2); break;
				case 16: linearLayout.setBackgroundResource(R.drawable.golf_img2); break;
				case 17: linearLayout.setBackgroundResource(R.drawable.hulahoops_img2); break;
				case 18: linearLayout.setBackgroundResource(R.drawable.guinnessrecord_img2); break;
				case 19: linearLayout.setBackgroundResource(R.drawable.fourfingers_img2); break;
				case 20: linearLayout.setBackgroundResource(R.drawable.icecream_img2); break;
				case 21: linearLayout.setBackgroundResource(R.drawable.chocolate_img2); break;
				case 22: linearLayout.setBackgroundResource(R.drawable.candles_img2); break;
				case 23: linearLayout.setBackgroundResource(R.drawable.freediver_img2); break;
				case 24: linearLayout.setBackgroundResource(R.drawable.plasticcup_img2); break;
				default:break;
			}
		}
	}

	public void OnClickLayout(View view){
		if(!imageFlag){
			firstRecordImage.setVisibility(View.VISIBLE);
			secondRecordImage.setVisibility(View.VISIBLE);
			recordText.setVisibility(View.VISIBLE);
			recordTitle.setVisibility(View.VISIBLE);
			imageFlag = true;
			linearLayout.setBackgroundResource(R.color.colorProgressBackround);
		}
	}

	public void BackToLevelList(View view){
		continueMusic = true;
		if(levelNumber >= 0 && levelNumber <= 12){
			Intent i = new Intent(GameActivity.this, LevelsLoading.class);
			startActivity(i);
		}else if(levelNumber >= 13 && levelNumber <= 24){
			Intent i = new Intent(GameActivity.this, LevelLoadingWorld2.class);
			startActivity(i);
		}
	}
	//----------------------------CALCULATION PERCENT VALUE LOGIC-----------------------------------
	private int CalculationPercent(int recordValue, int number){

		int numbersPercent = 0;

		if(number <= recordValue){
			numbersPercent = number * 100 / recordValue;
		}else {
			int percent = number * 100 / recordValue;
			if(percent > 200){percent = 200;}
			int subtractNumber = Math.abs(100 - percent);
			numbersPercent = 100 - subtractNumber;
		}

		return numbersPercent;
	}
	//------------------SET RECORDS RESOURCES ACCORDINGLY CHOOSED GAME LEVEL------------------------
	public void SetResource(int number){

		switch (number){
			case 1:	firstRecordImage.setImageResource(R.drawable.pizza_img1);
					secondRecordImage.setImageResource(R.drawable.pizza_img2);
					recordTitle.setText(R.string.title_level1);
					recordText.setText(R.string.text_level1);

					Toast toast = Toast.makeText(getApplicationContext(),
							R.string.increase_image,
						Toast.LENGTH_LONG);
					toast.setGravity(Gravity.TOP, 0, 0);
					toast.show();

					break;
			case 2:	firstRecordImage.setImageResource(R.drawable.mattress_img1);
					secondRecordImage.setImageResource(R.drawable.mattress_img2);
					recordTitle.setText(R.string.title_level2);
					recordText.setText(R.string.text_level2);
					break;
			case 3:	firstRecordImage.setImageResource(R.drawable.cards_img1);
					secondRecordImage.setImageResource(R.drawable.cards_img2);
					recordTitle.setText(R.string.title_level3);
					recordText.setText(R.string.text_level3);
					break;
			case 4:	firstRecordImage.setImageResource(R.drawable.cyclist_img1);
					secondRecordImage.setImageResource(R.drawable.cyclist_img2);
					recordTitle.setText(R.string.title_level4);
					recordText.setText(R.string.text_level4);
					break;
			case 5:	firstRecordImage.setImageResource(R.drawable.coconuts_img1);
					secondRecordImage.setImageResource(R.drawable.coconuts_img2);
					recordTitle.setText(R.string.title_level5);
					recordText.setText(R.string.text_level5);
					break;
			case 6:	firstRecordImage.setImageResource(R.drawable.pull_ups_img1);
					secondRecordImage.setImageResource(R.drawable.pull_ups_img2);
					recordTitle.setText(R.string.title_level6);
					recordText.setText(R.string.text_level6);
					break;
			case 7:	firstRecordImage.setImageResource(R.drawable.burnman_img1);
					secondRecordImage.setImageResource(R.drawable.burnman_img2);
					recordTitle.setText(R.string.title_level7);
					recordText.setText(R.string.text_level7);
					break;
			case 8:	firstRecordImage.setImageResource(R.drawable.dominowall_img1);
					secondRecordImage.setImageResource(R.drawable.dominowall_img2);
					recordTitle.setText(R.string.title_level8);
					recordText.setText(R.string.text_level8);
					break;
			case 9:	firstRecordImage.setImageResource(R.drawable.poolcue_img1);
					secondRecordImage.setImageResource(R.drawable.poolcue_img2);
					recordTitle.setText(R.string.title_level9);
					recordText.setText(R.string.text_level9);
					break;
			case 10:
				firstRecordImage.setImageResource(R.drawable.rooftiles_img1);
					secondRecordImage.setImageResource(R.drawable.rooftiles_img2);
					recordTitle.setText(R.string.title_level10);
					recordText.setText(R.string.text_level10);
					break;
			case 11:
				firstRecordImage.setImageResource(R.drawable.handswalk_img1);
					secondRecordImage.setImageResource(R.drawable.handswalk_img2);
					recordTitle.setText(R.string.title_level11);
					recordText.setText(R.string.text_level11);
					break;
			case 12:
				firstRecordImage.setImageResource(R.drawable.pullups_woman_img1);
					secondRecordImage.setImageResource(R.drawable.pullups_woman_img2);
					recordTitle.setText(R.string.title_level12);
					recordText.setText(R.string.text_level12);
					break;
			case 13:
				firstRecordImage.setImageResource(R.drawable.starwars_img1);
					secondRecordImage.setImageResource(R.drawable.starwars_img2);
					recordTitle.setText(R.string.title_level13);
					recordText.setText(R.string.text_level13);
					break;
			case 14:
				firstRecordImage.setImageResource(R.drawable.drill_img1);
					secondRecordImage.setImageResource(R.drawable.drill_img2);
					recordTitle.setText(R.string.title_level14);
					recordText.setText(R.string.text_level14);
					break;
			case 15:
				firstRecordImage.setImageResource(R.drawable.crushedrinks_img1);
					secondRecordImage.setImageResource(R.drawable.crushedrinks_img2);
					recordTitle.setText(R.string.title_level15);
					recordText.setText(R.string.text_level15);
					break;
			case 16:
				firstRecordImage.setImageResource(R.drawable.golf_img1);
					secondRecordImage.setImageResource(R.drawable.golf_img2);
					recordTitle.setText(R.string.title_level16);
					recordText.setText(R.string.text_level16);
					break;
			case 17:
				firstRecordImage.setImageResource(R.drawable.hulahoops_img1);
					secondRecordImage.setImageResource(R.drawable.hulahoops_img2);
					recordTitle.setText(R.string.title_level17);
					recordText.setText(R.string.text_level17);
					break;
			case 18:
				firstRecordImage.setImageResource(R.drawable.guinnessrecord_img1);
					secondRecordImage.setImageResource(R.drawable.guinnessrecord_img2);
					recordTitle.setText(R.string.title_level18);
					recordText.setText(R.string.text_level18);
					break;
			case 19:
				firstRecordImage.setImageResource(R.drawable.fourfingers_img1);
					secondRecordImage.setImageResource(R.drawable.fourfingers_img2);
					recordTitle.setText(R.string.title_level19);
					recordText.setText(R.string.text_level19);
					break;
			case 20:
				firstRecordImage.setImageResource(R.drawable.icecream_img1);
					secondRecordImage.setImageResource(R.drawable.icecream_img2);
					recordTitle.setText(R.string.title_level20);
					recordText.setText(R.string.text_level20);
					break;
			case 21:
				firstRecordImage.setImageResource(R.drawable.chocolate_img1);
					secondRecordImage.setImageResource(R.drawable.chocolate_img2);
					recordTitle.setText(R.string.title_level21);
					recordText.setText(R.string.text_level21);
					break;
			case 22:
				firstRecordImage.setImageResource(R.drawable.candles_img1);
					secondRecordImage.setImageResource(R.drawable.candles_img2);
					recordTitle.setText(R.string.title_level22);
					recordText.setText(R.string.text_level22);
					break;
			case 23:
				firstRecordImage.setImageResource(R.drawable.freediver_img1);
					secondRecordImage.setImageResource(R.drawable.freediver_img2);
					recordTitle.setText(R.string.title_level23);
					recordText.setText(R.string.text_level23);
					break;
			case 24:
				firstRecordImage.setImageResource(R.drawable.plasticcup_img1);
					secondRecordImage.setImageResource(R.drawable.plasticcup_img2);
					recordTitle.setText(R.string.title_level24);
					recordText.setText(R.string.text_level24);
					break;
			default:break;
		}
	}
}
