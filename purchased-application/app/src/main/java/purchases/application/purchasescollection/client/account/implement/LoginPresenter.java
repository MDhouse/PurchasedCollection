package purchases.application.purchasescollection.client.account.implement;

import android.support.annotation.NonNull;

import purchases.application.purchasescollection.client.account.contract.ILoginPresenter;
import purchases.application.purchasescollection.client.account.contract.ILoginView;
import purchases.application.purchasescollection.infrastructure.contract.IAuthenticationService;
import purchases.application.purchasescollection.infrastructure.contract.ILoadUser;
import purchases.application.purchasescollection.infrastructure.model.command.product.UserAction;
import purchases.application.purchasescollection.infrastructure.model.dto.UserDto;

public class LoginPresenter implements ILoginPresenter {

    @NonNull
    private final ILoginView loginView;

    @NonNull
    private final IAuthenticationService authenticationService;

    @NonNull
    private final boolean isLogout;

    public LoginPresenter(@NonNull ILoginView loginView, @NonNull IAuthenticationService authenticationService, @NonNull boolean isLogout) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.isLogout = isLogout;

        this.loginView.setPresenter(this);
    }

    @Override
    public void start() {

        if(isLogout){
            logout();
        }

        authenticationService.getCurrentUser(new ILoadUser() {
            @Override
            public void load(UserDto user) {
                loginView.showProduct();
            }

            @Override
            public void notAvailable() {
                loginView.showLoginForm();
            }
        });
    }

    @Override
    public void singIn(UserAction user) {

        authenticationService.signIn(user, new ILoadUser() {
            @Override
            public void load(UserDto user) {
                loginView.showProduct();
            }

            @Override
            public void notAvailable() {
                loginView.showErrorToast("Authentication failed!");
            }
        });
    }

    @Override
    public void createAccount(UserAction user) {

        authenticationService.createAccount(user, new ILoadUser() {
            @Override
            public void load(UserDto user) {
                loginView.showProduct();
            }

            @Override
            public void notAvailable() {
                loginView.showErrorToast("Authentication failed!");
            }
        });
    }

    private void logout() {

        authenticationService.singOut();
    }
}
