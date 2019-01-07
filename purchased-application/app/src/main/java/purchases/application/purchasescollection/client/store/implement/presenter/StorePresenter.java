package purchases.application.purchasescollection.client.store.implement.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.client.store.contract.presenter.IStorePresenter;
import purchases.application.purchasescollection.client.store.contract.view.IStoreView;
import purchases.application.purchasescollection.infrastructure.contract.IAuthenticationService;
import purchases.application.purchasescollection.infrastructure.contract.IStoreService;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadStores;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadUser;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCounter;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;
import purchases.application.purchasescollection.infrastructure.model.dto.UserDto;

import static com.google.common.base.Preconditions.checkNotNull;

public class StorePresenter implements IStorePresenter {

    @NonNull
    private final IStoreView storeView;

    @NonNull
    private final IStoreService storeService;

    @NonNull
    private final IAuthenticationService authenticationService;

    private UserDto currentUser;

    public StorePresenter(@NonNull IStoreView storeView, @NonNull IStoreService storeService, @NonNull IAuthenticationService authenticationService) {
        this.storeView = storeView;
        this.storeService = storeService;
        this.authenticationService = authenticationService;

        this.storeView.setPresenter(this);
    }

    @Override
    public void start() {

        loadUser();
        loadStore();
    }

    @Override
    public void loadStore() {

        storeService.getAll(currentUser.getId(), new ILoadStores(){

            @Override
            public void load(List<StoreDto> stores) {
                storeView.showStores(stores);
            }

            @Override
            public void notAvailable() {
                if(storeView.isActive())
                    storeView.showNoStores();
            }
        });
    }

    @Override
    public void loadMaps() {

        storeView.showMaps();
    }

    @Override
    public void radiusCounter(@NonNull StoreCounter storeCounter) {

        checkNotNull(storeCounter);
        storeService.radiusCounter(storeCounter);
        loadStore();
    }

    @Override
    public void deleteStore() {

        storeService.deleteAll();
    }

    @Override
    public String getUuid() {
        return currentUser.getId();
    }

    private void loadUser(){

        authenticationService.getCurrentUser(new ILoadUser() {
            @Override
            public void load(UserDto user) {
                currentUser = user;
            }

            @Override
            public void notAvailable() {
                currentUser = null;
            }
        });
    }
}
