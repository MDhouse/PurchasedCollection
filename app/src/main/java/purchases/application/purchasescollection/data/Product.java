package purchases.application.purchasescollection.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.util.UUID;

@Entity(tableName = "product")
public final class Product {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;

    @Nullable
    @ColumnInfo(name = "name")
    private final String name;

    @Nullable
    @ColumnInfo(name = "price")
    private final double price;

    @Nullable
    @ColumnInfo(name = "amount")
    private final int amount;

    @Nullable
    @ColumnInfo(name = "buy")
    private final boolean buy;

    @Ignore
    public Product(@Nullable String mName, @Nullable double mPrice, @Nullable int mAmount) {
        this(UUID.randomUUID().toString(), mName, mPrice, mAmount, false);
    }

    @Ignore
    public Product(@Nullable String mName, @Nullable double mPrice, @Nullable int mAmount, @Nullable boolean mBuy) {
        this(UUID.randomUUID().toString(), mName, mPrice, mAmount, mBuy);
    }

    @Ignore
    public Product(@NonNull String id, @Nullable String mName, @Nullable double mPrice, @Nullable int mAmount) {
        this(id, mName, mPrice, mAmount, false);
    }

    public Product(@NonNull String id, @Nullable String name, @Nullable double price, @Nullable int amount, @Nullable boolean buy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.buy = buy;
    }


    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public double getPrice() {
        return price;
    }

    @Nullable
    public int getAmount() {
        return amount;
    }

    @Nullable
    public boolean getBuy() { return buy; }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(name)
                && price == 0.00
                && amount == 0;
    }

    @Override
    public String toString() {
        String statusIsBuy = isParchedOrNot();

        return "Product " + name + '\'' +
                "Price: " + price + '\'' +
                "Amount: " + amount + '\'' +
                statusIsBuy;
    }

    public String isParchedOrNot() {
        if(buy) {
            return " Product is bought.";
        } else {
            return " Product to buy.";
        }
    }
}
