package purchases.application.purchasescollection.client.product.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductFormPresenter;
import purchases.application.purchasescollection.client.product.implement.presenter.ProductFormPresenter;
import purchases.application.purchasescollection.client.product.implement.view.ProductFormView;
import purchases.application.purchasescollection.common.contract.IFloatActionListener;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class ProductFormActivity extends AppCompatActivity implements IFloatActionListener {

    private IProductFormPresenter productFormPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public ProductFormActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_add_edit_product);
        ButterKnife.bind(this);

        String productId = getIntent().getStringExtra("EDIT_PRODUCT_ID");

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getTitle(productId));

        ProductFormView productFormView = (ProductFormView) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        if (productFormView == null) {
            productFormView = ProductFormView.newInstance();

            if (getIntent().hasExtra("EDIT_PRODUCT_ID")) {
                Bundle bundle = new Bundle();
                bundle.putString("EDIT_PRODUCT_ID", productId);
                productFormView.setArguments(bundle);
            }

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), productFormView, R.id.content_frame);
        }

        productFormPresenter = new ProductFormPresenter(
                productFormView,
                Injector.provideFirebaseService(),
                Injector.provideFirabseAuth(),
                productId,
                true);
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

    @OnClick(R.id.fab_product_edit_done)
    @Override
    public void runAction() {

        productFormPresenter.verifyForm();
    }

    private int getTitle(@Nullable String productId) {
        return productId == null ? R.string.product_create : R.string.product_edit;
    }
}
