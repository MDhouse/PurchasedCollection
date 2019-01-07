package purchases.application.purchasescollection.client.store.implement.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.store.activity.StoreFormActivity;
import purchases.application.purchasescollection.client.store.contract.presenter.IStoreMapPresenter;
import purchases.application.purchasescollection.client.store.contract.view.IStoreMapView;
import purchases.application.purchasescollection.common.notification.NotificationConst;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;

public class StoreMapView
        extends Fragment
        implements
        IStoreMapView,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnCameraMoveListener,
        OnMapReadyCallback{

    private IStoreMapPresenter storeMapPresenter;
    private Unbinder unbinder;
    private GoogleMap googleMap;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    @BindView(R.id.map_view)
    MapView mapView;

    @BindView(R.id.current_location_lat)
    TextView locationLatitude;

    @BindView(R.id.current_location_long)
    TextView locationLongtitude;

    public StoreMapView() {
    }

    public static StoreMapView newInstance() {
        return new StoreMapView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        storeMapPresenter.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme(new ThemeSupport(getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport(getActivity()).getFontStyle().getResId(), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_store_map_view, container, false);

        unbinder = ButterKnife.bind(this, view);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onMyLocationButtonClick() {

        this.moveMap(googleMap.getMyLocation(), 14.0f);
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        showDialogConfirm(location);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());

        this.googleMap = googleMap;

        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.setOnMyLocationButtonClickListener(this);
        this.googleMap.setOnMyLocationClickListener(this);
        this.googleMap.setOnCameraMoveListener(this);


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, getListener());

        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCameraMove() {

        this.moveMap(googleMap.getCameraPosition());
    }

    @Override
    public void showMissing() {

    }

    @Override
    public void showMarkers(List<StoreDto> stores) {

        if (!stores.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            stores.forEach(x -> {
                builder.include(x.getPosition());
                setMarkerOnMap(x);
            });

            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
            googleMap.moveCamera(cameraUpdate);
        }

    }

    @Override
    public void showEmptyMaps() {
        showToast("Do not have a markers on stores.");
    }

    @Override
    public void showSaveFavoriteStore(float latitude, float longitude) {

        Intent intent = new Intent(getContext(), StoreFormActivity.class);
        intent.putExtra("MAP_LATITUDE", latitude);
        intent.putExtra("MAP_LONGITUDE", longitude);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showToast(String message){

        Intent toSend = new Intent();

        toSend.setAction(NotificationConst.getPurchasedToast());
        toSend.putExtra(NotificationConst.getPurchasedToastMessage(), message);

        getActivity().sendBroadcast(toSend, "application.purchases.purchasescollection.HANDLE_INTENT");
    }

    @Override
    public void setPresenter(IStoreMapPresenter presenter) {

        storeMapPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    private LocationListener getListener() {

        if(locationListener == null)
            buildListener();

        return locationListener;
    }

    private void buildListener() {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                CameraPosition cameraPosition = googleMap.getCameraPosition();
                showCurrentLocation(location);
                moveMap(location, cameraPosition.zoom);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {


            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    private void setMarkerOnMap(StoreDto store) {

        googleMap.addMarker(new MarkerOptions()
                .position(store.getPosition())
                .title(store.getName()));
    }

    private void showDialogConfirm(@NonNull Location location){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Save favorite store");
        builder.setMessage("You are about to favorites the store. Do you want to save store data?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> storeMapPresenter.toSaveFavoriteStore(location));

        builder.setNegativeButton("No", (dialog, which) ->
                showToast("Find another store and add to your favorites."));

        builder.show();
    }

    private void showCurrentLocation(Location location){

        try{

            locationLatitude.setText(geographicSize(location.getLatitude(), "Lat"));
            locationLongtitude.setText(geographicSize(location.getLongitude(), "Long"));
        } catch(Exception ex){

            ex.getMessage();
        }
    }

    private void moveMap(CameraPosition cameraPosition) {

        this.setMapType(cameraPosition.zoom);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition.target, cameraPosition.zoom));
    }

    private void moveMap(Location location, float v ) {

        this.setMapType(v);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, v));
    }


    private void setMapType(float zoom){


        if(zoom > 18.0) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        } else {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    private String geographicSize(double size, String prefix) {

        double sizeConverts = BigDecimal.valueOf(size)
                .setScale(5, RoundingMode.HALF_UP)
                .doubleValue();

        return prefix + ": " + String.valueOf(sizeConverts);
    }
}
