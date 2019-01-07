package purchases.application.purchasescollection.common.utilities.inject;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import purchases.application.purchasescollection.infrastructure.implement.firebase.AuthenticationFirebaseService;
import purchases.application.purchasescollection.infrastructure.implement.firebase.ProductFirebaseService;
import purchases.application.purchasescollection.infrastructure.implement.firebase.StoreFirebaseService;
import purchases.application.purchasescollection.infrastructure.implement.room.ProductDatabase;
import purchases.application.purchasescollection.infrastructure.implement.room.ProductRoomService;
import purchases.application.purchasescollection.common.utilities.executor.ApplicationExecutor;

import static com.google.common.base.Preconditions.checkNotNull;

public class Injector {

    public static ProductRoomService provideRoomServices(@NonNull Context context) {

        checkNotNull(context);

        ProductDatabase productDatabase = ProductDatabase.getInstance(context);

        return ProductRoomService.getInstance(productDatabase.productDao(), new ApplicationExecutor());
    }

    public static ProductFirebaseService provideProduct() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productReference = database.getReference("product");

        return ProductFirebaseService.getInstance(productReference);
    }

    public static AuthenticationFirebaseService provideAuth() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("user");

        return AuthenticationFirebaseService.getInstance(FirebaseAuth.getInstance(), userReference);
    }

    public static StoreFirebaseService provideStore(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference storeReference = database.getReference("store");

        return StoreFirebaseService.getInstance(storeReference);
    }
}
