package purchases.application.purchasescollection.client.account.implement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.account.contract.ILoginPresenter;
import purchases.application.purchasescollection.client.account.contract.ILoginView;
import purchases.application.purchasescollection.client.product.activity.ProductActivity;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;
import purchases.application.purchasescollection.infrastructure.model.command.product.UserAction;
import purchases.application.purchasescollection.infrastructure.model.firebase.Product;

public class LoginView extends Fragment implements ILoginView {

    @NonNull
    private ILoginPresenter loginPresenter;

    @BindView(R.id.title_account_form)
    TextView titleAccountForm;


    @BindView(R.id.create_account_form)
    LinearLayout createAccountForm;

    @BindView(R.id.input_create_account_name)
    EditText createNameField;

    @BindView(R.id.input_create_account_email)
    EditText createEmailField;

    @BindView(R.id.input_create_account_password)
    EditText createPasswordField;


    @BindView(R.id.login_form_account)
    LinearLayout loginAccountForm;

    @BindView(R.id.input_login_account_email)
    EditText loginEmailField;

    @BindView(R.id.input_login_account_password)
    EditText loginPasswordField;

    private Unbinder unbinder;

    public LoginView() {
    }

    public static LoginView newInstance() { return new LoginView();}


    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    @OnClick(R.id.link_login)
    public void showLoginForm() {

        createAccountForm.setVisibility(View.GONE);
        loginAccountForm.setVisibility(View.VISIBLE);
        titleAccountForm.setText("Sing In");
    }

    @Override
    @OnClick(R.id.create_account_link)
    public void showCreateForm() {

        loginAccountForm.setVisibility(View.GONE);
        createAccountForm.setVisibility(View.VISIBLE);
        titleAccountForm.setText("Create Account");
    }

    @Override
    public void showProductActivity(String userName) {

        final Intent productIntent = new Intent(getActivity(), ProductActivity.class);
        productIntent.putExtra("USER_NAME", userName);

        startActivity(productIntent);
    }

    @Override
    public void showErrorToast(String message) {

        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(ILoginPresenter presenter) {

        this.loginPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



    @OnClick(R.id.confirm_create_account)
    protected void validFormCreateAccount() {


        String name = createNameField.getText().toString();
        String email =  createEmailField.getText().toString();
        String password = createPasswordField.getText().toString();

        if(TextUtils.isEmpty(name)){
            showErrorToast("Enter a name user");
            return;
        }

        if(name.length() < 3) {
            showErrorToast("Name too short, enter minimum 3 characters!");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            showErrorToast("Enter a email address!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showErrorToast("Enter a password!");
            return;
        }

        if (password.length() < 6) {
            showErrorToast("Password too short, enter minimum 6 characters!");
            return;
        }

        loginPresenter.createAccount(new UserAction(email, password, name));
    }

    @OnClick(R.id.confirm_login_account)
    protected void validFormLogIn() {

        String email = loginEmailField.getText().toString();
        String password = loginPasswordField.getText().toString();


        if (TextUtils.isEmpty(email)) {
            loginEmailField.setError("enter a email address");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 3) {
            showErrorToast("enter a password");
            return;
        }

        loginPresenter.singIn(new UserAction(email, password, null));
    }
}
