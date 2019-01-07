package purchases.application.purchasescollection.infrastructure.model.command.user;

import android.support.annotation.Nullable;

public class UserAction {

    private String email;
    private String password;
    private String userName;

    public UserAction(String email, String password, @Nullable String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
