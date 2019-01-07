package purchases.application.purchasescollection.infrastructure.implement.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.atomic.AtomicBoolean;

import purchases.application.purchasescollection.infrastructure.contract.IAuthenticationService;
import purchases.application.purchasescollection.infrastructure.contract.callback.ILoadUser;
import purchases.application.purchasescollection.infrastructure.model.command.user.UserAction;
import purchases.application.purchasescollection.infrastructure.model.dto.UserDto;
import purchases.application.purchasescollection.infrastructure.model.firebase.User;

public class AuthenticationFirebaseService implements IAuthenticationService {

    private static volatile AuthenticationFirebaseService INSTANCE;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    public AuthenticationFirebaseService(@NonNull FirebaseAuth firebaseAuth, @NonNull DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.database = databaseReference;
    }

    public static AuthenticationFirebaseService getInstance(@NonNull FirebaseAuth firebaseAuth, @NonNull DatabaseReference databaseReference) {

        if(isNull())
            build(firebaseAuth, databaseReference);

        return INSTANCE;
    }

    @Override
    public void getCurrentUser(@NonNull ILoadUser callback) {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(isNull(firebaseUser))
            callback.notAvailable();
        else
            callback.load(UserDto.map(firebaseUser));
    }

    @Override
    public void createAccount(UserAction user,  @NonNull ILoadUser callback) {

        AtomicBoolean doExecute = new AtomicBoolean(false);

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnSuccessListener( task -> {

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(user.getUserName())
                            .build();

                    firebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                            .addOnCompleteListener(doUpdate -> {

                                if(doUpdate.isSuccessful()) {
                                    UserDto newUser = UserDto.map(firebaseAuth.getCurrentUser());
                                    save(newUser);
                                    callback.load(newUser);
                                } else {
                                    callback.notAvailable();
                                }
                            });
                        }
                ).addOnFailureListener(errors ->
                    callback.notAvailable()
                );
    }

    @Override
    public void signIn(UserAction user, @NonNull ILoadUser callback) {

        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        UserDto newUser = UserDto.map(firebaseAuth.getCurrentUser());
                        callback.load(newUser);
                    } else {
                        callback.notAvailable();
                    }
                });
    }

    @Override
    public void singOut() {

        firebaseAuth.signOut();
    }

    private static boolean isNull(){

        return INSTANCE == null;
    }

    private static void build(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {

        synchronized (AuthenticationFirebaseService.class) {
            if(isNull())
                INSTANCE = new AuthenticationFirebaseService(firebaseAuth, databaseReference);
        }
    }

    private boolean isNull(FirebaseUser firebaseUser){

        return firebaseUser == null;
    }

    private void save(UserDto userDto) {


        User user = new User(userDto.getName(), userDto.getEmail());
        database.child(userDto.getId()).setValue(user);
    }
}
