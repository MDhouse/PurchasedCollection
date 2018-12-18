package purchases.application.purchasescollection.client.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.common.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.activity_setting_title);

        SettingFragment settingFragment = (SettingFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);


        if (settingFragment == null) {

            settingFragment = SettingFragment.newInstance();
            ActivityUtilities.addFragmentToActivity(getSupportFragmentManager(), settingFragment, R.id.content_frame);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
