package purchases.application.purchasescollection.infrastructure.model.command.product;

public class ProductTransaction {

    private final String id;
    private final boolean toBuy;

    public ProductTransaction(String id, boolean toBuy) {
        this.id = id;
        this.toBuy = toBuy;
    }

    public String getId() {
        return id;
    }

    public boolean isToBuy() {
        return toBuy;
    }
}
