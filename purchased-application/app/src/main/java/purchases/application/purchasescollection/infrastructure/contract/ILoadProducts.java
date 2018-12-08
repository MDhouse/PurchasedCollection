package purchases.application.purchasescollection.infrastructure.contract;

import java.util.List;

import purchases.application.purchasescollection.infrastructure.model.dto.ProductDto;

public interface ILoadProducts {
    void load(List<ProductDto> products);
    void notAvailable();
}
