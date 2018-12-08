package purchases.application.purchasescollection.client.product.implement.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductFormPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductFormView;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class ProductFormView extends Fragment implements IProductFormView {

    public static final String ARGUMENT_EDIT_PRODUCT_ID = "EDIT_PRODUCT_ID";

    private IProductFormPresenter productFormPresenter;

    private FloatingActionButton floatingActionButton;
    private TextView formEmpty;
    private EditText nameEdit, priceEdit, amountEdit;
    private Switch buySwitch;

    public ProductFormView() {
    }

    @NonNull
    public static ProductFormView newInstance() {return new ProductFormView();}

    @Override
    public void onResume() {
        super.onResume();
        productFormPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);

        floatingActionButton = getActivity().findViewById(R.id.fab_edit_product);
        floatingActionButton.setImageResource(R.drawable.ic_done);;
        floatingActionButton.setOnClickListener(v -> formValidate());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        formEmpty = root.findViewById(R.id.product_form_empty);

        nameEdit = root.findViewById(R.id.product_name);
        priceEdit = root.findViewById(R.id.product_price);
        amountEdit = root.findViewById(R.id.product_amount);
        buySwitch = root.findViewById(R.id.product_buy);


        errorDisplay(false);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void showEmptyProductError() {

        errorDisplay(true);
    }

    @Override
    public void showProductList() {

        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void showSwitch() {
        buySwitch.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSwitch() {
        buySwitch.setVisibility(View.GONE);
    }

    @Override
    public void setProduct(ProductDto product) {

        nameEdit.setText(product.getName());
        amountEdit.setText(String.valueOf(product.getAmount()));
        priceEdit.setText(String.valueOf(product.getPrice()));
        buySwitch.setChecked(product.isBuy());
    }

    @Override
    public void toIntent(String idProduct, String contentProduct) {
        Intent toSend = new Intent();

        toSend.setAction("purchases.application.purchasescollection.CREATED_PRODUCT");
        toSend.putExtra("PRODUCT_ID'S", idProduct);
        toSend.putExtra("PRODUCT_CONTENT'S", contentProduct);

        getActivity().sendBroadcast(toSend, "application.purchases.purchasedbroadcast.HANDLE_INTENT");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(IProductFormPresenter presenter) {

        productFormPresenter = presenter;
    }

    private void errorDisplay(boolean visibility) {
        formEmpty.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void formValidate() {

        if(validNewProduct() || this.validEditProduct()){
            productFormPresenter.saveProduct(getNameEdit(), getPriceEdit(), getAmountEdit(), getBuySwitch());
            return;
        }

        errorDisplay(true);
    }

    private boolean validNewProduct(){

        return  productFormPresenter.isNewProduct() && this.checkFields();
    }

    private boolean validEditProduct() {

        return (!this.productFormPresenter.isNewProduct() && this.productFormPresenter.isVerify(getNameEdit(), getPriceEdit(), getAmountEdit(), getBuySwitch()));
    }

    private boolean checkFields() {

        return (this.valid(nameEdit) && this.valid(priceEdit) && this.valid(amountEdit));
    }

    private boolean valid(@NonNull EditText editText) {

        return editText.getText().toString().trim().length() > 0;
    }

    public String getNameEdit() {
        return nameEdit.getText().toString();
    }

    public double getPriceEdit() {
        return Double.valueOf(priceEdit.getText().toString());
    }

    public int getAmountEdit() {
        return Integer.valueOf(amountEdit.getText().toString());
    }

    public boolean getBuySwitch() {
        return buySwitch.isChecked();
    }
}
