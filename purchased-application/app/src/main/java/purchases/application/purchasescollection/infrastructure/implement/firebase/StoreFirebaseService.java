package purchases.application.purchasescollection.infrastructure.implement.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import purchases.application.purchasescollection.infrastructure.contract.IStoreService;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadMapInformation;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadStores;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCounter;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreCreate;
import purchases.application.purchasescollection.infrastructure.model.command.store.StoreSearch;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;
import purchases.application.purchasescollection.infrastructure.model.firebase.Store;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.internal.firebase_auth.zzv.checkNotNull;

public class StoreFirebaseService implements IStoreService {

    private static volatile StoreFirebaseService INSTANCE;

    private DatabaseReference database;

    public StoreFirebaseService(@NonNull DatabaseReference database) {
        this.database = database;
    }

    public static StoreFirebaseService getInstance(@NonNull DatabaseReference database){

        if(isNull())
            build(database);

        return INSTANCE;
    }

    @Override
    public void getAll(@NonNull String uuid, @NonNull ILoadStores callback) {

        database
                .orderByChild("uuid")
                .equalTo(uuid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final List<StoreDto> stores = new ArrayList<>();

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                           StoreDto store = map(Objects.requireNonNull(ds.getValue(Store.class)));
                           stores.add(store);
                        }

                        if(stores.isEmpty())
                            callback.notAvailable();
                        else
                            callback.load(stores);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w(TAG, "Failed to read value.", databaseError.toException());
                    }
                });
    }

    @Override
    public void get(@NonNull StoreSearch storeSearch, @NonNull ILoadMapInformation callback) {

        if(storeSearch.isAllStoresUser()){
            database
                    .orderByChild("uuid")
                    .equalTo(storeSearch.getUuid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            final List<StoreDto> stores = new ArrayList<>();

                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                StoreDto store = map(Objects.requireNonNull(ds.getValue(Store.class)));
                                stores.add(store);
                            }

                            if(stores.isEmpty())
                                callback.notAvailable();
                            else
                                callback.load(stores);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w(TAG, "Failed to read value.", databaseError.toException());
                        }
                    });
        } else {
            database.child(storeSearch.getStoreId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            final Store store = dataSnapshot.getValue(Store.class);

                            if (Objects.requireNonNull(store).isEmpty()) {
                                callback.notAvailable();
                            } else {
                                final List<StoreDto> stores = addStore(store);
                                callback.load(stores);
                            }
                        }

                        private List<StoreDto> addStore(Store store) {
                            List<StoreDto> result = new ArrayList<>();
                            result.add(map(store));
                            return result;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w(TAG, "Failed to read value.", databaseError.toException());
                        }
                    });
        }

    }

    @Override
    public void create(@NonNull StoreCreate newStore) {

        Store store = newStore.toFirebase();

        database.child(store.getId()).setValue(store);
    }

    @Override
    public void radiusCounter(@NonNull StoreCounter storeCounter) {

        database.child(storeCounter.getId()).child("radius").setValue(storeCounter.getNewRadius());
    }

    @Override
    public void deleteAll() {

    }

    private static boolean isNull(){

        return INSTANCE == null;
    }

    private static void build(DatabaseReference database){

        synchronized (StoreFirebaseService.class) {
            if(isNull())
                INSTANCE = new StoreFirebaseService(database);
        }
    }

    private StoreDto map(@NonNull Store store) {

        checkNotNull(store);
        return new StoreDto(
                store.getId(),
                store.getName(),
                store.getDescription(),
                store.getLatitude(),
                store.getLongitude(),
                store.getRadius()
        );
    }
}
