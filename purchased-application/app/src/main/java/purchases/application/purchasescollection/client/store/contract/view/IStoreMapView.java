package purchases.application.purchasescollection.client.store.contract.view;

import java.util.List;

import purchases.application.purchasescollection.client.store.contract.presenter.IStoreMapPresenter;
import purchases.application.purchasescollection.common.contract.IView;
import purchases.application.purchasescollection.infrastructure.model.dto.MarkerDto;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;

public interface IStoreMapView extends IView<IStoreMapPresenter> {

    void showMissing();

    void showMarkers(List<StoreDto> stores);

    void showEmptyMaps();

    void showSaveFavoriteStore(float latitude, float longitude);

    void showToast(String message);
}
