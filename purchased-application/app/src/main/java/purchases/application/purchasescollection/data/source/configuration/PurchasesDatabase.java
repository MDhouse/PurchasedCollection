package purchases.application.purchasescollection.data.source.configuration;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import purchases.application.purchasescollection.data.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class PurchasesDatabase extends RoomDatabase {

    private static PurchasesDatabase INSTANCE;

    public abstract ProductDao productDao();

    private static final Object sLock = new Object();

    public static PurchasesDatabase getInstance(Context context){
        synchronized (sLock){
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PurchasesDatabase.class, "product.db")
                        .build();
            }

            return INSTANCE;
        }
    }
}
