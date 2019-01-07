package purchases.application.purchasescollection.infrastructure.contract.callback;

import java.util.List;

import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;

public interface ILoadStores {

    void load(List<StoreDto> stores);
    void notAvailable();
}
