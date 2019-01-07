package purchases.application.purchasescollection.infrastructure.model.command.store;

import com.google.common.base.Strings;

public class StoreSearch {

    private String storeId;
    private String uuid;

    public StoreSearch( String storeId, String uuid) {
        this.storeId = storeId;
        this.uuid = uuid;
    }

    public boolean isAllStoresUser() {
        return Strings.isNullOrEmpty(storeId);
    }

    public boolean isAllStores(){
        return Strings.isNullOrEmpty(storeId) && Strings.isNullOrEmpty(uuid);
    }

    public String getStoreId() {
        return storeId;
    }

    public String getUuid() {
        return uuid;
    }
}
