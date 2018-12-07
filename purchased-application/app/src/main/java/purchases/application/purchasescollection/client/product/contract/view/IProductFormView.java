package purchases.application.purchasescollection.client.product.contract.view;

import purchases.application.purchasescollection.client.product.contract.IView;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductFormPresenter;

public interface IProductFormView extends IView<IProductFormPresenter> {

    void showEmptyProductError();

    void showProductList();

    void showSwitch();

    void hideSwitch();

    void setName(String name);

    void setPrice(double price);

    void setAmount(int amount);

    void setBuy(boolean buy);

    void toIntent(String idProduct, String contentProduct);

    boolean isActive();
}
