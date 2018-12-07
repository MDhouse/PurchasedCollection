package purchases.application.purchasescollection.infrastructure.contract;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.infrastructure.model.command.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.command.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.ProductUpdate;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public interface IProductService {

    void getAll(@NonNull LoadProducts callback);

    void get(@NonNull ProductSearch productSearch, @NonNull LoadProduct callback);

    void create(@NonNull ProductCreate productCreate);

    void update(@NonNull ProductUpdate productUpdate);

    void transaction(@NonNull ProductTransaction productTransaction);

    void delete(@NonNull ProductDelete productDelete);

    void deleteAll();

    interface LoadProduct {
        void load(ProductDto product);
        void notAvailable();
    }

    interface LoadProducts {
        void load(List<ProductDto> products);
        void notAvailable();
    }
}
