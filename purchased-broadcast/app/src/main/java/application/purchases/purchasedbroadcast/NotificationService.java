package application.purchases.purchasedbroadcast;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {

    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "Notification Channel";
    private static final int IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
    private static int NOTIFICATION_ID = 0;


    public static final String PRODUCT_DETAIL_ACTION = "purchases.application.purchasescollection.PRODUCT_DETAIL";
    public static final String PRODUCT_DETAIL_FIELD = "PRODUCT_ID";

    public static final String PRODUCT_LIST_ACTION ="purchases.application.purchasescollection.PRODUCT_LIST";

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
