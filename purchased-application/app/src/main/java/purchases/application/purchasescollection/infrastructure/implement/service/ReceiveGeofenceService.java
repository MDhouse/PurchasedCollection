package purchases.application.purchasescollection.infrastructure.implement.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;


public class ReceiveGeofenceService extends IntentService {

    private final String TAG = getClass().getSimpleName();

    public ReceiveGeofenceService() {
        super("ReceiveGeofenceService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if (event != null) {

            if (event.hasError()) {
                onError(event.getErrorCode());
            } else {

                int transition = event.getGeofenceTransition();

                if (transition == Geofence.GEOFENCE_TRANSITION_ENTER || transition == Geofence.GEOFENCE_TRANSITION_DWELL || transition == Geofence.GEOFENCE_TRANSITION_EXIT) {

                    String names = fromEvent(event.getTriggeringGeofences());

                    toShowInformation(transition, names);
                }
            }
        }
    }

    private void toShowInformation(int transition, String storesName) {

        if (transition == Geofence.GEOFENCE_TRANSITION_EXIT)
            onExited(storesName);
        else
            onEntered(storesName);
    }


    private void onEntered(String namePlace){

        showToast("You are inside " + namePlace);
    }

    private void onExited(String namePlace) {

        showToast("You are outside " + namePlace);
    }

    private void onError(int errorCode) {

        Log.d(TAG, "You are error Store");
    }


    public void showToast(String message) {

        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show());
    }

    private String fromEvent(List<Geofence> geofences){
        int count = geofences.size();

        if(count == 1){
            return geofences.get(count - 1).getRequestId().trim();
        }

        return fromFor(geofences);
    }

    private String fromFor(List<Geofence> geofences){

        String storeName = "";
        int counter = 0;

        for(Geofence geofence : geofences){
            String names = geofence.getRequestId();

            if (containsWhiteSpace(names)){

                storeName = clone(counter, storeName, names.trim());
                counter++;
            }
        }

        return storeName.trim();
    }

    private String clone(int counter, String old, String names) {

        if(counter == 0)
            return names;

        if(counter == 1)
            return old + " " + names;

        return old + " | " + names;
    }

    public static boolean containsWhiteSpace(final String testCode){
        if(testCode != null){
            for(int i = 0; i < testCode.length(); i++){
                if(Character.isWhitespace(testCode.charAt(i))){
                    return true;
                }
            }
        }
        return false;
    }
}
