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
    public final static int THEME_WHITE = 1;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {

        FrameLayout frameLayout = activity.findViewById(R.id.content_frame);

        switch (sTheme) {
            default:
            case THEME_DEFAULT:
                frameLayout.setBackgroundColor(Color.BLACK);
                activity.setTheme(R.style.SecondTheme);
                break;
            case THEME_WHITE:
                frameLayout.setBackgroundColor(Color.WHITE);
                activity.setTheme(R.style.AppTheme);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void onToolbar(Activity activity){

        Toolbar toolbar = activity.findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setTitleTextColor(Color.WHITE);
    }

}
