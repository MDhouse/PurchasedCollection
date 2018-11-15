package purchases.application.purchasescollection.utilities.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import purchases.application.purchasescollection.R;

public class ThemeInfo extends Application {

    public static final int DEFAULT_THEME = R.style.Theme_App;
    public static final int MINT_THEME = R.style.Theme_App_Mint;

    private static final String KEY = "theme";

    private SharedPreferences sharedPreferences;

    public ThemeInfo(Context context) {
       this.sharedPreferences = context.getSharedPreferences("THEME", MODE_PRIVATE);
    }

    public int getThemeApplication() { return this.sharedPreferences.getInt(KEY, this.DEFAULT_THEME);}

    public void setThemeApplication(int theme) {
        SharedPreferences.Editor editorPreferences = this.sharedPreferences.edit();

        editorPreferences.putInt(this.KEY, theme);
        editorPreferences.commit();
    }
}
