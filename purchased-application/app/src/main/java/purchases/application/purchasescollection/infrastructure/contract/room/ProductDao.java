package purchases.application.purchasescollection.infrastructure.contract.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import purchases.application.purchasescollection.infrastructure.model.entity.Product;


@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE id = :id")
    Product getById(String id);

    @Query("SELECT * FROM product WHERE name = :name")
    Product getByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Product product);

    @Query("UPDATE product SET name = :name, buy = :buy, amount = :amount, price = :price  WHERE id = :id")
    void update(String id, String name, int amount, double price, boolean buy);

    @Query("UPDATE product SET buy = :isBuy WHERE id = :id")
    void update(String id, boolean isBuy);

    @Query("DELETE FROM product WHERE id = :id")
    int deleteById(String id);

    @Query("DELETE FROM product")
    void deleteAll();

    @Query("DELETE FROM product WHERE buy = 1")
    int deletePurchasesProduct();
}
