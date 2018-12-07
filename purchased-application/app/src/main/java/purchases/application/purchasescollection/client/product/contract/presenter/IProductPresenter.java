package purchases.application.purchasescollection.client.product.contract.presenter;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.client.product.contract.IPresenter;

public interface IProductPresenter extends IPresenter {

    void loadProducts();

    void createProduct();

    void detailProduct(@NonNull String id);

    void productTransaction(@NonNull String id, @NonNull boolean isBuy);
}
