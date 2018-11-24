package purchases.application.purchasescollection.addEditProduct;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.data.source.interfaces.ProductDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditProductPresenter implements AddEditProductContract.Presenter, ProductDataSource.LoadProductCallback
{
    @NonNull
    private final ProductDataSource productDataSource;

    @NonNull
    private final AddEditProductContract.View addProductView;

    @Nullable
    private String productId;

    private Product product;

    private boolean isDataMissing;

    public AddEditProductPresenter(@NonNull ProductDataSource productDataSource,
                                   @NonNull AddEditProductContract.View addProductView,
                                   @Nullable String productId,
                                   boolean isDataMissing) {

        this.productDataSource = checkNotNull(productDataSource);
        this.addProductView = checkNotNull(addProductView);
        this.productId = productId;
        this.isDataMissing = isDataMissing;

        this.addProductView.setPresenter(this);
    }

    @Override
    public void start() {

        if (onStart()) {
            populateProduct();
            addProductView.showSwitch();
        } else {
            addProductView.hideSwitch();
        }
    }

    @Override
    public void saveProduct(String name, double price, int amount, boolean buy) {

        if (isNewProduct()) {
            createProduct(name, price, amount);
        } else {
            updateProduct(name, price, amount, buy);
        }
    }

    @Override
    public void populateProduct() {
        if (isNewProduct()) {
            throw new RuntimeException("populateTask() was called but task is new.");
        }
        productDataSource.getProduct(productId, this);
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

        if(this.product.getBuy() != buy) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isNewProduct() {

        return productId == null;
    }

    @Override
    public boolean isDataMissing() {

        return isDataMissing;
    }

    @Override
    public void onProductLoad(Product product) {

        if (addProductView.isActive()) {

            this.product = product;

            addProductView.setName(product.getName());
            addProductView.setPrice(product.getPrice());
            addProductView.setAmount(product.getAmount());
            addProductView.setBuy(product.getBuy());
        }

        isDataMissing = false;
    }

    @Override
    public void onDataNotAvailable() {

        if (addProductView.isActive()) {
            addProductView.showEmptyProductError();
        }
    }


    private boolean onStart() {

        return !isNewProduct() && isDataMissing();
    }

    private void createProduct(String name, double price, int amount) {

        Product newProduct = new Product(name, price, amount);

        if (newProduct.isEmpty()) {
            addProductView.showEmptyProductError();
        } else {
            productDataSource.saveProduct(newProduct);
            addProductView.toIntent(newProduct.getId(), newProduct.toString());
            addProductView.showProductList();
        }
}

    private void updateProduct(String name, double price, int amount, boolean buy) {

        productDataSource.saveProduct(new Product(productId, name, price, amount, buy));
        addProductView.showProductList();
    }

//    private void productIntent(String productInformation) {
//
//        Intent myServiceIntent = new Intent(this, "AddEditProductPresenter");
//
//        this.broadcastService.onHandleIntent(news);
//
//
//    }
}
