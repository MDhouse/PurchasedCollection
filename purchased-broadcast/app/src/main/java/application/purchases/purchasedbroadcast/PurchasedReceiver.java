package application.purchases.purchasedbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PurchasedReceiver extends BroadcastReceiver {

    public PurchasedReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("purchases.application.purchasescollection.CREATED_PRODUCT".equals(intent.getAction())){
            StringBuilder message = new StringBuilder();

            message.append("Broadcast intent detected "
                    + intent.getAction());
            message.append(intent.getStringExtra("PRODUCT_CONTENT"));

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
