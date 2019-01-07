package purchases.application.purchasescollection.client.store.contract.presenter;

import purchases.application.purchasescollection.common.contract.IPresenter;

public interface IStoreFormPresenter extends IPresenter {

    void saveStore(String storeName, String storeDescription, double storeRadius);

    void verifyForm();

    boolean isVerify();
}
