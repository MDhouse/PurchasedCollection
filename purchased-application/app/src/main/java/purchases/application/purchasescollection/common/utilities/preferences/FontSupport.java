package purchases.application.purchasescollection.common.utilities.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

public class FontSupport {

    private static final String KEY = "fonts_application";

    private SharedPreferences sharedPreferences;

    public FontSupport(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public FontStyle getFontStyle() {

        String prefFonts =  this.sharedPreferences.getString(KEY,  "Medium");
        return FontStyle.valueOf(prefFonts);
    }
}
