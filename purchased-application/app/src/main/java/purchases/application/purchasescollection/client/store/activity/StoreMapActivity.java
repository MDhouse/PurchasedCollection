package purchases.application.purchasescollection.client.store.activity;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.store.contract.presenter.IStoreMapPresenter;
import purchases.application.purchasescollection.client.store.implement.presenter.StoreMapPresenter;
import purchases.application.purchasescollection.client.store.implement.view.StoreMapView;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.inject.Injector;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreSearch;

public class StoreMapActivity extends AppCompatActivity {

    private IStoreMapPresenter storeMapPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public StoreMapActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_store_on_map);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Maps");

        boolean showStore = getIntent().getBooleanExtra("STORE_SHOW", false);
        String storeId =  getIntent().getStringExtra("STORE_ID");
        String uuid =  getIntent().getStringExtra("UUID");

        StoreMapView storeMapView = (StoreMapView)getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(storeMapView == null){
            storeMapView = StoreMapView.newInstance();

            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), storeMapView, R.id.content_frame);
        }

        storeMapPresenter = new StoreMapPresenter(storeMapView, Injector.provideStore(), getApplicationContext(), showStore, new StoreSearch(storeId, uuid));
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
}
