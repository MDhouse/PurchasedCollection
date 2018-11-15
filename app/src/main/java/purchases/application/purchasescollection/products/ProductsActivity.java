package purchases.application.purchasescollection.products;


import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.utilities.ActivityUtilities;
import purchases.application.purchasescollection.utilities.Injector;
import purchases.application.purchasescollection.utilities.preferences.ThemeInfo;

public class ProductsActivity extends AppCompatActivity  {

    private ThemeInfo themeInfo;

    private ProductsPresenter productsPresenter;

    private DrawerLayout drawerLayout;

    private FrameLayout frameLayout;

    public ProductsActivity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.themeInfo = new ThemeInfo(this.getApplicationContext());

        setTheme(this.themeInfo.getThemeApplication());

        setContentView(R.layout.activity_products);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


        frameLayout = findViewById(R.id.content_frame);

        // Set up the navigation drawer.
        drawerLayout = findViewById(R.id.drawer_layout);
        // drawerLayout.setStatusBarBackground(R.attr.colorPrimary);

        NavigationView navigationView = findViewById(R.id.drawer_navigation);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ProductsFragment productsFragment =
                (ProductsFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (productsFragment == null) {
            productsFragment = ProductsFragment.newInstance();
            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), productsFragment, R.id.content_frame);
        }

        this.productsPresenter = new ProductsPresenter(Injector.provideTasksRepository(getApplicationContext()), productsFragment);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.list_navigation_menu_item:
                            break;
                        case R.id.change_template:
                            changeTemplate();
                            break;
                        default:
                            break;
                    }
                    // Close the navigation drawer when an item is selected.
                    menuItem.setChecked(true);
                    drawerLayout.closeDrawers();
                    return true;
                });
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


    private void changeTemplate() {

        int theme = this.themeInfo.getThemeApplication() == this.themeInfo.DEFAULT_THEME ? this.themeInfo.MINT_THEME :  this.themeInfo.DEFAULT_THEME;

        this.themeInfo.setThemeApplication(theme);
        recreate();
    }
}
