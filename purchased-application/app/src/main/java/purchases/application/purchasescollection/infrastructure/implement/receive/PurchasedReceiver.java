package purchases.application.purchasescollection.infrastructure.implement.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import purchases.application.purchasescollection.common.notification.NotificationConst;
import purchases.application.purchasescollection.infrastructure.implement.service.ProductNotifyService;

public class PurchasedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("purchases.application.purchasescollection.CREATED_PRODUCT".equals(intent.getAction())){
            runNotifyProduct(context, intent);
        }

        if(NotificationConst.getPurchasedToast().equals(intent.getAction())){
            runToastShow(context, intent);
        }
    }

    private void runNotifyProduct(Context context, Intent intent) {

        final String idProduct = intent.getStringExtra(NotificationConst.getProductIdReceiver());
        final String contentProduct = intent.getStringExtra(NotificationConst.getProductContentReceiver());

        Intent serviceIntent = new Intent(context, ProductNotifyService.class);
        serviceIntent.putExtra(NotificationConst.getProductIdReceiver(), idProduct);
        serviceIntent.putExtra(NotificationConst.getProductContentReceiver(), contentProduct);
        context.startService(serviceIntent);
    }

    private void runToastShow(Context context, Intent intent){

        final String message = intent.getStringExtra(NotificationConst.getPurchasedToastMessage());

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
