package purchases.application.purchasescollection.client.store.activity;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.store.contract.presenter.IStoreFormPresenter;
import purchases.application.purchasescollection.client.store.implement.presenter.StoreFormPresenter;
import purchases.application.purchasescollection.client.store.implement.view.StoreFormView;
import purchases.application.purchasescollection.common.contract.IFloatActionListener;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class StoreFormActivity extends AppCompatActivity implements IFloatActionListener {

    private IStoreFormPresenter storeFormPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public StoreFormActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_store_form);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        float latitude = getIntent().getFloatExtra("MAP_LATITUDE",0);
        float longitude = getIntent().getFloatExtra("MAP_LONGITUDE",0);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Add store");

        StoreFormView storeFormView = (StoreFormView)getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(storeFormView == null) {
            storeFormView = StoreFormView.newInstance();

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), storeFormView, R.id.content_frame);
        }

        storeFormPresenter = new StoreFormPresenter(storeFormView,
                Injector.provideStore(),
                Injector.provideAuth(),
                latitude,
                longitude);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.fab_store_done)
    @Override
    public void runAction() {
        storeFormPresenter.verifyForm();
    }
}
