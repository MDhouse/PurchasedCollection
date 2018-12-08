package purchases.application.purchasescollection.infrastructure.implement;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.infrastructure.contract.ILoadProduct;
import purchases.application.purchasescollection.infrastructure.contract.ILoadProducts;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.model.command.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.command.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.ProductUpdate;
import purchases.application.purchasescollection.infrastructure.model.entity.Product;

public class ProductRepository implements IProductService<Product> {


    @Override
    public void getAll(@NonNull ILoadProducts callback) {

    }

    @Override
    public void get(@NonNull ProductSearch productSearch, @NonNull ILoadProduct callback) {

    }

    @Override
    public void create(@NonNull ProductCreate productCreate) {

    }

    @Override
    public void create(@NonNull Product product) {

    }

    @Override
    public void update(@NonNull ProductUpdate productUpdate) {

    }

    @Override
    public void transaction(@NonNull ProductTransaction productTransaction) {

    }

    @Override
    public void delete(@NonNull ProductDelete productDelete) {

    }

    @Override
    public void deleteAll() {

    }
}
