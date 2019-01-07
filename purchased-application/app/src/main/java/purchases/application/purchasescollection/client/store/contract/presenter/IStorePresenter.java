package purchases.application.purchasescollection.client.store.contract.presenter;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.common.contract.IPresenter;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCounter;

public interface IStorePresenter extends IPresenter {

    void loadStore();

    void loadMaps();

    void radiusCounter(@NonNull StoreCounter storeCounter);

    void deleteStore();

    String getUuid();
}
