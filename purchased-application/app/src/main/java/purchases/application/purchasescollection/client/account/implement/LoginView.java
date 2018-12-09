package purchases.application.purchasescollection.client.account.implement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.account.contract.ILoginPresenter;
import purchases.application.purchasescollection.client.account.contract.ILoginView;
import purchases.application.purchasescollection.client.product.activity.ProductActivity;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;
import purchases.application.purchasescollection.infrastructure.model.command.product.UserAction;
import purchases.application.purchasescollection.infrastructure.model.firebase.User;

public class LoginView extends Fragment implements ILoginView {

    @NonNull
    private ILoginPresenter loginPresenter;

    private LinearLayout loginForm, createForm;
    private Button singInConfirm, createConfirm, createCancel, createAccount;
    private TextView titleFormView;

    private EditText createFieldEmail, createFieldPassword, loginFieldEmail, loginFieldPassword;

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

        loginForm = root.findViewById(R.id.login_form);
        createForm = root.findViewById(R.id.create_account_form);

        singInConfirm = root.findViewById(R.id.confirm_form_login);
        createConfirm = root.findViewById(R.id.confirm_form_create);

        createAccount = root.findViewById(R.id.create_account_form_view);
        createCancel = root.findViewById(R.id.cancel_form);

        createFieldEmail = root.findViewById(R.id.account_email);
        createFieldPassword = root.findViewById(R.id.account_password);

        loginFieldEmail = root.findViewById(R.id.login_email);
        loginFieldPassword = root.findViewById(R.id.login_password);

        titleFormView = root.findViewById(R.id.title_form);

        setListenersButton();

        return root;
    }

    @Override
    public void showLoginForm() {

        createForm.setVisibility(View.GONE);
        loginForm.setVisibility(View.VISIBLE);
        titleFormView.setText("Sing In Application");
    }

    @Override
    public void showCreateForm() {

        loginForm.setVisibility(View.GONE);
        createForm.setVisibility(View.VISIBLE);
        titleFormView.setText("Create Account");
    }

    @Override
    public void showProduct() {

        startActivity(new Intent(getActivity(), ProductActivity.class));
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

    private void setListenersButton() {


        singInConfirm.setOnClickListener(v -> validFormSingIn());

        createConfirm.setOnClickListener(v -> validFormCreateAccount());
        createAccount.setOnClickListener(v -> showCreateForm());
        createCancel.setOnClickListener(v -> showLoginForm());

    }

    private void validFormCreateAccount() {

        String email = createFieldEmail.getText().toString();
        String password = createFieldPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            showErrorToast("Enter email address!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showErrorToast("Enter password!");
            return;
        }

        if (password.length() < 6) {
            showErrorToast("Password too short, enter minimum 6 characters!");
            return;
        }

        loginPresenter.createAccount(new UserAction(email, password));
    }

    private void validFormSingIn() {

        String email = loginFieldEmail.getText().toString();
        String password = loginFieldPassword.getText().toString();


        if (TextUtils.isEmpty(email)) {
            showErrorToast("Enter email address!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showErrorToast("Enter password!");
            return;
        }

        loginPresenter.singIn(new UserAction(email, password));
    }
}
