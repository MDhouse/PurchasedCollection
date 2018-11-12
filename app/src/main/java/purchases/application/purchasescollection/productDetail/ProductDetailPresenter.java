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
    public void start() { openProduct(); }

    private void openProduct() {

        if(Strings.isNullOrEmpty(productId)) {
            productDetailView.showMissingProduct();
            return;
        }

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

        productLocaleDataSource.deleteProduct(productId);
        productDetailView.showTaskDeleted();
    }

    @Override
    public void buyProduct() {
        if(Strings.isNullOrEmpty(productId)) {
            productDetailView.showMissingProduct();
            return;
        }

        productLocaleDataSource.buyProduct(productId);
        productDetailView.showBuyProduct();
    }

    private void showProduct(Product product) {

        String title = product.getName();
        String description = product.toString();

        if (Strings.isNullOrEmpty(title)) {
            productDetailView.hideTitle();
        } else {
            productDetailView.showTitle(title);
        }

        if (Strings.isNullOrEmpty(description)) {
            productDetailView.hideInformation();
        } else {
            productDetailView.showInformation(description);
        }
    }
}
