package purchases.application.purchasescollection.interfaces;

public interface ItemListListener<T> {

    void onClick(T itemList);

    void onCompleteClick(T itemList);

    void onUnCompleteClick(T itemList);
}
