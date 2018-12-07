package purchases.application.purchasescollection.client.product.contract.presenter;

import purchases.application.purchasescollection.client.product.contract.IPresenter;

public interface IProductDetailPresenter extends IPresenter {

    void editProduct();

    void deleteProduct();

    void buyProduct();
}
