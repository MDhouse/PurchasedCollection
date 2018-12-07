package purchases.application.purchasescollection.utilities.inject;

import android.content.Context;
import android.support.annotation.NonNull;

import purchases.application.purchasescollection.infrastructure.implement.room.ProductDatabase;
import purchases.application.purchasescollection.infrastructure.implement.room.ProductRoomService;
import purchases.application.purchasescollection.utilities.executor.ApplicationExecutor;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injector {

//    public static ProductLocaleDataSource provideTasksRepository(@NonNull Context context) {
//
//        checkNotNull(context);
//        PurchasesDatabase database = PurchasesDatabase.getInstance(context);
//
//        return ProductLocaleDataSource.getInstance(database.productDao(), new ApplicationExecutor());
//    }

    public static ProductRoomService provideRoomServices(@NonNull Context context) {

        checkNotNull(context);

        ProductDatabase productDatabase = ProductDatabase.getInstance(context);

        return ProductRoomService.getInstance(productDatabase.productDao(), new ApplicationExecutor());
    }

    public static void provideFirebaseService(@NonNull Context context) {

        checkNotNull(context);

    }
}
