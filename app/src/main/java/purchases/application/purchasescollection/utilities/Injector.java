package purchases.application.purchasescollection.utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import purchases.application.purchasescollection.data.source.configuration.PurchasesDatabase;
import purchases.application.purchasescollection.data.source.implement.ProductLocaleDataSource;
import purchases.application.purchasescollection.utilities.executor.ApplicationExecutor;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injector {

    public static ProductLocaleDataSource provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        PurchasesDatabase database = PurchasesDatabase.getInstance(context);

        return ProductLocaleDataSource.getInstance(database.productDao(), new ApplicationExecutor());
    }
}
