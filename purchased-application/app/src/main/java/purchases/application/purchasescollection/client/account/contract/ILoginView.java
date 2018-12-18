package purchases.application.purchasescollection.client.account.contract;

import purchases.application.purchasescollection.common.contract.IView;

public interface ILoginView extends IView<ILoginPresenter> {

    void showLoginForm();
    void showCreateForm();

    void showProductActivity(String userName);

    void showErrorToast(String message);
}
