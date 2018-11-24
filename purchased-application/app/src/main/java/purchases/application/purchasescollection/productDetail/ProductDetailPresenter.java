package purchases.application.purchasescollection.productDetail;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import javax.annotation.Nullable;

import purchases.application.purchasescollection.data.Product;
import purchases.application.purchasescollection.data.source.implement.ProductLocaleDataSource;
import purchases.application.purchasescollection.data.source.interfaces.ProductDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductDetailPresenter implements ProductDetailContract.Presenter {

    private final ProductLocaleDataSource productLocaleDataSource;
    private final ProductDetailContract.View productDetailView;

    @Nullable
    private String productId;

    public ProductDetailPresenter(@NonNull ProductLocaleDataSource productLocaleDataSource, @NonNull ProductDetailContract.View productDetailView, @Nullable String productId) {
        this.productLocaleDataSource =  checkNotNull(productLocaleDataSource);
        this.productDetailView = checkNotNull(productDetailView);
        this.productId = productId;

        this.productDetailView.setPresenter(this);
    }

    @Override
    public void start() {

        this.productDetailView.showLoadProduct();

        openProduct();
    }

    @Override
    public void editProduct() {
        if(Strings.isNullOrEmpty(productId)) {
            productDetailView.showMissingProduct();
            return;
        }

        productDetailView.showEditedProduct(productId);
    }

    @Override
    public void deleteProduct() {

        if(Strings.isNullOrEmpty(productId)) {
            productDetailView.showMissingProduct();
            return;
        }

        productDetailView.showLoadProduct();
        productLocaleDataSource.deleteProduct(productId);
        productDetailView.showTaskDeleted();
    }

    @Override
    public void buyProduct() {

        if(Strings.isNullOrEmpty(productId)) {
            productDetailView.showMissingProduct();
            return;
        }

        productDetailView.showLoadProduct();
        productLocaleDataSource.buyProduct(productId);
        loadProduct();
    }


    private void openProduct() {

        if(Strings.isNullOrEmpty(productId)) {
            productDetailView.showMissingProduct();
            return;
        }

        loadProduct();
    }

    private void loadProduct(){
        productLocaleDataSource.getProduct(productId, new ProductDataSource.LoadProductCallback() {
            @Override
            public void onProductLoad(Product product) {

                if(!productDetailView.isActive()) {
                    return;
                }

                if( null == product) {
                    productDetailView.showMissingProduct();
                } else {
                    showProduct(product);
                }
            }

            @Override
            public void onDataNotAvailable() {

                if(!productDetailView.isActive()) {
                    return;
                }

                productDetailView.showMissingProduct();
            }
        });
    }

    private void showProduct(Product product) {

        if(!product.isEmpty()) {
            productDetailView.setName(product.getName());
            productDetailView.setAmount(product.getAmount());
            productDetailView.setPrice(product.getPrice());
            productDetailView.setBuy(product.isParchedOrNot());

            productDetailView.setOptionBuy(product.getBuy());

            productDetailView.showDetailProduct();
        } else {
            productDetailView.showMissingProduct();
        }
    }

}
