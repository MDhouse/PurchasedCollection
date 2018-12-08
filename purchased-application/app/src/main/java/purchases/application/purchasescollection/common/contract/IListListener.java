package purchases.application.purchasescollection.common.contract;

public interface IListListener<T> {

    void click(T item);
    void completeAction(T item);
    void unCompleteAction(T item);
}
