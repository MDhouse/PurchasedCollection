package purchases.application.purchasescollection.infrastructure.implement.room;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import purchases.application.purchasescollection.common.utilities.executor.ApplicationExecutor;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadProduct;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadProducts;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.contract.room.ProductDao;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.infrastructure.model.entity.Product;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductUpdate;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductRoomService implements IProductService<Product> {

    private static volatile ProductRoomService INSTANCE;

    private ProductDao productDao;
    private ApplicationExecutor applicationExecutor;


    public ProductRoomService(@NonNull ProductDao productDao, @NonNull ApplicationExecutor applicationExecutor) {
        this.productDao = productDao;
        this.applicationExecutor = applicationExecutor;
    }

    public static ProductRoomService getInstance(@NonNull ProductDao productDao, @NonNull ApplicationExecutor applicationExecutor) {

        if(isNull())
            build(productDao, applicationExecutor);

        return INSTANCE;
    }


    @Override
    public void getAll(@NonNull String uuid, @NonNull ILoadProducts callback) {

    }

    @Override
    public void getAll(@NonNull ILoadProducts callback) {

        run(() -> {
            final List<Product> products = productDao.getAll();

            applicationExecutor.getMainThread().execute(() -> {
                if (products.isEmpty()) {
                    callback.notAvailable();
                } else {
                    callback.load(map(products));
                }
            });
        });
    }

    @Override
    public void get(@NonNull ProductSearch productSearch, @NonNull ILoadProduct callback) {

        run(() -> {
            final Product product =
                    productSearch.getId() == null
                    ? productDao.getByName(productSearch.getName())
                    : productDao.getById(productSearch.getId());

            applicationExecutor.getMainThread().execute(() -> {

                if (product.isEmpty()) {
                    callback.notAvailable();
                } else {
                    callback.load(map(product));
                }
            });
        });

    }

    @Override
    public void create(@NonNull ProductCreate productCreate) {

       create(productCreate.toEntity());
    }

    @Override
    public void create(@NonNull Product product) {

        checkNotNull(product);

        run(() -> productDao.create(product));
    }

    @Override
    public void update(@NonNull ProductUpdate productUpdate) {

        checkNotNull(productUpdate);

        run(() -> productDao.update(
                    productUpdate.getId(),
                    productUpdate.getName(),
                    productUpdate.getAmount(),
                    productUpdate.getPrice(),
                    productUpdate.isBuy()));
    }

    @Override
    public void transaction(@NonNull ProductTransaction productTransaction) {

        checkNotNull(productTransaction);

        run(() -> productDao.update(productTransaction.getId(),productTransaction.isToBuy()));
    }

    @Override
    public void delete(@NonNull ProductDelete productDelete) {

        checkNotNull(productDelete);

        run(() -> productDao.deleteById(productDelete.getId()));
    }

    @Override
    public void deleteAll() {

        run(() -> productDao.deleteAll());
    }

    private static boolean isNull() {

        return INSTANCE == null;
    }

    private static void build(@NonNull ProductDao productDao, @NonNull ApplicationExecutor applicationExecutor) {

        synchronized (ProductRoomService.class) {
            if(isNull())
                INSTANCE = new ProductRoomService(productDao, applicationExecutor);
        }
    }

    private void run(Runnable runnable){

        applicationExecutor.getDiskIO().execute(runnable);
    }

    private ProductDto map(Product product) {

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAmount(),
                product.getBuy()
        );
    }

    private List<ProductDto>  map(Collection<Product> products) {

        return products
                .stream()
                .map(x -> map(x))
                .collect(Collectors.toList());
    }
}
