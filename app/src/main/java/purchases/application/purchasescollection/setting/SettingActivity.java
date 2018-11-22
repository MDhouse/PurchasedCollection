package purchases.application.purchasescollection.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.utilities.activity.ActivityUtilities;
import purchases.application.purchasescollection.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.utilities.preferences.ThemeSupport;

public class SettingActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme( new ThemeSupport(this).getThemeApplication());
        getTheme().applyStyle(new FontSupport(this).getFontStyle().getResId(), true);

        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.actionBar = getSupportActionBar();

        this.actionBar.setDisplayHomeAsUpEnabled(true);

        this.setTitle();

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

    private void setTitle() {

        actionBar.setTitle(R.string.activity_setting_title);
    }
}
