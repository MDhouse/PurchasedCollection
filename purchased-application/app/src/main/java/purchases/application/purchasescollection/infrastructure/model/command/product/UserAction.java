package purchases.application.purchasescollection.infrastructure.model.command.product;

public class UserAction {

    private String email;
    private  String password;

    public UserAction(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
