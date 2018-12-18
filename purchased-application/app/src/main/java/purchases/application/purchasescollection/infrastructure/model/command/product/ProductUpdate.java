package purchases.application.purchasescollection.infrastructure.model.command.product;

public class ProductUpdate {

    private final String id;
    private final String name;
    private final double price;
    private final int amount;
    private final boolean buy;

    public ProductUpdate(String id, String name, double price, int amount, boolean buy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.buy = buy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isBuy() {
        return buy;
    }
}
