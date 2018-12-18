package purchases.application.purchasescollection.client.product.implement.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import purchases.application.purchasescollection.client.product.contract.presenter.IProductFormPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductFormView;
import purchases.application.purchasescollection.infrastructure.contract.IAuthenticationService;
import purchases.application.purchasescollection.infrastructure.contract.ILoadProduct;
import purchases.application.purchasescollection.infrastructure.contract.ILoadUser;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductUpdate;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.infrastructure.model.dto.UserDto;

public class ProductFormPresenter implements IProductFormPresenter, ILoadProduct {

    @NonNull
    private final IProductFormView productFormView;
    @NonNull
    private final IProductService productService;
    @NonNull
    private final IAuthenticationService authenticationService;

    @Nullable
    private final String productId;

    private boolean isDataMissing;

    private ProductDto product;
    private String uuid;

    public ProductFormPresenter(@NonNull IProductFormView productFormView, @NonNull IProductService productService, @NonNull IAuthenticationService authenticationService, @Nullable String productId, boolean isDataMissing) {
        this.productFormView = productFormView;
        this.productService = productService;
        this.authenticationService = authenticationService;
        this.productId = productId;
        this.isDataMissing = isDataMissing;

        this.productFormView.setPresenter(this);
    }

    @Override
    public void start() {

        getUserId();

        if(getStarted()){
            populateProduct();
            productFormView.showSwitch();
        } else {
            productFormView.hideSwitch();
        }
    }

    @Override
    public void saveProduct(String name, double price, int amount, boolean buy) {

        if (isNewProduct()) {
            createProduct(name, price, amount);
        } else {
            updateProduct(name, price, amount, buy);
        }

        productFormView.showProductList();
    }

    @Override
    public void populateProduct() {

        productService.get(new ProductSearch(productId, null), this);
    }

    @Override
    public void verifyForm() {

        productFormView.formValidate();
    }

    @Override
    public boolean isNewProduct() {

        return productId == null;
    }

    @Override
    public boolean isVerify(String name, double price, int amount, boolean buy) {

        if (!this.product.getName().equalsIgnoreCase(name)) {
            return true;
        }

        if(this.product.getPrice() != price) {
            return true;
        }

        if(this.product.getAmount() != amount) {
            return true;
        }

        if(this.product.isBuy() != buy) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isDataMissing() {
        return isDataMissing;
    }

    @Override
    public void load(ProductDto product) {

        if(productFormView.isActive())
            productFormView.setProduct(product);

        setProduct(product);
        setDataMissing(false);
    }

    @Override
    public void notAvailable() {

        if(productFormView.isActive())
            productFormView.showEmptyProductError();
    }

    private void updateProduct(String name, double price, int amount, boolean buy) {

        productService.update(new ProductUpdate(productId, name, price, amount, buy));
    }

    private void createProduct(String name, double price, int amount) {


        purchases.application.purchasescollection.infrastructure.model.firebase.Product newProduct = new ProductCreate(name, price, amount, uuid).toFirebase();

        productService.create(newProduct);
        productFormView.toIntent(newProduct.getId(), newProduct.toString());
    }

    private boolean getStarted() {

        return !isNewProduct() && isDataMissing();
    }

    private void setProduct(ProductDto product) {
        this.product = product;
    }

    private void setDataMissing(boolean dataMissing) {
        isDataMissing = dataMissing;
    }

    private void getUserId(){

        authenticationService.getCurrentUser(new ILoadUser() {
            @Override
            public void load(UserDto user) {
                uuid = user.getId();
            }

            @Override
            public void notAvailable() {
                uuid = null;
            }
        });
    }
}
