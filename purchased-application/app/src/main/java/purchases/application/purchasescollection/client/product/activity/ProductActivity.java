package purchases.application.purchasescollection.client.product.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.implement.presenter.ProductPresenter;
import purchases.application.purchasescollection.client.product.implement.view.ProductView;
import purchases.application.purchasescollection.setting.SettingActivity;
import purchases.application.purchasescollection.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.utilities.inject.Injector;
import purchases.application.purchasescollection.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.utilities.preferences.ThemeSupport;

public class ProductActivity extends AppCompatActivity  {

    private ProductPresenter productPresenter;


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public ProductActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_products);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);

        this.navigationView = findViewById(R.id.drawer_navigation);

        setDrawerContent();

        ProductView productView = (ProductView)getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (productView == null) {
            productView = productView.newInstance();
            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), productView, R.id.content_frame);
        }

        this.productPresenter = new ProductPresenter(productView, Injector.provideRoomServices(getApplicationContext()));
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

    private void setDrawerContent() {

        if(this.navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    menuItem -> {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                break;
                            case R.id.setting_navigation_menu_item:
                                Intent intent = new Intent(this, SettingActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    });
        }
    }
}
