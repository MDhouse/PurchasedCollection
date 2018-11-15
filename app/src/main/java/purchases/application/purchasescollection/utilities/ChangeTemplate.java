package purchases.application.purchasescollection.utilities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import purchases.application.purchasescollection.R;

public class ChangeTemplate {

    private static int sTheme;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_SECOND = 1;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {

        switch(sTheme){
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Theme_App);
                break;
            case THEME_SECOND:
                activity.setTheme(R.style.Theme_App_Mint);
                break;
        }
    }
}
