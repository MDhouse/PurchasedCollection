package purchases.application.purchasescollection.infrastructure.contract.callback;

import com.google.android.gms.common.ConnectionResult;

public interface ITrackRegisterCallback {

    void onApiClientConnected();
    void onApiClientSuspended();
    void onApiClientConnectionFailed(ConnectionResult connectionResult);

    void onTrackRegisteredSuccessful();
}
