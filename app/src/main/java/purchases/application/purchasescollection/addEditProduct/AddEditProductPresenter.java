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

    private boolean isDataMissing;

    public AddEditProductPresenter(@NonNull ProductDataSource productDataSource,
                                   @NonNull AddEditProductContract.View mAddTaskView,
                                   @Nullable String productId,
                                   boolean isDataMissing) {
        this.productDataSource = checkNotNull(productDataSource);
        this.addProductView = checkNotNull(mAddTaskView);
        this.productId = productId;
        this.isDataMissing = isDataMissing;

        this.addProductView.setPresenter(this);
    }

    @Override
    public void start() {
        if (!isNewProduct() && isDataMissing()) {
            populateTask();
            addProductView.showSwitch();
        } else {
            addProductView.hideSwitch();
        }
    }

    @Override
    public void populateTask() {
        if (isNewProduct()) {
            throw new RuntimeException("populateTask() was called but task is new.");
        }
        productDataSource.getProduct(productId, this);
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
    public boolean isDataMissing() {
        return isDataMissing;
    }

    @Override
    public void onProductLoad(Product product) {

        if (addProductView.isActive()) {
            addProductView.setName(product.getName());
            addProductView.setPrice(product.getPrice());
            addProductView.setAmount(product.getAmount());
        }
        isDataMissing = false;
    }

    @Override
    public void onDataNotAvailable() {

        if (addProductView.isActive()) {
            addProductView.showEmptyProductError();
        }
    }


    private boolean isNewProduct() {
        return productId == null;
    }

    private void createProduct(String name, double price, int amount) {

        Product newProduct = new Product(name, price, amount);

        if (newProduct.isEmpty()) {
            addProductView.showEmptyProductError();
        } else {
            productDataSource.saveProduct(newProduct);
            addProductView.showProductList();
        }
}

    private void updateProduct(String name, double price, int amount, boolean buy) {
        if (isNewProduct()) {
            throw new RuntimeException("updateProduct() was called but task is new.");
        }

        productDataSource.saveProduct(new Product(productId, name, price, amount, buy));
        addProductView.showProductList();
    }
}
