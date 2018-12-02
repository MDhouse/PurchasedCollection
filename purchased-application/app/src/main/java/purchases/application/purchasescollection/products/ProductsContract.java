package purchases.application.purchasescollection.products;

import android.support.annotation.NonNull;

import java.util.List;

import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.interfaces.BasePresenter;
import purchases.application.purchasescollection.interfaces.BaseView;

public interface ProductsContract {

    interface View extends BaseView<Presenter> {

        void showProducts(List<Product> products);

        void showAddProduct();

        void showNoProducts();

        void showDetailProduct(String productId);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadProducts(boolean forceUpdate);

        void addNewProduct();

        void openProductDetail(@NonNull Product product);

        void buyProduct(@NonNull Product product);

        void notBuyProduct(Product itemList);
    }
}
