package purchases.application.purchasescollection.client.product.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.implement.presenter.ProductDetailPresenter;
import purchases.application.purchasescollection.client.product.implement.view.ProductDetailView;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "PRODUCT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        String productId =  getIntent().getStringExtra(PRODUCT_ID);

        ProductDetailView productDetailView = (ProductDetailView) getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(productDetailView == null) {
            productDetailView = ProductDetailView.newInstance(productId);

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), productDetailView, R.id.content_frame);
        }

         new ProductDetailPresenter(Injector.provideRoomServices(getApplicationContext()), productDetailView, productId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
