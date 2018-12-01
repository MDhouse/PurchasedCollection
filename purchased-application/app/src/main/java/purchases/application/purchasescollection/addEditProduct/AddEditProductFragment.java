package purchases.application.purchasescollection.addEditProduct;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.utilities.preferences.ThemeSupport;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditProductFragment extends Fragment implements AddEditProductContract.View  {

    public static final String ARGUMENT_EDIT_PRODUCT_ID = "EDIT_PRODUCT_ID";

    private AddEditProductContract.Presenter presenter;


    private FloatingActionButton fab;

    private TextView formEmpty;

    private EditText name, price, amount;
    private Switch buy;

    @NonNull
    public static AddEditProductFragment newInstance() {
        return new AddEditProductFragment();
    }

    public AddEditProductFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);


        this.fab = getActivity().findViewById(R.id.fab_product_edit_done);
        this.fab.setImageResource(R.drawable.ic_done);
        this.fab.setOnClickListener(v -> formValidate());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        formEmpty = root.findViewById(R.id.product_form_empty);

        name = root.findViewById(R.id.product_name);
        price = root.findViewById(R.id.product_price);
        amount = root.findViewById(R.id.product_amount);
        buy = root.findViewById(R.id.product_buy);


        this.errorDisplay(false);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void showEmptyProductError() {
        this.errorDisplay(true);
    }

    @Override
    public void showProductList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showSwitch() {
        buy.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSwitch() {
        buy.setVisibility(View.GONE);
    }

    @Override
    public void setName(String name) {

        this.name.setText(name);
    }

    @Override
    public void setPrice(double price) {

        this.price.setText(String.valueOf(price));
    }

    @Override
    public void setAmount(int amount) {

        this.amount.setText(String.valueOf(amount));
    }

    @Override
    public void setBuy(boolean buy) {

        this.buy.setChecked(buy);
    }

    @Override
    public void toIntent(String idProduct, String contentProduct) {

        Intent toSend = new Intent();

        toSend.setAction("CREATED_PRODUCT");
        toSend.putExtra("PRODUCT_ID", idProduct);
        toSend.putExtra("PRODUCT_CONTENT", contentProduct);

        getActivity().sendBroadcast(toSend, "application.purchases.purchasedbroadcast.HANDLE_INTENT");
    }

    @Override
    public boolean isActive() {

        return isAdded();
    }

    @Override
    public void setPresenter(AddEditProductContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @NonNull
    private String getName() {
        return name.getText().toString();
    }

    private double getPrice() {
       return Double.valueOf(price.getText().toString());
    }

    private int getAmount() {
        return Integer.valueOf(amount.getText().toString());
    }

    private boolean getBuy() {
        return buy.isChecked();
    }


    private void formValidate() {

        if(this.validNewProduct()) {
            this.presenter.saveProduct(getName(), getPrice(), getAmount(), getBuy());
            return;
        }

        if(this.validEditProduct()) {
            this.presenter.saveProduct(getName(), getPrice(), getAmount(), getBuy());
            return;
        }

        this.errorDisplay(true);
    }

    private boolean validNewProduct(){

        return this.presenter.isNewProduct() && this.checkFields();
    }

    private boolean validEditProduct() {

        return (!this.presenter.isNewProduct() && this.presenter.isVerify(getName(), getPrice(), getAmount(), getBuy()));
    }

    private boolean checkFields() {

        return (this.valid(name) && this.valid(price) && this.valid(amount));
    }

    private boolean valid(@NonNull EditText editText) {

        return editText.getText().toString().trim().length() > 0;
    }
    
    private void errorDisplay(boolean display){

        this.formEmpty.setVisibility(display ? View.VISIBLE : View.GONE);
    }
}
