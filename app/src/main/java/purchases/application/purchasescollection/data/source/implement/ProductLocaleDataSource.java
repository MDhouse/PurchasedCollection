package purchases.application.purchasescollection.data.source.implement;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.data.source.configuration.ProductDao;
import purchases.application.purchasescollection.data.source.interfaces.ProductDataSource;
import purchases.application.purchasescollection.utilities.executor.ApplicationExecutor;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductLocaleDataSource implements ProductDataSource {

    private static volatile ProductLocaleDataSource INSTANCE;

    private ProductDao productDao;
    private ApplicationExecutor applicationExecutor;

    public ProductLocaleDataSource(@NonNull ProductDao productDao,
                                   @NonNull ApplicationExecutor applicationExecutor) {
        this.productDao = productDao;
        this.applicationExecutor = applicationExecutor;
    }

    public static ProductLocaleDataSource getInstance(@NonNull ProductDao productDao,
                                                      @NonNull ApplicationExecutor applicationExecutor){

        if (INSTANCE == null) {
            synchronized (ProductLocaleDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new ProductLocaleDataSource(productDao, applicationExecutor);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getProducts(@NonNull LoadProductsCallback callback) {

        Runnable runnable = () -> {
            final List<Product> products = productDao.getProducts();

            applicationExecutor.getMainThread().execute(() -> {
                if (products.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable();
                } else {
                    callback.onProductsLoad(products);
                }
            });
        };

        applicationExecutor.getDiskIO().execute(runnable);
    }

    @Override
    public void getProduct(@NonNull String id, @NonNull LoadProductCallback callback) {

        Runnable runnable = () -> {
            final Product product = productDao.getProductById(id);

            applicationExecutor.getMainThread().execute(() -> {
                if (product.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable();
                } else {
                    callback.onProductLoad(product);
                }
            });
        };

        applicationExecutor.getDiskIO().execute(runnable);
    }

    @Override
    public void saveProduct(@NonNull Product product) {

        checkNotNull(product);
        Runnable runnable = () -> productDao.addProduct(product);
        applicationExecutor.getDiskIO().execute(runnable);
    }

    @Override
    public void notBuyProduct(@NonNull Product product) {
        checkNotNull(product);

        Runnable runnable = () -> productDao.updateBuyProduct(product.getId(), false);
        applicationExecutor.getDiskIO().execute(runnable);
    }

    @Override
    public void buyProduct(@NonNull String id) {

        Runnable runnable = () -> productDao.updateBuyProduct(id, true);
        applicationExecutor.getDiskIO().execute(runnable);
    }

    @Override
    public void buyProduct(@NonNull Product product) {

        Runnable runnable = () -> productDao.updateBuyProduct(product.getId(), true);
        applicationExecutor.getDiskIO().execute(runnable);
    }

    @Override
    public void deleteAllProducts() {

        Runnable runnable = () -> productDao.deleteProducts();
        applicationExecutor.getDiskIO().execute(runnable);
    }

    @Override
    public void deleteProduct(@NonNull String id) {

        Runnable runnable = () -> productDao.deleteProductById(id);
        applicationExecutor.getDiskIO().execute(runnable);
    }
}
