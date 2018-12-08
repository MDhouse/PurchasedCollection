package purchases.application.purchasescollection.infrastructure.contract;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.infrastructure.model.command.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.command.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.ProductUpdate;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.infrastructure.model.entity.Product;

public interface IProductService {

    void getAll(@NonNull ILoadProducts callback);

    void get(@NonNull ProductSearch productSearch, @NonNull ILoadProduct callback);

    void create(@NonNull ProductCreate productCreate);

    void create(@NonNull Product product);

    void update(@NonNull ProductUpdate productUpdate);

    void transaction(@NonNull ProductTransaction productTransaction);

    void delete(@NonNull ProductDelete productDelete);

    void deleteAll();
}
