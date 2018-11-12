package purchases.application.purchasescollection.addEditProduct;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.utilities.ActivityUtilities;
import purchases.application.purchasescollection.utilities.ChangeTemplate;
import purchases.application.purchasescollection.utilities.Injector;

public class AddEditProductActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    private AddEditProductPresenter addEditProductPresenter;

    private ActionBar actionBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        ChangeTemplate.onActivityCreateSetTheme(this);

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

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(),
                    addEditTaskFragment, R.id.content_frame);
        }

        boolean shouldLoadDataFromRepo = true;

        if (savedInstanceState != null) {
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }

        addEditProductPresenter = new AddEditProductPresenter(
                Injector.provideTasksRepository(getApplicationContext()),
                addEditTaskFragment,
                productId,
                shouldLoadDataFromRepo);
    }

    private void setToolbarTitle(@Nullable String taskId) {
        if(taskId == null) {
            actionBar.setTitle(R.string.product_create);
        } else {
            actionBar.setTitle(R.string.product_edit);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, addEditProductPresenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
