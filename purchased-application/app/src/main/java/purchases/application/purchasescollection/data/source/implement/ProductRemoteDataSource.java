package purchases.application.purchasescollection.data.source.implement;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.data.source.interfaces.ProductDataSource;

public class ProductRemoteDataSource implements ProductDataSource {

    private static volatile ProductRemoteDataSource INSTANCE;

    public ProductRemoteDataSource() {
    }

    public static ProductRemoteDataSource getInsance() {

        if(INSTANCE == null) {
            synchronized (ProductRemoteDataSource.class) {
                if(INSTANCE == null) {
                    INSTANCE = new ProductRemoteDataSource();
                }
            }
        }

        return INSTANCE;
    }

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
