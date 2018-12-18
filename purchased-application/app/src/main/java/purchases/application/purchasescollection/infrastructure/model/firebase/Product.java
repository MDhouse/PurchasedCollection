package purchases.application.purchasescollection.infrastructure.model.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Product {

    public String id;
    public String name;
    public double price;
    public int amount;
    public boolean buy;
    public String uuid;

    public Product() {
    }

    public Product(String id, String name, double price, int amount, boolean buy, String uuid) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.buy = buy;
        this.uuid = uuid;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result =  new HashMap<>();

        result.put("id", id);
        result.put("name", name);
        result.put("price", price);
        result.put("amount", amount);
        result.put("buy", buy);
        result.put("uuid", uuid);

        return result;
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

    public String getUuid() {
        return uuid;
    }
}
