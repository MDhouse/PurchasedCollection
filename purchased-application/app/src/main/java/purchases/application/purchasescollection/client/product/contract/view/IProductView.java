package purchases.application.purchasescollection.client.product.contract.view;

import java.util.List;

import purchases.application.purchasescollection.common.contract.IView;
import purchases.application.purchasescollection.client.product.contract.presenter.IProductPresenter;
import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public interface IProductView extends IView<IProductPresenter> {

    void showProduct(List<ProductDto> products);

    void showNoProducts();

    void showFormProduct();

    void showDetailProduct(String productId);
}
