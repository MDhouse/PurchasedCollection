package application.purchases.purchasedbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import application.purchases.purchasedbroadcast.notification.NotificationConst;
import application.purchases.purchasedbroadcast.notification.NotificationService;

public class PurchasedReceiver extends BroadcastReceiver {

    public PurchasedReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("purchases.application.purchasescollection.CREATED_PRODUCT".equals(intent.getAction())){

            final String idProduct = intent.getStringExtra(NotificationConst.getProductIdReceiver());
            final String contentProduct = intent.getStringExtra(NotificationConst.getProductContentReceiver());

            Intent serviceIntent = new Intent(context, NotificationService.class);
            serviceIntent.putExtra(NotificationConst.getProductIdReceiver(), idProduct);
            serviceIntent.putExtra(NotificationConst.getProductContentReceiver(), contentProduct);
            context.startService(serviceIntent);
        }
    }
}
