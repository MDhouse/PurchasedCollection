package purchases.application.purchasescollection.infrastructure.model.dto;


import com.google.common.base.Strings;

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

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isBuy() {
        return buy;
    }

    public boolean isEmpty() {

        return Strings.isNullOrEmpty(id)
                && Strings.isNullOrEmpty(name)
                && price == 0.00
                && amount == 0;
    }

    public String getPriceFormat() {
        return "$ " + String.valueOf(price);
    }

    public String getAmountFormat() {
        return String.valueOf(amount);
    }

    public String getInformation() {
        return isBuy() ? " Product is bought." : " Product to buy.";
    }
}
