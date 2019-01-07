package purchases.application.purchasescollection.client.product.implement.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import purchases.application.purchasescollection.client.product.contract.presenter.IProductDetailPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductDetailView;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadProduct;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public class ProductDetailPresenter implements IProductDetailPresenter {

    @NonNull
    private final IProductService productService;

    @NonNull
    private final IProductDetailView productDetailView;

    @Nullable
    private String productId;

    public ProductDetailPresenter(@NonNull IProductService productService, @NonNull IProductDetailView productDetailView, @Nullable String productId) {
        this.productService = productService;
        this.productDetailView = productDetailView;
        this.productId = productId;

        productDetailView.setPresenter(this);
    }

    @Override
    public void start() {

        productDetailView.showLoadProduct();

        openProduct();
    }

    @Override
    public void editProduct() {

        if(Strings.isNullOrEmpty(productId)){
            productDetailView.showMissingProduct();
            return;
        }

        productDetailView.showEditedProduct(productId);
    }

    @Override
    public void deleteProduct() {
        if(Strings.isNullOrEmpty(productId)){
            productDetailView.showMissingProduct();
            return;
        }

        delete();
    }

    @Override
    public void buyProduct() {

        if(Strings.isNullOrEmpty(productId)){
            productDetailView.showMissingProduct();
            return;
        }

        buy();
    }

    private void openProduct() {

        if(Strings.isNullOrEmpty(productId)){
            productDetailView.showMissingProduct();
            return;
        }

        load();
    }

    private void delete() {
        productDetailView.showLoadProduct();
        productService.delete(new ProductDelete(productId));
        productDetailView.showDeletedProduct();
    }

    private void buy() {

        productDetailView.showLoadProduct();
        productService.transaction(new ProductTransaction(productId, true));
        load();
    }

    private void load() {

        productService.get(new ProductSearch(productId, null),
                new ILoadProduct() {
                    @Override
                    public void load(ProductDto product) {
                        if(!productDetailView.isActive())
                            return;

                        if(null == product || product.isEmpty())
                            productDetailView.showMissingProduct();
                        else {
                            productDetailView.setDetailProduct(product);
                            productDetailView.showDetailProduct();
                        }
                    }

                    @Override
                    public void notAvailable() {
                        if(!productDetailView.isActive())
                            return;

                        productDetailView.showMissingProduct();
                    }
                });
    }
}
