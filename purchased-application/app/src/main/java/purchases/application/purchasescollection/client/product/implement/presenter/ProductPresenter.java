package purchases.application.purchasescollection.client.product.implement.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.client.product.contract.presenter.IProductPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductView;
import purchases.application.purchasescollection.infrastructure.contract.IAuthenticationService;
import purchases.application.purchasescollection.infrastructure.contract.ILoadProducts;
import purchases.application.purchasescollection.infrastructure.contract.ILoadUser;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.implement.firebase.AuthenticationFirebaseService;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.infrastructure.model.dto.UserDto;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductPresenter implements IProductPresenter {

    @NonNull
    private final IProductView productView;
    @NonNull
    private final IProductService productService;
    @NonNull
    private final IAuthenticationService authenticationService;

    private UserDto currentUser;

    public ProductPresenter(@NonNull IProductView productView, @NonNull IProductService productService, @NonNull IAuthenticationService authenticationFirebaseService) {
        this.productView = productView;
        this.productService = productService;
        this.authenticationService = authenticationFirebaseService;

        this.productView.setPresenter(this);
    }

    @Override
    public void start() {
        loadUser();
        loadProducts();
    }

    @Override
    public void loadProducts() {

        productService.getAll(currentUser.getId(), new ILoadProducts() {
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

    private void loadUser(){

        authenticationService.getCurrentUser(new ILoadUser() {
            @Override
            public void load(UserDto user) {
                currentUser = user;
            }

            @Override
            public void notAvailable() {
                currentUser = null;
            }
        });
    }
}
