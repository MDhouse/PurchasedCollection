package application.purchases.purchasedbroadcast;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ReceiveActivity extends AppCompatActivity {

    private PurchasedReceiver purchasedReceiver = new PurchasedReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        IntentFilter newProduct = new IntentFilter("purchases.application.purchasescollection.CREATED_PRODUCT");

        registerReceiver(purchasedReceiver, newProduct, "application.purchases.purchasedbroadcast.HANDLE_INTENT", null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(purchasedReceiver);
    }
}
