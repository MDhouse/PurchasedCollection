package purchases.application.purchasescollection.client.account.contract;

import purchases.application.purchasescollection.common.contract.IPresenter;
import purchases.application.purchasescollection.infrastructure.model.command.user.UserAction;

public interface ILoginPresenter extends IPresenter {

    void singIn(UserAction user);

    void createAccount(UserAction user);
}
