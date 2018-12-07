package purchases.application.purchasescollection.client.product.contract.presenter;

import purchases.application.purchasescollection.client.product.contract.IPresenter;

public interface IProductFormPresenter extends IPresenter {

    void saveProduct(String name, double price, int amount, boolean buy);

    void populateProduct();

    boolean isNewProduct();

    boolean isVerify(String name, double price, int amount, boolean buy);

    boolean isDataMissing();
}
