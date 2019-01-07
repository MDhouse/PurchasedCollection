package purchases.application.purchasescollection.infrastructure.contract.callback;

import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public interface ILoadProduct {
    void load(ProductDto product);
    void notAvailable();
}
