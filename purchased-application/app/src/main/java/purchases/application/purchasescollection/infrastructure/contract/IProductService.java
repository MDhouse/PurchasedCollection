package purchases.application.purchasescollection.infrastructure.contract;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.infrastructure.model.command.product.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.product.ProductUpdate;

public interface IProductService<T> {

    void getAll(@NonNull ILoadProducts callback);

    void get(@NonNull ProductSearch productSearch, @NonNull ILoadProduct callback);

    void create(@NonNull ProductCreate productCreate);

    void create(@NonNull T product);

    void update(@NonNull ProductUpdate productUpdate);

    void transaction(@NonNull ProductTransaction productTransaction);

    void delete(@NonNull ProductDelete productDelete);

    void deleteAll();
}
