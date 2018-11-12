package purchases.application.purchasescollection.productDetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.utilities.ActivityUtilities;
import purchases.application.purchasescollection.utilities.ChangeTemplate;
import purchases.application.purchasescollection.utilities.Injector;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "PRODUCT_ID";

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ChangeTemplate.onActivityCreateSetTheme(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        String productId =  getIntent().getStringExtra(PRODUCT_ID);

        ProductDetailFragment productDetailFragment = (ProductDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(productDetailFragment == null) {
            productDetailFragment = ProductDetailFragment.newInstance(productId);

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), productDetailFragment, R.id.content_frame);
        }

        new ProductDetailPresenter(
                Injector.provideTasksRepository(getApplicationContext()),
                productDetailFragment,
                productId
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
