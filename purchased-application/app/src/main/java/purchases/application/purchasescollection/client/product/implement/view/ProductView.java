package purchases.application.purchasescollection.client.product.implement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import purchases.application.purchasescollection.R;
import purchases.application.purchasescollection.client.product.activity.ProductDetailActivity;
import purchases.application.purchasescollection.client.product.activity.ProductFormActivity;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductPresenter;
import purchases.application.purchasescollection.client.product.contract.view.IProductView;
import purchases.application.purchasescollection.client.product.implement.adapter.ProductAdapter;
import purchases.application.purchasescollection.common.componenent.SwipeChildRefreshLayout;
import purchases.application.purchasescollection.common.contract.IListListener;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public class ProductView extends Fragment implements IProductView {

    @NonNull
    private IProductPresenter productPresenter;
    private IListListener<ProductDto> productListListener;

    private ProductAdapter productAdapter;
    private Unbinder unbinder;

    @BindView(R.id.products)
    LinearLayout productPresent;

    @BindView(R.id.no_products)
    LinearLayout productNotAvailable;

    @BindView(R.id.products_list)
    ListView productList;

    @BindView(R.id.refresh_layout)
    SwipeChildRefreshLayout swipeChildRefreshLayout;

    public ProductView() {
    }

    public static ProductView newInstance() {return new ProductView();}

    @Override
    public void onResume() {
        super.onResume();
        productPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_product, container, false);

        unbinder = ButterKnife.bind(this, root);

        getProductList();
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProduct(List<ProductDto> products) {

        productAdapter.replaceData(products);

        setVisibilityNotAvailable(View.GONE);
        setVisibilityProduct(View.VISIBLE);
    }

    @Override
    public void showNoProducts() {

        productAdapter.resetData();

        setVisibilityProduct(View.GONE);
        setVisibilityNotAvailable(View.VISIBLE);
    }

    @Override
    public void showFormProduct() {

        Intent intent = new Intent(getContext(), ProductFormActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void showDetailProduct(String productId) {

        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra("PRODUCT_ID", productId);
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

    private void getProductList() {

        productList.setAdapter(productAdapter);

        swipeChildRefreshLayout.setScrollUpChild(productList);
        swipeChildRefreshLayout.setOnRefreshListener(() -> productPresenter.loadProducts());
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

    private void setVisibilityProduct(int visible) {

        if(productPresent != null)
            productPresent.setVisibility(visible);
    }

    private void setVisibilityNotAvailable(int visible) {

        if(productNotAvailable != null)
            productNotAvailable.setVisibility(visible);
    }
}
