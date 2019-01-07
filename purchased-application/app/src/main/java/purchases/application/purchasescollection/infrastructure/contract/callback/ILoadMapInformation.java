package purchases.application.purchasescollection.infrastructure.contract.callback;

import java.util.List;

import purchases.application.purchasescollection.infrastructure.model.dto.MarkerDto;
import purchases.application.purchasescollection.infrastructure.model.dto.StoreDto;

public interface ILoadMapInformation {

    void load(List<StoreDto> store);
    void notAvailable();
}
