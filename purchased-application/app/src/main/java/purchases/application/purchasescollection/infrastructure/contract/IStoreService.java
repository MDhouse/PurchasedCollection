package purchases.application.purchasescollection.infrastructure.contract;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadMapInformation;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadStores;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCounter;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCreate;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreSearch;

public interface IStoreService {

    void getAll(@NonNull String id, @NonNull ILoadStores callback);

    void radiusCounter(@NonNull StoreCounter storeCounter);

    void deleteAll();

    void get(@NonNull StoreSearch storeSearch, @NonNull ILoadMapInformation callback);

    void create(@NonNull StoreCreate newStore);
}
