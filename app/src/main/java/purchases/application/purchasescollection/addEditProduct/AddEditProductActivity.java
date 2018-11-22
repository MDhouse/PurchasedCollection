package purchases.application.purchasescollection.addEditProduct;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.utilities.inject.Injector;
import purchases.application.purchasescollection.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.utilities.preferences.ThemeSupport;

public class AddEditProductActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    private AddEditProductPresenter addEditProductPresenter;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_add_edit_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AddEditProductFragment addEditTaskFragment = (AddEditProductFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_frame);

        String productId = getIntent().getStringExtra(AddEditProductFragment.ARGUMENT_EDIT_PRODUCT_ID);

        setToolbarTitle(productId);

        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddEditProductFragment.newInstance();

            if (getIntent().hasExtra(AddEditProductFragment.ARGUMENT_EDIT_PRODUCT_ID)) {
                Bundle bundle = new Bundle();
                bundle.putString(AddEditProductFragment.ARGUMENT_EDIT_PRODUCT_ID, productId);
                addEditTaskFragment.setArguments(bundle);
            }

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), addEditTaskFragment, R.id.content_frame);
        }

        boolean shouldLoadDataFromRepo = true;


        addEditProductPresenter = new AddEditProductPresenter(
                Injector.provideTasksRepository(getApplicationContext()),
                addEditTaskFragment,
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

    private void setToolbarTitle(@Nullable String productId) {
        if(productId == null) {
            actionBar.setTitle(R.string.product_create);
        } else {
            actionBar.setTitle(R.string.product_edit);
        }
    }
}
