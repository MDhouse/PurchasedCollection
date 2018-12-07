package purchases.application.purchasescollection.client.product.implement.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import purchases.application.purchasescollection.client.product.contract.presenter.IProductFormPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductFormView;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;

public class ProductFormPresenter implements IProductFormPresenter {

    @NonNull
    private final IProductFormView productFormView;
    @NonNull
    private final IProductService productService;
    @Nullable
    private final String productId;

    public ProductFormPresenter(@NonNull IProductFormView productFormView, @NonNull IProductService productService, @Nullable String productId) {
        this.productFormView = productFormView;
        this.productService = productService;
        this.productId = productId;

        this.productFormView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void saveProduct(String name, double price, int amount, boolean buy) {

    }

    @Override
    public void populateProduct() {

    }

    @Override
    public boolean isNewProduct() {
        return false;
    }

    @Override
    public boolean isVerify(String name, double price, int amount, boolean buy) {
        return false;
    }

    @Override
    public boolean isDataMissing() {
        return false;
    }


}
