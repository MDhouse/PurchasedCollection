package purchases.application.purchasescollection.client.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

import static com.google.common.base.Preconditions.checkNotNull;


public class SettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences sharedPreferences;

    public static SettingFragment newInstance() { return new SettingFragment(); }

    public SettingFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.prefences);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        onSharedPreferenceChanged(sharedPreferences, getString(R.string.pref_theme_key));
        onSharedPreferenceChanged(sharedPreferences, getString(R.string.pref_font_key));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);

        if(key.equals("theme_application")) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }

        if(key.equals("fonts_application")) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
