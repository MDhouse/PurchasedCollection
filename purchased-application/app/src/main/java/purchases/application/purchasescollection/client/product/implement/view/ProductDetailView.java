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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.activity.ProductFormActivity;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductDetailPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductDetailView;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;
import purchases.application.purchasescollection.common.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.common.utilities.preferences.ThemeSupport;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductDetailView extends Fragment implements IProductDetailView {

    private IProductDetailPresenter productDetailPresenter;

    private Unbinder unbinder;

    private MenuItem optionForProduct;

    private boolean statusPurchases;

    @BindView(R.id.execute_action)
    LinearLayout detailLoad;

    @BindView(R.id.product_detail_no_date)
    LinearLayout detailNotData;

    @BindView(R.id.product_detail_data)
    LinearLayout detailData;

    @BindView(R.id.product_detail_name)
    TextView productName;

    @BindView(R.id.product_detail_price)
    TextView productPrice;

    @BindView(R.id.product_detail_amount)
    TextView productAmount;

    @BindView(R.id.product_detail_status)
    TextView productIsBuy;

    public ProductDetailView() {
    }

    public static ProductDetailView newInstance(String productId){
        Bundle arguments = new Bundle();
        arguments.putString("PRODUCT_ID", productId);
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

        unbinder = ButterKnife.bind(this, root);

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        setVisibilityDetailLoad(View.GONE);
        setVisibilityDetailNotData(View.GONE);

        setVisibilityDetailData(View.VISIBLE);
    }

    @Override
    public void showMissingProduct() {

        setVisibilityDetailLoad(View.GONE);
        setVisibilityDetailData(View.GONE);

        setVisibilityDetailNotData(View.VISIBLE);
    }

    @Override
    public void showLoadProduct() {

        setVisibilityDetailNotData(View.GONE);
        setVisibilityDetailData(View.GONE);

        setVisibilityDetailLoad(View.VISIBLE);
    }

    @Override
    public void showEditedProduct(String productId) {

        Intent intent = new Intent(getContext(), ProductFormActivity.class);
        intent.putExtra("EDIT_PRODUCT_ID", productId);
        startActivityForResult(intent, 1);
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

    public void setVisibilityDetailLoad(int visible) {

        if(detailLoad != null)
            detailLoad.setVisibility(visible);
    }

    public void setVisibilityDetailNotData(int visible) {

        if(detailNotData != null)
            detailNotData.setVisibility(visible);
    }

    public void setVisibilityDetailData(int visible) {

        if(detailData != null)
            detailData.setVisibility(visible);
    }
}
