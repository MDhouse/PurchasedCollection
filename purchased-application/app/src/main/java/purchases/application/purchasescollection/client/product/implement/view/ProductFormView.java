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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductFormPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductFormView;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

public class ProductFormView extends Fragment implements IProductFormView {

    private IProductFormPresenter productFormPresenter;
    private Unbinder unbinder;

    @BindView(R.id.product_form_empty)
    TextView formEmpty;

    @BindView(R.id.product_name)
    EditText nameEdit;

    @BindView(R.id.product_price)
    EditText priceEdit;

    @BindView(R.id.product_amount)
    EditText amountEdit;

    @BindView(R.id.product_buy)
    Switch buySwitch;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_add_edit_product, container, false);

        unbinder = ButterKnife.bind(this, root);

        errorDisplay(false);
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        setVisibilityBuySwitch(View.VISIBLE);
    }

    @Override
    public void hideSwitch() {

        setVisibilityBuySwitch(View.GONE);
    }

    @Override
    public void formValidate() {

        if(validNewProduct() || this.validEditProduct()){
            productFormPresenter.saveProduct(getNameEdit(), getPriceEdit(), getAmountEdit(), getBuySwitch());
            return;
        }

        errorDisplay(true);
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

        getActivity().sendBroadcast(toSend, "application.purchases.purchasescollection.HANDLE_INTENT");
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

    private void setVisibilityBuySwitch(int visible){

        if(buySwitch != null)
            buySwitch.setVisibility(visible);
    }

    private String getNameEdit() {
        return nameEdit.getText().toString();
    }

    private double getPriceEdit() {
        return Double.valueOf(priceEdit.getText().toString());
    }

    private int getAmountEdit() {
        return Integer.valueOf(amountEdit.getText().toString());
    }

    private boolean getBuySwitch() {
        return buySwitch.isChecked();
    }
}
