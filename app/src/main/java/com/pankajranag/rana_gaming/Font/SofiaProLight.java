package com.pankajranag.rana_gaming.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SofiaProLight extends TextView {


    public SofiaProLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/SofiaProLight.otf"));
    }
}