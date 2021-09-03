package com.pankajranag.rana_gaming.Font;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import java.util.Locale;

public class CustomFontTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String sScheme =
            "http://schemas.android.com/apk/res-auto";
    private static final String sAttribute = "customFont";

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        } else {
            final String fontName = attrs.getAttributeValue(sScheme, sAttribute);

            if (fontName == null) {
                throw new IllegalArgumentException("You must provide \"" + sAttribute + "\" for your text view");
            } else {
                final Typeface customTypeface = CustomFont.fromString(fontName).asTypeface(context);
                setTypeface(customTypeface);
            }
        }
    }



    enum CustomFont {

        SOFIA_PRO("fonts/SofiaProRegular.otf"),
        SOFIA_PRO_REGULAR("fonts/SofiaProRegular.otf"),
        SOFIA_PRO_BOLD("fonts/SofiaProBold.otf"),
        SOFIA_PRO_BOLD_ITALIC("fonts/SofiaProBoldItalic.otf"),
        SOFIA_PRO_ULTRA_LIGHT("fonts/SofiaProUltraLight.otf"),
        SOFIA_PRO_MEDIUM("fonts/SofiaProMedium.otf"),
        SOFIA_PRO_LIGHT("fonts/SofiaProLight.otf"),
        SOFIA_PRO_EXTRA_LIGHT("fonts/SofiaProExtraLight.otf"),
        SOFIA_PRO_SEMI_BOLD("fonts/SofiaProSemiBold.otf"),

//        SEGOEUI_2("fonts/SEGOEUI_2.TTF"),

//            BILLA_BONG("fonts/BillabongBoldItalic W00 Rg.ttf"),
//            BILLA_BONG_BOLD("fonts/BillabongBoldItalic W00 Rg.ttf"),

//        TAHOMA("fonts/tahoma.ttf"),

        AVENIR_NEXT_LT_PRO_THIN("fonts/AvenirNextLTPro-Thin.otf"),
        AVENIR_NEXT_LT_PRO_REGULAR("fonts/AvenirNextLTPro-Regular.otf"),
        AVENIR_NEXT_LT_PRO_LIGHT("fonts/AvenirNextLTPro-Light.otf"),
        AVENIR_NEXT_LT_PRO_DEMI("fonts/AvenirNextLTPro-Demi.otf"),
        AVENIR_NEXT_LT_PRO_ULTRA_LIGHT("fonts/AvenirNextLTPro-Ultralight.otf"),
        AVENIR_NEXT_LT_PRO_MEDIUM("fonts/AvenirNextLTPro-Medium.otf");

        private final String fileName;

        CustomFont(String fileName) {
            this.fileName = fileName;
        }

        static CustomFont fromString(String fontName) {
            return CustomFont.valueOf(fontName.toUpperCase(Locale.US));
        }

        public Typeface asTypeface(Context context) {
            return Typeface.createFromAsset(context.getAssets(), fileName);
        }
    }
}