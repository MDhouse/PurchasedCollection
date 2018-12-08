package purchases.application.purchasescollection.client.product.implement.view;

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
import purchases.application.purchasescollection.client.product.activity.ProductFormActivity;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductDetailPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductDetailView;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductDetailView extends Fragment implements IProductDetailView {

    @NonNull
    private static final String ARGUMENT_PRODUCT_ID = "PRODUCT_ID";
    @NonNull
    private static final int REQUEST_EDIT_PRODUCT = 1;

    @NonNull
    private IProductDetailPresenter productDetailPresenter;

    private LinearLayout detailLoad, detailNotData, detailData;
    private TextView productName, productPrice, productAmount, productIsBuy;
    private MenuItem optionForProduct;

    private boolean statusPurchases;

    public ProductDetailView() {
    }

    public static ProductDetailView newInstance(String productId){
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PRODUCT_ID, productId);
        ProductDetailView productDetailView = new ProductDetailView();
        productDetailView.setArguments(arguments);
        return productDetailView;
    }

    @Override
    public void onResume() {
        super.onResume();
        productDetailPresenter.start();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_product_detail, container, false);

        final FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_product);
        fab.setOnClickListener(v -> productDetailPresenter.editProduct());

        detailLoad = root.findViewById(R.id.execute_action);
        detailData = root.findViewById(R.id.product_detail_data);
        detailNotData = root.findViewById(R.id.product_detail_no_date);

        productName = root.findViewById(R.id.product_detail_name);
        productPrice = root.findViewById(R.id.product_detail_price);
        productAmount = root.findViewById(R.id.product_detail_amount);
        productIsBuy = root.findViewById(R.id.product_detail_status);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.product_detail_menu, menu);
        optionForProduct = menu.findItem(R.id.menu_buy);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_buy:
                productDetailPresenter.buyProduct();
                return true;
            case R.id.menu_delete:
                productDetailPresenter.deleteProduct();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        setEnableMenu();
    }

    @Override
    public void setDetailProduct(ProductDto product) {

        productName.setText(product.getName());
        productPrice.setText(product.getPriceFormat());
        productAmount.setText(product.getAmountFormat());
        productIsBuy.setText(product.getInformation());

        setStatusPurchases(product.isBuy());
    }

    @Override
    public void showDetailProduct() {

        this.detailLoad.setVisibility(View.GONE);
        this.detailNotData.setVisibility(View.GONE);
        this.detailData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMissingProduct() {

        this.detailLoad.setVisibility(View.GONE);
        this.detailData.setVisibility(View.GONE);
        this.detailNotData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadProduct() {

        this.detailNotData.setVisibility(View.GONE);
        this.detailData.setVisibility(View.GONE);
        this.detailLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEditedProduct(String productId) {
        Intent intent = new Intent(getContext(), ProductFormActivity.class);
        intent.putExtra(ProductFormView.ARGUMENT_EDIT_PRODUCT_ID, productId);
        startActivityForResult(intent, REQUEST_EDIT_PRODUCT);
    }

    @Override
    public void showDeletedProduct() {
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(IProductDetailPresenter presenter) {
        productDetailPresenter = checkNotNull(presenter);
    }

    private void setStatusPurchases(boolean statusPurchases) {
        this.statusPurchases = !statusPurchases;
        setEnableMenu();
    }

    private void setEnableMenu(){

        if(optionForProduct != null)
            optionForProduct
                    .setEnabled(statusPurchases)
                    .setVisible(statusPurchases);
    }
}
