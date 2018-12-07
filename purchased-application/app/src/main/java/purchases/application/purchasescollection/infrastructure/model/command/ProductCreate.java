package purchases.application.purchasescollection.infrastructure.model.command;

import purchases.application.purchasescollection.infrastructure.model.entity.Product;

public class ProductCreate {

    private final String name;
    private final double price;
    private final int amount;

    public ProductCreate(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
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

    public Product toProduct() {

        return new Product(this.name, this.price, this.amount);
    }
}
