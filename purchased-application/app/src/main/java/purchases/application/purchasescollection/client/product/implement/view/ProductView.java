package purchases.application.purchasescollection.client.product.implement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import purchases.application.purchasescollection.client.product.activity.ProductDetailActivity;
import purchases.application.purchasescollection.client.product.activity.ProductFormActivity;
import purchases.application.purchasescollection.common.componenent.SwipeChildRefreshLayout;
import purchases.application.purchasescollection.common.contract.IListListener;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductView;
import purchases.application.purchasescollection.client.product.implement.adapter.ProductAdapter;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public class ProductView extends Fragment implements IProductView {

    private IProductPresenter productPresenter;
    private IListListener<ProductDto> productListListener;

    private ProductAdapter productAdapter;

    private LinearLayout productNotAvailable;

    private LinearLayout productPresent;


    public ProductView() {
    }

    public static ProductView newInstance() {return new ProductView();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        loadAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_product, container, false);

        final ListView productList = root.findViewById(R.id.products_list);
        productList.setAdapter(productAdapter);

        final SwipeChildRefreshLayout swipeChildRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeChildRefreshLayout.setScrollUpChild(productList);
        swipeChildRefreshLayout.setOnRefreshListener(() -> productPresenter.loadProducts());

        final FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.fab_add_product);
        floatingActionButton.setImageResource(R.drawable.ic_action_add);
        floatingActionButton.setOnClickListener(v -> productPresenter.createProduct());

        productPresent = root.findViewById(R.id.products);
        productNotAvailable = root.findViewById(R.id.no_products);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        productPresenter.start();
    }

    @Override
    public void showProduct(List<ProductDto> products) {

        productAdapter.replaceData(products);

        productNotAvailable.setVisibility(View.GONE);
        productPresent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoProducts() {

        productAdapter.resetData();

        productPresent.setVisibility(View.GONE);
        productNotAvailable.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFormProduct() {

        Intent intent = new Intent(getContext(), ProductFormActivity.class);
        startActivityForResult(intent, ProductFormActivity.REQUEST_ADD_TASK);
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
    public void setPresenter(IProductPresenter presenter) {
        this.productPresenter = presenter;
    }

    private void loadAdapter() {

        if(productListListener == null) {
            productListListener = new IListListener<ProductDto>() {
                @Override
                public void click(ProductDto item) {
                    productPresenter.detailProduct(item.getId());
                }

                @Override
                public void completeAction(ProductDto item) {
                    productPresenter.productTransaction(item.getId(), true);
                }

                @Override
                public void unCompleteAction(ProductDto item) {
                    productPresenter.productTransaction(item.getId(), false);
                }
            };
        }

        if(this.productAdapter == null) {
            productAdapter = new ProductAdapter(new ArrayList<>(0), productListListener);
        }
    }
}
