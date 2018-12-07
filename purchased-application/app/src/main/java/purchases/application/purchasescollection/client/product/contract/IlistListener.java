package purchases.application.purchasescollection.client.product.contract;

public interface IlistListener<T> {

    void click(T item);
    void completeAction(T item);
    void unCompleteAction(T item);
}
