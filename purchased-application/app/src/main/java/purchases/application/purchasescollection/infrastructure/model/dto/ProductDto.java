package purchases.application.purchasescollection.infrastructure.model.dto;


public class ProductDto {

    private String id;
    private String name;
    private double price;
    private int amount;
    private boolean buy;

    public ProductDto(String id, String name, double price, int amount, boolean buy) {
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

    public boolean isBuy() {
        return buy;
    }
}
