package purchases.application.purchasescollection.client.product.contract.view;

import purchases.application.purchasescollection.common.contract.IView;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductDetailPresenter;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public interface IProductDetailView extends IView<IProductDetailPresenter> {

    void setDetailProduct(ProductDto product);

    void showDetailProduct();

    void showMissingProduct();

    void showLoadProduct();

    void showEditedProduct(String productId);

    void showDeletedProduct();
}
