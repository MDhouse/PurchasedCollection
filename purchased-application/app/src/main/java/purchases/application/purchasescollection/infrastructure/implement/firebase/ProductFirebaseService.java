package purchases.application.purchasescollection.infrastructure.implement.firebase;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.model.command.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.command.ProductUpdate;

public class ProductFirebaseService implements IProductService {


    @Override
    public void getAll(@NonNull LoadProducts callback) {

    }

    @Override
    public void get(@NonNull ProductSearch productSearch, @NonNull LoadProduct callback) {

    }

    @Override
    public void create(@NonNull ProductCreate productCreate) {

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
