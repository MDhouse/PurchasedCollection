package purchases.application.purchasescollection.data.source.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.data.Product;

public interface ProductDataSource {

    interface LoadProductsCallback {

        void onProductsLoad(List<Product> products);

        void onDataNotAvailable();
    }

    interface LoadProductCallback {

        void onProductLoad(Product product);

        void onDataNotAvailable();
    }

    void getProducts(@NonNull LoadProductsCallback callback);

    void getProduct(@NonNull String id, @NonNull LoadProductCallback callback);

    void saveProduct(@NonNull Product product);

    void notBuyProduct(Product product);

    void buyProduct(@NonNull String id);

    void buyProduct(@NonNull Product product);

    void deleteAllProducts();

    void deleteProduct(@NonNull String id);
}
