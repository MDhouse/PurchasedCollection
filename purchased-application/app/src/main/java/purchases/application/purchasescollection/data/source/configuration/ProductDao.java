package purchases.application.purchasescollection.data.source.configuration;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import purchases.application.purchasescollection.data.Product;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    List<Product> getProducts();

    @Query("SELECT * FROM product WHERE id = :id")
    Product getProductById(String id);

    @Query("SELECT * FROM product WHERE name = :name")
    Product getProductByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProduct(Product product);

    @Query("UPDATE product SET buy = :buy WHERE id = :id")
    void updateBuyProduct(String id, boolean buy);

    @Query("DELETE FROM product WHERE id = :id")
    int deleteProductById(String id);

    @Query("DELETE FROM product")
    void deleteProducts();

    @Query("DELETE FROM product WHERE buy = 1")
    int deletePurchasesProduct();
}
