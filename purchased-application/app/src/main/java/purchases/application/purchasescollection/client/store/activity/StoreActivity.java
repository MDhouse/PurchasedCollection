package purchases.application.purchasescollection.client.store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.MoreObjects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.account.activity.LoginActivity;
import purchases.application.purchasescollection.client.product.activity.ProductActivity;
import purchases.application.purchasescollection.client.setting.SettingActivity;
import purchases.application.purchasescollection.client.store.contract.presenter.IStorePresenter;
import purchases.application.purchasescollection.client.store.implement.presenter.StorePresenter;
import purchases.application.purchasescollection.client.store.implement.view.StoreView;
import purchases.application.purchasescollection.common.contract.IFloatActionListener;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class StoreActivity extends AppCompatActivity  implements IFloatActionListener {

    private IStorePresenter storePresenter;
    private String userName;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.drawer_navigation)
    NavigationView navigationView;

    public StoreActivity() {
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_store);

        ButterKnife.bind(this);

        setUserNameTextView(getIntent().getStringExtra("USER_NAME"));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setDrawerContent();

        StoreView storeView = (StoreView)getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (storeView == null) {
            storeView = StoreView.newInstance();
            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), storeView, R.id.content_frame);
        }

        storePresenter = new StorePresenter(storeView, Injector.provideStore(), Injector.provideAuth());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestart() {
        recreate();
        super.onRestart();
    }

    @OnClick(R.id.fab_to_map)
    @Override
    public void runAction() {

        storePresenter.loadMaps();
    }

    private void setDrawerContent() {

        if(this.navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    menuItem -> {
                        switch (menuItem.getItemId()) {
                            case R.id.store_navigation_menu_item:
                                break;
                            case R.id.list_navigation_menu_item:
                                Intent intentProduct = new Intent(this, ProductActivity.class);
                                intentProduct.putExtra("USER_NAME", userName);
                                startActivity(intentProduct);
                                break;
                            case R.id.setting_navigation_menu_item:
                                Intent intentSetting = new Intent(this, SettingActivity.class);
                                startActivity(intentSetting);
                                break;
                            case R.id.log_out_navigation_menu_item:
                                Intent intentLogOut = new Intent(this, LoginActivity.class);
                                intentLogOut.putExtra("LOGOUT", true);
                                startActivity(intentLogOut);
                                break;
                            default:
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    });
        }
    }

    private void setUserNameTextView(String userName){

        final View header = navigationView.getHeaderView(0);

        final TextView userTextView = header.findViewById(R.id.user_name);
        userTextView.setText(MoreObjects.firstNonNull(userName, ""));
        this.userName = MoreObjects.firstNonNull(userName, "");
    }
}
