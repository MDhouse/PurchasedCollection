package purchases.application.purchasescollection.client.account.activity;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.account.implement.LoginPresenter;
import purchases.application.purchasescollection.client.account.implement.LoginView;
import purchases.application.purchasescollection.common.notification.NotificationConst;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;
import purchases.application.purchasescollection.infrastructure.implement.receive.PurchasedReceiver;

public class LoginActivity extends AppCompatActivity {

    private PurchasedReceiver purchasedReceiver;
    private IntentFilter intentFilter;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        registerReceiver(getPurchasedReceiver(), getIntentFilter(), getPermission(), null);

        setContentView(R.layout.activity_login);
        boolean isLogout =  getIntent().getBooleanExtra("LOGOUT", false);

        LoginView loginView =(LoginView)getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(loginView == null){
            loginView = LoginView.newInstance();
            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), loginView, R.id.content_frame);
        }

        new LoginPresenter(loginView, Injector.provideAuth(), isLogout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(getPurchasedReceiver());
    }

    private PurchasedReceiver getPurchasedReceiver() {

        if(purchasedReceiver == null)
            purchasedReceiver = new PurchasedReceiver();

        return purchasedReceiver;
    }

    private IntentFilter getIntentFilter() {

        if(intentFilter == null) {
            intentFilter = new IntentFilter();
            intentFilter.addAction("purchases.application.purchasescollection.CREATED_PRODUCT");
            intentFilter.addAction(NotificationConst.getPurchasedToast());
        }

        return intentFilter;
    }

    private String getPermission() {

        return "application.purchases.purchasescollection.HANDLE_INTENT";
    }
}
