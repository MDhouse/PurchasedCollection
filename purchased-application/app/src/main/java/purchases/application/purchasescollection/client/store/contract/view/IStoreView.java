package purchases.application.purchasescollection.client.store.contract.view;

import java.util.List;

import purchases.application.purchasescollection.client.store.contract.presenter.IStorePresenter;
import purchases.application.purchasescollection.common.contract.IView;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;

public interface IStoreView extends IView<IStorePresenter> {

    void showStores(List<StoreDto> stores);

    void showNoStores();

    void showMaps();
}
