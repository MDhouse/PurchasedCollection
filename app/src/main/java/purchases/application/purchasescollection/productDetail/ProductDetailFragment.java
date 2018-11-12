package purchases.application.purchasescollection.productDetail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.addEditProduct.AddEditProductActivity;
import purchases.application.purchasescollection.addEditProduct.AddEditProductFragment;

import static com.google.common.base.Preconditions.checkNotNull;
import static purchases.application.purchasescollection.R.id.menu_delete;


public class ProductDetailFragment extends Fragment implements ProductDetailContract.View {

    @NonNull
    private static final String ARGUMENT_PRODUCT_ID = "PRODUCT_ID";

    @NonNull
    private static final int REQUEST_EDIT_PRODUCT = 1;

    private ProductDetailContract.Presenter presenter;

    private TextView detailTitle;
    private TextView detailDescription;


    public ProductDetailFragment() {
    }

    public static ProductDetailFragment newInstance(String productId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PRODUCT_ID, productId);
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        productDetailFragment.setArguments(arguments);
        return productDetailFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_edit_product);
        fab.setOnClickListener(v -> presenter.editProduct());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        setHasOptionsMenu(true);

        detailTitle = (TextView) root.findViewById(R.id.product_detail_title);
        detailDescription = (TextView) root.findViewById(R.id.product_detail_description);

        detailTitle.setText("testing");

        return inflater.inflate(R.layout.fragment_product_detail, container, false);
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
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.product_detail_menu, menu);
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
    public void hideTitle() {
        detailTitle.setVisibility(View.GONE);
    }

    @Override
    public void showTitle(@NonNull String title) {
        detailTitle.setText(title);
        detailTitle.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideInformation() {
        detailDescription.setVisibility(View.GONE);
    }

    @Override
    public void showInformation(@NonNull String description) {
        detailDescription.setText(description);
        detailDescription.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBuyProduct() {

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
    public void showMissingProduct() {
        detailTitle.setText("");
        detailDescription.setText("No data");
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }
}
