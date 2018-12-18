package purchases.application.purchasescollection.common.contract;

public interface IView<T> {

    void setPresenter(T presenter);

    boolean isActive();
}
