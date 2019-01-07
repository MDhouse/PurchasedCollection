package purchases.application.purchasescollection.client.store.contract.presenter;

import android.location.Location;

import purchases.application.purchasescollection.common.contract.IPresenter;

public interface IStoreMapPresenter extends IPresenter {

    void toSaveFavoriteStore(Location location);
}
