package purchases.application.purchasescollection.utilities.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import purchases.application.purchasescollection.R;

public class ThemeSupport {

    private static final String KEY = "theme_application";
    private static final String DEFAULT_VALUE = "purple_dark";

    private SharedPreferences sharedPreferences;

    public ThemeSupport(Context context) {
       this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

       context.setTheme(getThemeApplication());
    }

    public int getThemeApplication() {

        String prefTheme =  this.sharedPreferences.getString(KEY, DEFAULT_VALUE);
        return this.transformShared(prefTheme);
    }

    private int transformShared(String theme){

        return theme.equals(DEFAULT_VALUE) ? R.style.Theme_App : R.style.Theme_App_Mint;
    }
}
