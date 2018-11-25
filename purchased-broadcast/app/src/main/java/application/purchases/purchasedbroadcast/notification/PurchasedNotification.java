package application.purchases.purchasedbroadcast.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import application.purchases.purchasedbroadcast.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PurchasedNotification {

    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    public static final String CHANNEL_NAME = "Notification Channel";
    private static final int IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
    private static int NOTIFICATION_ID = 0;


    public static final String PRODUCT_DETAIL_ACTION = "purchases.application.purchasescollection.PRODUCT_DETAIL";
    public static final String PRODUCT_DETAIL_FIELD = "PRODUCT_ID";

    public static final String PRODUCT_LIST_ACTION ="purchases.application.purchasescollection.PRODUCT_LIST";

    private static final String NOTIFICATION_TAG = "Purchased";

    public static void notify(final Context context,
                              final String message, final String productId, final int number) {

        final Resources res = context.getResources();

        final String title = res.getString(R.string.purchased_notification_title_template);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_purchased)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker(message)
                .setNumber(number)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(title))
                .addAction(
                        R.drawable.ic_action_stat_share,
                        res.getString(R.string.action_share),
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(PRODUCT_LIST_ACTION)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(
                        R.drawable.ic_action_stat_reply,
                        res.getString(R.string.action_reply),
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(PRODUCT_DETAIL_ACTION)
                                        .putExtra(PRODUCT_DETAIL_FIELD, productId)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        ,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        ))
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    public static void notifyReceiver(final Context context,
                                      final String exampleString, final int number){


        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(), 0);

        final Resources res = context.getResources();

        final String title = res.getString(R.string.purchased_notification_title_template);
        final String text = res.getString(R.string.purchased_notification_placeholder_text_template, exampleString);

        //noinspection deprecation
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_purchased)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker(exampleString)
                .setNumber(number)
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text)
                        .setBigContentTitle(title)
                        .setSummaryText("Dummy summary text"))
                .addAction(
                        R.drawable.ic_action_stat_share,
                        res.getString(R.string.action_share),
                        PendingIntent.getActivity(
                                context,
                                0,
                                Intent.createChooser(new Intent(Intent.ACTION_SEND)
                                        .setType("text/plain")
                                        .putExtra(Intent.EXTRA_TEXT, "Dummy text"), "Dummy title"),
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(
                        R.drawable.ic_action_stat_reply,
                        res.getString(R.string.action_reply),
                        null)
                .setAutoCancel(true);

        notify(context, builder.build());

    }

    private static void notify(final Context context, final Notification notification) {

        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(initChannel());

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

// --Commented out by Inspection START (24.11.2018 12:57):
//    /**
//     * Cancels any notifications of this type previously shown using
//     * {@link #notify(Context, String, int)}.
//     */
//    @TargetApi(Build.VERSION_CODES.ECLAIR)
//    public static void cancel(final Context context) {
//        final NotificationManager nm = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
//            nm.cancel(NOTIFICATION_TAG, 0);
//        } else {
//            nm.cancel(NOTIFICATION_TAG.hashCode());
//        }
//    }
// --Commented out by Inspection STOP (24.11.2018 12:57)


    private static NotificationChannel initChannel() {

        final NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, IMPORTANCE);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.setVibrationPattern(new long[] {
                500,
                500,
                500,
                500,
                500
        });

        return notificationChannel;
    }
}
