package purchases.application.purchasescollection.client.product.contract.view;

import purchases.application.purchasescollection.common.contract.IView;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductFormPresenter;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public interface IProductFormView extends IView<IProductFormPresenter> {

    void showEmptyProductError();

    void showProductList();

    void showSwitch();

    void hideSwitch();

    void setProduct(ProductDto product);

    void toIntent(String idProduct, String contentProduct);
}
