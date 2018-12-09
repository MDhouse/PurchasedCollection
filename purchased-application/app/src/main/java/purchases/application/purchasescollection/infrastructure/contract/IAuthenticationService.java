package purchases.application.purchasescollection.infrastructure.contract;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.infrastructure.model.command.product.UserAction;

public interface IAuthenticationService {

    void getCurrentUser(@NonNull ILoadUser callback);

    void createAccount(UserAction user, @NonNull ILoadUser callback);

    void signIn(UserAction user, @NonNull ILoadUser callback);

    void singOut();
}
