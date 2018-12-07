package purchases.application.purchasescollection.client.product.contract.view;

import purchases.application.purchasescollection.client.product.contract.IView;

public interface IProductDetailView extends IView<IProductDetailView> {

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
