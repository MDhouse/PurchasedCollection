package purchases.application.purchasescollection.infrastructure.contract.callback;

import purchases.application.purchasescollection.infrastructure.model.dto.UserDto;

public interface ILoadUser {
    void load(UserDto user);
    void notAvailable();
}
