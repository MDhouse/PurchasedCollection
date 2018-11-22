package purchases.application.purchasescollection.productDetail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.addEditProduct.AddEditProductActivity;
import purchases.application.purchasescollection.addEditProduct.AddEditProductFragment;
import purchases.application.purchasescollection.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.utilities.preferences.ThemeSupport;

import static com.google.common.base.Preconditions.checkNotNull;


public class ProductDetailFragment extends Fragment implements ProductDetailContract.View {

    @NonNull
    private static final String ARGUMENT_PRODUCT_ID = "PRODUCT_ID";

    @NonNull
    private static final int REQUEST_EDIT_PRODUCT = 1;

    private ProductDetailContract.Presenter presenter;

    private LinearLayout detailLoad;
    private LinearLayout detailNoData;
    private LinearLayout detailData;

    private TextView productName;
    private TextView productPrice;
    private TextView productAmount;
    private TextView productIsBuy;

    private boolean statusPurchases;
    private MenuItem optionBuy;

    public static ProductDetailFragment newInstance(String productId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PRODUCT_ID, productId);
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        productDetailFragment.setArguments(arguments);
        return productDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        setHasOptionsMenu(true);

        detailLoad = root.findViewById(R.id.execute_action);
        detailData = root.findViewById(R.id.product_detail_data);
        detailNoData = root.findViewById(R.id.product_detail_no_date);

        productName = root.findViewById(R.id.product_detail_name);
        productPrice = root.findViewById(R.id.product_detail_price);
        productAmount = root.findViewById(R.id.product_detail_amount);
        productIsBuy = root.findViewById(R.id.product_detail_status);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_product);
        fab.setOnClickListener(v -> presenter.editProduct());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.product_detail_menu, menu);

        this.optionBuy = menu.findItem(R.id.menu_buy);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_delete:
                presenter.deleteProduct();
                return true;
            case R.id.menu_buy:
                presenter.buyProduct();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        super.onPrepareOptionsMenu(menu);

        this.setBuyOption();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_EDIT_PRODUCT) {

            if(resultCode == Activity.RESULT_OK) {
                getActivity().finish();
            }
        }
    }

    @Override
    public void showEditedProduct(String productId) {

        Intent intent = new Intent(getContext(), AddEditProductActivity.class);
        intent.putExtra(AddEditProductFragment.ARGUMENT_EDIT_PRODUCT_ID, productId);
        startActivityForResult(intent, REQUEST_EDIT_PRODUCT);
    }

    @Override
    public void showTaskDeleted() {
        getActivity().finish();
    }

    @Override
    public void setName(String name) {

        this.productName.setText(name);
    }

    @Override
    public void setPrice(double price) {

        this.productPrice.setText("$ " + String.valueOf(price));
    }

    @Override
    public void setAmount(int amount) {

        this.productAmount.setText(String.valueOf(amount));
    }

    @Override
    public void setBuy(String status) {

        this.productIsBuy.setText(status);
    }

    @Override
    public void setOptionBuy(boolean status) {

        this.statusPurchases = !status;
        this.setBuyOption();
    }

    @Override
    public void showDetailProduct() {

        this.detailLoad.setVisibility(View.GONE);
        this.detailNoData.setVisibility(View.GONE);

        this.detailData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMissingProduct() {

        this.detailLoad.setVisibility(View.GONE);
        this.detailData.setVisibility(View.GONE);

        this.detailNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadProduct() {

        this.detailNoData.setVisibility(View.GONE);
        this.detailData.setVisibility(View.GONE);

        this.detailLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }


    private void setBuyOption() {

        if(this.optionBuy != null) {
            this.optionBuy.setEnabled(this.statusPurchases);
            this.optionBuy.setVisible(this.statusPurchases);
        }
    }
}
