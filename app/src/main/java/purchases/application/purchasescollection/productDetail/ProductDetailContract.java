package purchases.application.purchasescollection.productDetail;

import purchases.application.purchasescollection.interfaces.BasePresenter;
import purchases.application.purchasescollection.interfaces.BaseView;

public interface ProductDetailContract {

    interface View extends BaseView<Presenter> {

        void hideTitle();

        void showTitle(String title);

        void hideInformation();

        void showInformation(String description);

        void showBuyProduct();

        void showEditedProduct(String productId);

        void showTaskDeleted();

        void showMissingProduct();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void editProduct();

        void deleteProduct();

        void buyProduct();
    }
}
