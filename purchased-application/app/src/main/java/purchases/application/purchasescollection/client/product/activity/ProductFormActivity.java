package purchases.application.purchasescollection.client.product.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.implement.presenter.ProductFormPresenter;
import purchases.application.purchasescollection.client.product.implement.view.ProductFormView;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class ProductFormActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    public ProductFormActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_add_edit_product);

        String productId = getIntent().getStringExtra(ProductFormView.ARGUMENT_EDIT_PRODUCT_ID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getTitle(productId));

        ProductFormView productFormView = (ProductFormView) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (productFormView == null) {
            productFormView = ProductFormView.newInstance();

            if (getIntent().hasExtra(productFormView.ARGUMENT_EDIT_PRODUCT_ID)) {
                Bundle bundle = new Bundle();
                bundle.putString(productFormView.ARGUMENT_EDIT_PRODUCT_ID, productId);
                productFormView.setArguments(bundle);
            }

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), productFormView, R.id.content_frame);
        }

        boolean shouldLoadDataFromRepo = true;

        new ProductFormPresenter(
                productFormView,
                Injector.provideRoomServices(getApplicationContext()),
                productId,
                shouldLoadDataFromRepo);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private int getTitle(@Nullable String productId) {
        return productId == null ? R.string.product_create : R.string.product_edit;
    }
}
