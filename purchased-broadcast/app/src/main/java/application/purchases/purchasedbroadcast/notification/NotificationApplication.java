package application.purchases.purchasedbroadcast.notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initNotifyChannel();
    }

    private void initNotifyChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            final NotificationChannel notificationChannel = new NotificationChannel(
                    NotificationConst.getChannelId(),
                    NotificationConst.getChannelName(),
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            final NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
