package application.purchases.purchasedbroadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import application.purchases.purchasedbroadcast.notification.PurchasedNotification;


public class ReceiveActivity extends AppCompatActivity {
    private int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
    }

    public void onNotify(View view) {

        // sendBroadcast(new Intent("Test"));

        PurchasedNotification.notify(getApplicationContext(), "Product Test Not'Price: 12.0'Amount: 1' Product to buy.y" , "7c0a21bd-35c6-4d5a-bfca-f376f5b2bc85", NOTIFICATION_ID);
        NOTIFICATION_ID++;
    }
}
