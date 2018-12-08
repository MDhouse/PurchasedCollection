package purchases.application.purchasescollection.infrastructure.implement.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import purchases.application.purchasescollection.common.utilities.executor.ApplicationExecutor;
import purchases.application.purchasescollection.infrastructure.contract.ILoadProduct;
import purchases.application.purchasescollection.infrastructure.contract.ILoadProducts;
import purchases.application.purchasescollection.infrastructure.contract.IProductService;
import purchases.application.purchasescollection.infrastructure.model.command.ProductCreate;
import purchases.application.purchasescollection.infrastructure.model.command.ProductDelete;
import purchases.application.purchasescollection.infrastructure.model.command.ProductSearch;
import purchases.application.purchasescollection.infrastructure.model.command.ProductTransaction;
import purchases.application.purchasescollection.infrastructure.model.command.ProductUpdate;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.infrastructure.model.firebase.Product;

import static android.content.ContentValues.TAG;

public class ProductFirebaseService implements IProductService<Product> {

    private static volatile ProductFirebaseService INSTANCE;

    private DatabaseReference database;
    private ApplicationExecutor applicationExecutor;



    public ProductFirebaseService(@NonNull DatabaseReference database, @NonNull ApplicationExecutor applicationExecutor) {
        this.database = database;
        this.applicationExecutor = applicationExecutor;
    }

    public static ProductFirebaseService getInstance(DatabaseReference database, ApplicationExecutor applicationExecutor) {

        if(isNull())
            build(database, applicationExecutor);

        return INSTANCE;
    }

    @Override
    public void getAll(@NonNull ILoadProducts callback) {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<ProductDto> products = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ProductDto product = map(ds.getValue(Product.class));
                    products.add(product);
                }

                if (products.isEmpty()) {
                    callback.notAvailable();
                } else {
                    callback.load(products);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public void get(@NonNull ProductSearch productSearch, @NonNull ILoadProduct callback) {

        database.child(productSearch.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final ProductDto product = map(dataSnapshot.getValue(Product.class));

                if (product.isEmpty()) {
                    callback.notAvailable();
                } else {
                    callback.load(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public void create(@NonNull ProductCreate productCreate) {

        Product product = productCreate.toFirebase();
        create(product);
    }

    @Override
    public void create(@NonNull Product product) {

        database.child(product.getId()).setValue(product);
    }

    @Override
    public void update(@NonNull ProductUpdate productUpdate) {

        database.child(productUpdate.getId()).child("name").setValue(productUpdate.getName());
        database.child(productUpdate.getId()).child("price").setValue(productUpdate.getPrice());
        database.child(productUpdate.getId()).child("amount").setValue(productUpdate.getAmount());
        database.child(productUpdate.getId()).child("buy").setValue(productUpdate.isBuy());
    }

    @Override
    public void transaction(@NonNull ProductTransaction productTransaction) {
        database.child(productTransaction.getId()).child("buy").setValue(productTransaction.isToBuy());
    }

    @Override
    public void delete(@NonNull ProductDelete productDelete) {

        database.child(productDelete.getId()).removeValue();
    }

    @Override
    public void deleteAll() {
    }

    private static boolean isNull() {

        return INSTANCE == null;
    }

    private static void build(DatabaseReference databaseReference, ApplicationExecutor applicationExecutor) {

        synchronized (ProductFirebaseService.class) {
            if(isNull())
                INSTANCE = new ProductFirebaseService(databaseReference, applicationExecutor);
        }
    }

   private ProductDto map(Product product) {

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAmount(),
                product.isBuy()
        );
    }
}
