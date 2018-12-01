package application.purchases.purchasedbroadcast;


import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import application.purchases.purchasedbroadcast.notification.PurchasedNotification;


public class ReceiveActivity extends AppCompatActivity {

    private static final String ACTION_NEW_PRODUCT = "purchases.application.purchasescollection.CREATED_PRODUCT";
    private static final String ACTION_PERMISSION = "application.purchases.purchasedbroadcast.HANDLE_INTENT";
    private int NOTIFICATION_ID = 0;

    private PurchasedReceiver purchasedReceiver;

    @Override
    protected void onResume() {

        super.onResume();
        purchasedReceiver = new PurchasedReceiver();

        IntentFilter newProduct = new IntentFilter();
        newProduct.addAction("CREATED_PRODUCT");
        newProduct.addAction("Test");

        registerReceiver(purchasedReceiver, newProduct, "application.purchases.purchasedbroadcast.HANDLE_INTENT", null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
    }

    @Override
    protected void onStop() {

        super.onStop();
        unregisterReceiver(purchasedReceiver);
    }

    public void onNotify(View view) {

        sendBroadcast(new Intent("Test"), "application.purchases.purchasedbroadcast.HANDLE_INTENT");
    }
}
