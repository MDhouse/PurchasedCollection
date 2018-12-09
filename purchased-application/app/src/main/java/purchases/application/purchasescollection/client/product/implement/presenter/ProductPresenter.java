package purchases.application.purchasescollection.client.product.implement.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.client.product.contract.presenter.IProductPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductView;
import purchases.application.purchasescollection.infrastructure.contract.ILoadProducts;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductPresenter implements IProductPresenter {

    @NonNull
    private final IProductView productView;
    @NonNull
    private final IProductService productService;

    public ProductPresenter(@NonNull IProductView productView, @NonNull IProductService productService) {
        this.productView = productView;
        this.productService = productService;

        this.productView.setPresenter(this);
    }

    @Override
    public void start() {
        loadProducts();
    }

    @Override
    public void loadProducts() {

        productService.getAll(new ILoadProducts() {
            @Override
            public void load(List<ProductDto> products) {
                productView.showProduct(products);
            }

            @Override
            public void notAvailable() {
                if(productView.isActive())
                    productView.showNoProducts();
            }
        });
    }

    @Override
    public void createProduct() {

        productView.showFormProduct();
    }

    @Override
    public void detailProduct(@NonNull String id) {

        checkNotNull(id);
        productView.showDetailProduct(id);
    }

    @Override
    public void productTransaction(@NonNull String id, @NonNull boolean isBuy) {

        checkNotNull(id);
        checkNotNull(isBuy);

        productService.transaction(new ProductTransaction(id, isBuy));
        loadProducts();
    }
}
