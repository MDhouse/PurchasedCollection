package purchases.application.purchasescollection.infrastructure.model.command.product;

import java.util.UUID;

import purchases.application.purchasescollection.infrastructure.model.entity.Product;

public class ProductCreate {

    private final String name;
    private final double price;
    private final int amount;
    private final String uuid;

    public ProductCreate(String name, double price, int amount, String uuid) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.uuid = uuid;
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

    public Product toEntity() {

        return new Product(this.name, this.price, this.amount);
    }

    public purchases.application.purchasescollection.infrastructure.model.firebase.Product toFirebase() {
        return new purchases.application.purchasescollection.infrastructure.model.firebase.Product(
                UUID.randomUUID().toString(),
                name,
                price,
                amount,
                false,
                uuid
        );
    }
}
