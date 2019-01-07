package purchases.application.purchasescollection.common.contract;

public interface IStoreListener<T> {

    void toRadiusEdit(T item);
    void toMap(String id);
}
