package purchases.application.purchasescollection.client.store.contract.view;

import purchases.application.purchasescollection.client.store.contract.presenter.IStoreFormPresenter;
import purchases.application.purchasescollection.common.contract.IView;

public interface IStoreFormView extends IView<IStoreFormPresenter> {

    void showStoreList();
    void formValidate();

    void showToast(String message);

    void showLocationInfo(String location);
}
