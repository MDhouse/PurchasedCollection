package purchases.application.purchasescollection.client.store.implement.presenter;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.common.base.Strings;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import purchases.application.purchasescollection.client.store.contract.presenter.IStoreMapPresenter;
import purchases.application.purchasescollection.client.store.contract.view.IStoreMapView;
import purchases.application.purchasescollection.infrastructure.contract.IStoreService;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadMapInformation;
import purchases.application.purchasescollection.infrastructure.contract.callback.ITrackRegisterCallback;
import purchases.application.purchasescollection.infrastructure.implement.registry.TrackRegister;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreSearch;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;
import purchases.application.purchasescollection.infrastructure.model.dto.TrackDto;

import static com.google.common.base.Preconditions.checkNotNull;

public class StoreMapPresenter implements IStoreMapPresenter {

    @NonNull
    private final IStoreMapView storeMapView;
    @NonNull
    private final IStoreService storeService;
    @NonNull
    private final Context context;
    @NonNull
    private final boolean showStore;

    @Nullable
    private StoreSearch storeSearch;

    private TrackRegister trackRegister;
    private ITrackRegisterCallback trackRegisterCallback;

    public StoreMapPresenter(@NonNull IStoreMapView storeMapView, @NonNull IStoreService storeService, @NonNull Context context, @NonNull boolean showStore, @Nullable StoreSearch storeId) {
        this.storeMapView = storeMapView;
        this.storeService = storeService;
        this.context = context;
        this.showStore = showStore;
        this.storeSearch = storeId;

        this.storeMapView.setPresenter(this);
    }

    @Override
    public void start() {

        setTrackRegister();
        if(showStore)
            openStore();
    }

    @Override
    public void toSaveFavoriteStore(Location location) {

        checkNotNull(location);

        storeMapView.showSaveFavoriteStore((float) location.getLatitude(), (float) location.getLongitude());
    }

    private void openStore() {

        if (this.storeSearch != null) {

            storeService.get(this.storeSearch, new ILoadMapInformation(){
                @Override
                public void load(List<StoreDto> store) {

                    if(!storeMapView.isActive())
                        return;

                    if(isNullOrEmpty(store))
                        storeMapView.showMissing();

                    storeMapView.showMarkers(store);
                    registerTracks(store);
                }

                private boolean isNullOrEmpty(List<StoreDto> store){
                    return null == store || store.isEmpty();
                }

                @Override
                public void notAvailable() {
                    if(!storeMapView.isActive())
                        return;

                    storeMapView.showMissing();
                }
            });
        }


    }

    private void setTrackRegister() {

        if(trackRegister == null)
            trackRegister = new TrackRegister(context);
    }

    private void registerTracks(List<StoreDto> store) {

        trackRegister.setCallback(getTrackRegisterCallback());
        trackRegister.registerTracks(getTracks(store));
    }

    private List<TrackDto> getTracks(List<StoreDto> store) {

        return store.stream().map(StoreDto::getTrack).collect(Collectors.toList());
    }

    private ITrackRegisterCallback getTrackRegisterCallback() {

        if(isNull())
            createCallback();

        return trackRegisterCallback;
    }

    private void createCallback() {

        trackRegisterCallback = new ITrackRegisterCallback() {
            @Override
            public void onApiClientConnected() {
                storeMapView.showToast("Api client connected");
            }

            @Override
            public void onApiClientSuspended() {
                storeMapView.showToast("Api client suspend");
            }

            @Override
            public void onApiClientConnectionFailed(ConnectionResult connectionResult) {
                storeMapView.showToast("Api client failed");
            }

            @Override
            public void onTrackRegisteredSuccessful() {
                storeMapView.showToast("Successful registry track");
            }
        };
    }

    private boolean isNull() {
        return trackRegisterCallback == null;
    }
}
