package com.alexbros.pidlubnyalexey.guesstherecord;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Alexey on 3/1/2017.
 */

public class LevelsFunctionsClass extends AppCompatActivity {

	protected AdView mAdView;

	protected void AddingMobileAd(){
		MobileAds.initialize(getApplicationContext(), "ca-app-pub-3889824584026064~5350305632");
		mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.build();
		mAdView.loadAd(adRequest);
	}

	protected void ChangeImage(ImageButton imagebutton, int numLevel){

		SharedPreferences sharedPreferences = getSharedPreferences("level" + numLevel, MODE_PRIVATE);
		int number = sharedPreferences.getInt("number" + numLevel, 0);

		switch (numLevel){
			case 1:	if(number == 1){imagebutton.setImageResource(R.drawable.level1btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level1btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level1btnbronze);}
					break;
			case 2:	if(number == 1){imagebutton.setImageResource(R.drawable.level2btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level2btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level2btnbronze);}
					break;
			case 3:	if(number == 1){imagebutton.setImageResource(R.drawable.level3btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level3btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level3btnbronze);}
					break;
			case 4:	if(number == 1){imagebutton.setImageResource(R.drawable.level4btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level4btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level4btnbronze);}
					break;
			case 5:	if(number == 1){imagebutton.setImageResource(R.drawable.level5btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level5btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level5btnbronze);}
					break;
			case 6:	if(number == 1){imagebutton.setImageResource(R.drawable.level6btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level6btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level6btnbronze);}
					break;
			case 7:	if(number == 1){imagebutton.setImageResource(R.drawable.level7btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level7btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level7btnbronze);}
					break;
			case 8:	if(number == 1){imagebutton.setImageResource(R.drawable.level8btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level8btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level8btnbronze);}
					break;
			case 9:	if(number == 1){imagebutton.setImageResource(R.drawable.level9btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level9btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level9btnbronze);}
					break;
			case 10:if(number == 1){imagebutton.setImageResource(R.drawable.level10btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level10btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level10btnbronze);}
					break;
			case 11:if(number == 1){imagebutton.setImageResource(R.drawable.level11btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level11btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level11btnbronze);}
					break;
			case 12:if(number == 1){imagebutton.setImageResource(R.drawable.level12btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level12btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level12btnbronze);}
					break;
			case 13:if(number == 1){imagebutton.setImageResource(R.drawable.level13btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level13btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level13btnbronze);}
					break;
			case 14:if(number == 1){imagebutton.setImageResource(R.drawable.level14btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level14btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level14btnbronze);}
					break;
			case 15:if(number == 1){imagebutton.setImageResource(R.drawable.level15btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level15btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level15btnbronze);}
					break;
			case 16:if(number == 1){imagebutton.setImageResource(R.drawable.level16btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level16btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level16btnbronze);}
					break;
			case 17:if(number == 1){imagebutton.setImageResource(R.drawable.level17btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level17btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level17btnbronze);}
					break;
			case 18:if(number == 1){imagebutton.setImageResource(R.drawable.level18btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level18btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level18btnbronze);}
					break;
			case 19:if(number == 1){imagebutton.setImageResource(R.drawable.level19btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level19btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level19btnbronze);}
					break;
			case 20:if(number == 1){imagebutton.setImageResource(R.drawable.level20btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level20btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level20btnbronze);}
					break;
			case 21:if(number == 1){imagebutton.setImageResource(R.drawable.level21btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level21btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level21btnbronze);}
					break;
			case 22:if(number == 1){imagebutton.setImageResource(R.drawable.level22btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level22btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level22btnbronze);}
					break;
			case 23:if(number == 1){imagebutton.setImageResource(R.drawable.level23btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level23btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level23btnbronze);}
					break;
			case 24:if(number == 1){imagebutton.setImageResource(R.drawable.level24btngold);}
					if(number == 2){imagebutton.setImageResource(R.drawable.level24btnsilver);}
					if(number == 3){imagebutton.setImageResource(R.drawable.level24btnbronze);}
					break;
			default:break;
		}
	}
}
