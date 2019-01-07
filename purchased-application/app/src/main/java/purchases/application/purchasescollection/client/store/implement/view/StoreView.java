package purchases.application.purchasescollection.client.store.implement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.store.activity.StoreMapActivity;
import purchases.application.purchasescollection.client.store.contract.presenter.IStorePresenter;
import purchases.application.purchasescollection.client.store.contract.view.IStoreView;
import purchases.application.purchasescollection.client.store.implement.adapter.StoreAdapter;
import purchases.application.purchasescollection.common.componenent.SwipeChildRefreshLayout;
import purchases.application.purchasescollection.common.contract.IStoreListener;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCounter;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;

public class StoreView extends Fragment implements IStoreView {

    @NonNull
    private IStorePresenter storePresenter;
    private IStoreListener<StoreDto> storeListener;

    private StoreAdapter storeAdapter;
    private Unbinder unbinder;


    @BindView(R.id.stores)
    LinearLayout storesPresenter;

    @BindView(R.id.no_stores)
    LinearLayout storesNotAvailable;

    @BindView(R.id.stores_expand_list)
    ExpandableListView storeList;

    @BindView(R.id.refresh_stores_layout)
    SwipeChildRefreshLayout swipeChildRefreshLayout;

    public StoreView() {

    }

    public static StoreView newInstance() {
        return new StoreView();
    }

    @Override
    public void onResume() {
        super.onResume();

        storePresenter.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);

        loadAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_store_view, container, false);

        unbinder = ButterKnife.bind(this, root);

        getStoreList();
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showStores(List<StoreDto> stores) {

        storeAdapter.replaceData(stores);

        setVisibilityStoresNotAvailable(View.GONE);
        setVisibilityStoresPresenter(View.VISIBLE);
    }

    @Override
    public void showNoStores() {

        storeAdapter.resetData();

        setVisibilityStoresPresenter(View.GONE);
        setVisibilityStoresNotAvailable(View.VISIBLE);
    }

    @Override
    public void showMaps() {
        Intent intent = new Intent(getContext(), StoreMapActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(IStorePresenter presenter) {

        storePresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @OnClick(R.id.stores_on_maps)
    public void toMap(){

        Intent intent = new Intent(getContext(), StoreMapActivity.class);
        intent.putExtra("STORE_SHOW", true);
        intent.putExtra("UUID", storePresenter.getUuid());
        startActivity(intent);
    }


    private void loadAdapter() {

        if(storeListener == null) {
            storeListener = new IStoreListener<StoreDto>() {

                @Override
                public void toRadiusEdit(StoreDto item) {
                    showDialogRadiusEdit(item);
                }

                @Override
                public void toMap(String id) {
                    Intent intent = new Intent(getContext(), StoreMapActivity.class);
                    intent.putExtra("STORE_SHOW", true);
                    intent.putExtra("STORE_ID", id);
                    startActivity(intent);
                }
            };
        }

        if(storeAdapter == null) {
            storeAdapter = new StoreAdapter(new ArrayList<>(0), getContext(), storeListener);
        }
    }

    private void getStoreList() {

        storeList.setAdapter(storeAdapter);

        swipeChildRefreshLayout.setScrollUpChild(storeList);
        swipeChildRefreshLayout.setOnRefreshListener(
                () -> storePresenter.loadStore()
        );
    }

    public void setVisibilityStoresPresenter(int visible) {

        if(storesPresenter != null)
            storesPresenter.setVisibility(visible);
    }

    public void setVisibilityStoresNotAvailable(int visible) {

        if(storesNotAvailable != null)
            storesNotAvailable.setVisibility(visible);
    }

    private void showDialogRadiusEdit(@NonNull StoreDto store){

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogNumberPicker = inflater.inflate(R.layout.radius_picker, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit radius");
        builder.setMessage("Do you changes radius for \"" + store.getName() + "\"");
        builder.setView(dialogNumberPicker);
        builder.setCancelable(false);

        final NumberPicker numberPicker = dialogNumberPicker.findViewById(R.id.dialog_radius_picker);
        numberPicker.setMaxValue(1000);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue((int)store.getRadius());

        builder.setPositiveButton("Done", (dialog, which) ->
                storePresenter.radiusCounter(new StoreCounter(store.getId(), numberPicker.getValue()))
        );

        builder.setNegativeButton("Cancel", (dialog, which) -> {});


        builder.show();
    }
}
