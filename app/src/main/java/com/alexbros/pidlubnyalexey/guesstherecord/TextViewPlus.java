package com.alexbros.pidlubnyalexey.guesstherecord;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Alexey on 1/22/2017.
 */

public class TextViewPlus extends TextView {

	public TextViewPlus(Context context) {
		super(context);
		applyCustomFont(context);
	}

	public TextViewPlus(Context context, AttributeSet attrs) {
		super(context, attrs);
		applyCustomFont(context);
	}

	public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		applyCustomFont(context);
	}

	private void applyCustomFont(Context context) {
		this.setTypeface(Typeface.createFromAsset(context.getAssets(),
				"RockwellRegular.ttf"));
	}
}
