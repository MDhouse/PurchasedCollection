package purchases.application.purchasescollection.client.store.implement.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.store.contract.presenter.IStoreFormPresenter;
import purchases.application.purchasescollection.client.store.contract.view.IStoreFormView;
import purchases.application.purchasescollection.common.notification.NotificationConst;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class StoreFormView extends Fragment implements IStoreFormView {

    private IStoreFormPresenter storeFormPresenter;
    private Unbinder unbinder;

    @BindView(R.id.store_location)
    TextView locationInfo;

    @BindView(R.id.store_name)
    EditText storeName;

    @BindView(R.id.store_desc)
    EditText storeDescription;

    @BindView(R.id.store_radius)
    EditText storeRadius;

    public StoreFormView() {
    }

    public static StoreFormView newInstance() {

        return new StoreFormView();
    }

    @Override
    public void onResume() {
        super.onResume();
        storeFormPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root =  inflater.inflate(R.layout.fragment_store_form_view, container, false);

        unbinder = ButterKnife.bind(this, root);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showStoreList() {

        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void formValidate() {

        if(checkFields()) {
            storeFormPresenter.saveStore(
                    getStoreName(),
                    getStoreDescription(),
                    getStoreRadius());
            return;
        }

        showToast("A fill the all fields of forms.");
    }

    @Override
    public void showToast(String message) {

        Intent toSend = new Intent();

        toSend.setAction(NotificationConst.getPurchasedToast());
        toSend.putExtra(NotificationConst.getPurchasedToastMessage(), message);

        getActivity().sendBroadcast(toSend, "application.purchases.purchasescollection.HANDLE_INTENT");
    }

    @Override
    public void showLocationInfo(String location) {

        if(locationInfo != null)
            locationInfo.setText(location);
    }

    @Override
    public void setPresenter(IStoreFormPresenter presenter) {

        storeFormPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private boolean checkFields() {

        return (this.valid(storeName) && this.valid(storeDescription) && this.valid(storeRadius));
    }

    private boolean valid(@NonNull EditText editText) {

        return editText.getText().toString().trim().length() > 0;
    }

    private String getStoreName() {
        return storeName.getText().toString();
    }

    private String getStoreDescription() {
        return storeDescription.getText().toString();
    }

    private double getStoreRadius() {
        return Double.valueOf(storeRadius.getText().toString());
    }
}
