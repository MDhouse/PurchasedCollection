package purchases.application.purchasescollection.products;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import purchases.application.purchasescollection.addEditProduct.AddEditProductActivity;
import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.data.source.interfaces.ProductDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductsPresenter implements ProductsContract.Presenter {

    private final ProductDataSource productDataSource;

    private final ProductsContract.View productView;

    public ProductsPresenter(@NonNull ProductDataSource productDataSource, @NonNull ProductsContract.View productView) {
        this.productDataSource = checkNotNull(productDataSource);
        this.productView = checkNotNull(productView);

        this.productView.setPresenter(this);
    }

    @Override
    public void start() {
        loadProducts(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddEditProductActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {

        }
    }

    @Override
    public void loadProducts(boolean forceUpdate) {

        productDataSource.getProducts(new ProductDataSource.LoadProductsCallback() {
            @Override
            public void onProductsLoad(List<Product> products) {
                productView.showProducts(products);
            }

            @Override
            public void onDataNotAvailable() {
                if (productView.isActive()){
                    productView.showNoProducts();
                }
            }
        });
    }

    @Override
    public void addNewProduct() {
        productView.showAddProduct();
    }

    @Override
    public void openProductDetail(@NonNull Product product) {

        checkNotNull(product);
        productView.showDetailProduct(product.getId());
    }

    @Override
    public void buyProduct(@NonNull Product product) {

        checkNotNull(product);
        productDataSource.buyProduct(product);
        loadProducts(false);
    }

    @Override
    public void notBuyProduct(@NonNull Product product) {
        checkNotNull(product);
        productDataSource.notBuyProduct(product);
    }
}
