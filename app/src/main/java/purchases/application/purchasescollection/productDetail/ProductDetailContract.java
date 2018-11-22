package purchases.application.purchasescollection.productDetail;

import javax.annotation.Nullable;

import purchases.application.purchasescollection.interfaces.BasePresenter;
import purchases.application.purchasescollection.interfaces.BaseView;

public interface ProductDetailContract {

    interface View extends BaseView<Presenter> {

        void setName(String name);

        void setPrice(double price);

        void setAmount(int amount);

        void setBuy(String status);

        void setOptionBuy(boolean status);

        void showDetailProduct();

        void showMissingProduct();

        void showLoadProduct();

        void showEditedProduct(String productId);

        void showTaskDeleted();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editProduct();

        void deleteProduct();

        void buyProduct();
    }
}
