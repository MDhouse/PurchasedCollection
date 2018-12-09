package purchases.application.purchasescollection.client.account.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.account.implement.LoginPresenter;
import purchases.application.purchasescollection.client.account.implement.LoginView;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class LoginActivity extends AppCompatActivity {

    public static final String LOGOUT = "LOGOUT";


    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_login);
        boolean isLogout =  getIntent().getBooleanExtra(LOGOUT, false);

        LoginView loginView =(LoginView)getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(loginView == null){
            loginView = LoginView.newInstance();
            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), loginView, R.id.content_frame);
        }

        new LoginPresenter(loginView, Injector.provideFirabseAuth(), isLogout);
    }
}
