package purchases.application.purchasescollection.client.store.implement.presenter;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import purchases.application.purchasescollection.client.store.contract.presenter.IStoreFormPresenter;
import purchases.application.purchasescollection.client.store.contract.view.IStoreFormView;
import purchases.application.purchasescollection.infrastructure.contract.IAuthenticationService;
import purchases.application.purchasescollection.infrastructure.contract.IStoreService;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadUser;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCreate;
import purchases.application.purchasescollection.infrastructure.model.dto.UserDto;

public class StoreFormPresenter implements IStoreFormPresenter {

    @NonNull
    private final IStoreFormView storeFormView;
    @NonNull
    private final IStoreService storeService;
    @NonNull
    private final IAuthenticationService authenticationService;

    private float latitude;
    private float longitude;

    private String uuid;

    public StoreFormPresenter(@NonNull IStoreFormView storeFormView, @NonNull IStoreService storeService, @NonNull IAuthenticationService authenticationService, float latitude, float longitude) {
        this.storeFormView = storeFormView;
        this.storeService = storeService;
        this.authenticationService = authenticationService;
        this.latitude = latitude;
        this.longitude = longitude;

        this.storeFormView.setPresenter(this);
    }

    @Override
    public void start() {
        getUserId();

        storeFormView.showLocationInfo(getLocation());

    }

    @Override
    public void saveStore(String storeName, String storeDescription, double storeRadius) {

        StoreCreate newStore = new StoreCreate(storeName, storeDescription, latitude, longitude, storeRadius, uuid);

        storeService.create(newStore);

        storeFormView.showStoreList();
    }

    @Override
    public void verifyForm() {

        storeFormView.formValidate();
    }

    @Override
    public boolean isVerify() {
        return false;
    }


    private void getUserId(){

        authenticationService.getCurrentUser(new ILoadUser() {
            @Override
            public void load(UserDto user) {
                uuid = user.getId();
            }

            @Override
            public void notAvailable() {
                uuid = null;
            }
        });
    }

    private String getLocation(){

        return "Current latitude: " + String.valueOf(latitude) + " and longitude: " + String.valueOf(longitude);
    }
}
