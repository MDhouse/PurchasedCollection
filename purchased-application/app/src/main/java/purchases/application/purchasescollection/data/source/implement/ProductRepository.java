package purchases.application.purchasescollection.data.source.implement;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.data.source.interfaces.ProductDataSource;

public class ProductRepository implements ProductDataSource {



    @Override
    public void getProducts(@NonNull LoadProductsCallback callback) {

    }

    @Override
    public void getProduct(@NonNull String id, @NonNull LoadProductCallback callback) {

    }

    @Override
    public void saveProduct(@NonNull Product product) {

    }

    @Override
    public void notBuyProduct(Product product) {

    }

    @Override
    public void buyProduct(@NonNull String id) {

    }

    @Override
    public void buyProduct(@NonNull Product product) {

    }

    @Override
    public void deleteAllProducts() {

    }

    @Override
    public void deleteProduct(@NonNull String id) {

    }
}
