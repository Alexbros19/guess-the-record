package com.alexbros.pidlubnyalexey.guesstherecord;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;
import android.graphics.Typeface;

public class ButtonPlus extends AppCompatButton {

	public ButtonPlus(Context context) {
		super(context);
		applyCustomFont(context);
	}

	public ButtonPlus(Context context, AttributeSet attrs) {
		super(context, attrs);
		applyCustomFont(context);
	}

	public ButtonPlus(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		applyCustomFont(context);
	}

	private void applyCustomFont(Context context) {
		this.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"RockwellRegular.ttf"));
	}
}
