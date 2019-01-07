package purchases.application.purchasescollection.infrastructure.implement.registry;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import purchases.application.purchasescollection.infrastructure.contract.callback.ITrackRegisterCallback;
import purchases.application.purchasescollection.infrastructure.implement.service.ReceiveGeofenceService;
import purchases.application.purchasescollection.infrastructure.model.dto.TrackDto;

public class TrackRegister implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Context context;
    private GoogleApiClient googleApiClient;
    private List<Geofence> geoTracks;
    private PendingIntent trackPendingIntent;
    private GeofencingClient geofencingClient;

    private ITrackRegisterCallback callback;


    public TrackRegister(Context context) {
        this.context = context;
        this.geoTracks = new ArrayList<>();
        this.geofencingClient = LocationServices.getGeofencingClient(context);
    }

    public void registerTracks(List<TrackDto> tracks) {
        connect();
        buildTracks(tracks);
    }

    private void connect() {

        if ( googleApiClient == null ) {
            googleApiClient = new GoogleApiClient.Builder( context )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
        }

        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (callback != null) {
            callback.onApiClientConnected();
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.geofencingClient
                .addGeofences(getTracksRequest(), getTrackPendingIntent())
                .addOnSuccessListener(v -> {
                    if(callback != null){
                        callback.onTrackRegisteredSuccessful();
                    }
                })
                .addOnFailureListener(e -> {});
    }

    @Override
    public void onConnectionSuspended(int i) {

        if(callback != null){
            callback.onApiClientSuspended();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        if(callback != null){
            callback.onApiClientConnectionFailed(connectionResult);
        }
    }

    public void onDisconnect() {
        googleApiClient.disconnect();
    }

    public void setCallback(ITrackRegisterCallback callback) {
        this.callback = callback;
    }

    private PendingIntent getTrackPendingIntent() {

        if(isNull())
            createRequestPendingIntent();

        return trackPendingIntent;
    }

    private void createRequestPendingIntent() {

        Intent intent = new Intent(context, ReceiveGeofenceService.class);
        trackPendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean isNull(){
        return trackPendingIntent == null;
    }

    private void buildTracks(List<TrackDto> tracks){

        tracks.forEach(x ->
            geoTracks.add(getGeoTrack(x))
        );
    }

    private Geofence getGeoTrack(TrackDto track) {

        return new Geofence.Builder()
                .setRequestId(track.getName())
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setCircularRegion(track.getLatitude(), track.getLongitude(), track.getRadius())
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .build();
    }

    private GeofencingRequest getTracksRequest() {

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geoTracks);

        return builder.build();
    }
}
