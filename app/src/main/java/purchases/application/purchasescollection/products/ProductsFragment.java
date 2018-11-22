package purchases.application.purchasescollection.products;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.addEditProduct.AddEditProductActivity;
import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.interfaces.ItemListListener;
import purchases.application.purchasescollection.productDetail.ProductDetailActivity;
import purchases.application.purchasescollection.utilities.preferences.FontSupport;
import purchases.application.purchasescollection.utilities.preferences.ThemeSupport;

import static com.google.common.base.Preconditions.checkNotNull;


public class ProductsFragment extends Fragment implements ProductsContract.View {

    private ProductsContract.Presenter presenter;

    private ProductsAdapter productsAdapter;

    private LinearLayout productNotData;

    private LinearLayout productData;

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

        getActivity().setTheme( new ThemeSupport( getActivity()).getThemeApplication());
        getActivity().getTheme().applyStyle(new FontSupport( getActivity()).getFontStyle().getResId(), true);

        setHasOptionsMenu(true);

        productsAdapter = new ProductsAdapter(new ArrayList<>(0), productItemListListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        ListView products = root.findViewById(R.id.products_list);
        products.setAdapter(productsAdapter);

        productData = root.findViewById(R.id.products);

        productNotData = root.findViewById(R.id.no_products);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_product);
        fab.setImageResource(R.drawable.ic_action_add);
        fab.setOnClickListener(v -> presenter.addNewProduct());

        final SwipeChildRefreshLayout swipeLayout = root.findViewById(R.id.refresh_layout);

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

        productNotData.setVisibility(View.GONE);
        productData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddProduct() {

        Intent intent = new Intent(getContext(), AddEditProductActivity.class);
        startActivityForResult(intent, AddEditProductActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showNoProducts() {

        productsAdapter.resetData();
        productData.setVisibility(View.GONE);
        productNotData.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetailProduct(String productId) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.PRODUCT_ID, productId);
        startActivity(intent);
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
