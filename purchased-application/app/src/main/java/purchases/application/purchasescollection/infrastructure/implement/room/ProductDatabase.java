package purchases.application.purchasescollection.infrastructure.implement.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import purchases.application.purchasescollection.infrastructure.contract.room.ProductDao;
import purchases.application.purchasescollection.infrastructure.model.entity.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    private static ProductDatabase INSTANCE;

    public abstract ProductDao productDao();

    private static final Object lock = new Object();

    public static ProductDatabase getInstance(Context context) {

        synchronized (lock) {
            if(isNull())
                build(context);

            return INSTANCE;
        }
    }

    private static boolean isNull() {

        return INSTANCE == null;
    }

    private static void build(Context context) {

        INSTANCE = Room
                .databaseBuilder(context.getApplicationContext(),ProductDatabase.class, "product.db")
                .build();
    }
}
