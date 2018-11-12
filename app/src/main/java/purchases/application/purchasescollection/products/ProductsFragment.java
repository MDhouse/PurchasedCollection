package purchases.application.purchasescollection.products;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.addEditProduct.AddEditProductActivity;
import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.interfaces.ItemListListener;
import purchases.application.purchasescollection.productDetail.ProductDetailActivity;
import purchases.application.purchasescollection.utilities.ChangeTemplate;

import static android.graphics.Color.BLUE;
import static com.google.common.base.Preconditions.checkNotNull;


public class ProductsFragment extends Fragment implements ProductsContract.View {

    private ProductsContract.Presenter presenter;
    private ProductsAdapter productsAdapter;
    private View noProductView;
    private TextView noProductMainView;
    private TextView noProductAddView;
    private LinearLayout productView;

    public ProductsFragment() {
    }

    public static ProductsFragment newInstance() { return new ProductsFragment(); }

    ItemListListener<Product> productItemListListener = new ItemListListener<Product>() {
        @Override
        public void onClick(Product itemList) {
            presenter.openProductDetail(itemList);
        }

        @Override
        public void onCompleteClick(Product itemList) {
            presenter.buyProduct(itemList);
        }

        @Override
        public void onUnCompleteClick(Product itemList) {
            presenter.notBuyProduct(itemList);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productsAdapter = new ProductsAdapter(new ArrayList<Product>(0), productItemListListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        ListView products = root.findViewById(R.id.products_list);
        products.setAdapter(productsAdapter);

        productView = root.findViewById(R.id.products);

        noProductView = root.findViewById(R.id.no_products);
        noProductMainView = root.findViewById(R.id.no_products_main);
        noProductAddView = root.findViewById(R.id.no_products_add);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_product);
        fab.setImageResource(R.drawable.ic_action_add);
        fab.setOnClickListener(v -> presenter.addNewProduct());

        final SwipeChildRefreshLayout swipeLayout = root.findViewById(R.id.refresh_layout);

        swipeLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.white),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        swipeLayout.setScrollUpChild(products);

        swipeLayout.setOnRefreshListener(() -> presenter.loadProducts(false));

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode);
    }

    @Override
    public void showProducts(List<Product> products) {

        productsAdapter.replaceData(products);

        productView.setVisibility(View.VISIBLE);
        noProductView.setVisibility(View.GONE);
    }

    @Override
    public void showAddProduct() {

        Intent intent = new Intent(getContext(), AddEditProductActivity.class);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showNoProducts() {

        showNoProductsView(getResources().getString(R.string.empty_product_message), false);
    }

    @Override
    public void showDetailProduct(String productId) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
    }


    private void showNoProductsView(String text, boolean showAddView) {

        productView.setVisibility(View.GONE);
        noProductView.setVisibility(View.VISIBLE);

        noProductMainView.setText(text);
        noProductAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }
}
