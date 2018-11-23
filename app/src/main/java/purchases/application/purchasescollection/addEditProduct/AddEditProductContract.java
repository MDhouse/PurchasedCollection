package purchases.application.purchasescollection.addEditProduct;

import purchases.application.purchasescollection.interfaces.BasePresenter;
import purchases.application.purchasescollection.interfaces.BaseView;

public interface AddEditProductContract {

    interface View extends BaseView<Presenter> {

        void showEmptyProductError();

        void showProductList();

        void showSwitch();

        void hideSwitch();

        void setName(String name);

        void setPrice(double price);

        void setAmount(int amount);

        void setBuy(boolean buy);

        void toIntent(String idProduct, String contentProduct);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void saveProduct(String name, double price, int amount, boolean buy);

        void populateProduct();

        boolean isNewProduct();

        boolean isVerify(String name, double price, int amount, boolean buy);

        boolean isDataMissing();
    }
}
