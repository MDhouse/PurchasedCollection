package purchases.application.purchasescollection.infrastructure.implement.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import purchases.application.purchasescollection.common.notification.NotificationConst;
import purchases.application.purchasescollection.common.notification.NotificationHelper;

public class ProductNotifyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String productId = intent.getStringExtra(NotificationConst.getProductIdReceiver());
        final String productContent = intent.getStringExtra(NotificationConst.getProductContentReceiver());

        final Notification notify = NotificationHelper.notifyProduct(this,productContent, productId , 0);

        startForeground(NotificationConst.getNotificationId(), notify);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
