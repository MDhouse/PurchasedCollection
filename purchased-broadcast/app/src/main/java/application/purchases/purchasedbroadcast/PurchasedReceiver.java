package application.purchases.purchasedbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import application.purchases.purchasedbroadcast.notification.PurchasedNotification;

import static android.content.ContentValues.TAG;

public class PurchasedReceiver extends BroadcastReceiver {

    public PurchasedReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Broadcast intent detected "
                + intent.getAction();

        StringBuilder sb = new StringBuilder();
        String log = sb.toString();
        Log.d(TAG, message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        // PurchasedNotification.notifyReceiver(context, message, 1);

    }
}
