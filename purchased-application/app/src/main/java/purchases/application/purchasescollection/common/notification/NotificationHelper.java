package purchases.application.purchasescollection.common.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import purchases.application.purchasescollection.R;

public class NotificationHelper {

    public static Notification notifyProduct(final Context context, final String message, final String productId, final int number) {

        final PendingIntent productListAction = create(context, NotificationConst.getProductListActionName());

        final PendingIntent productDetailAction = create(context, NotificationConst.getProductDetailActionName(),
                new ExtraParameter[]{new ExtraParameter(NotificationConst.getProductDetailFieldName(), productId)});

        final String title = NotificationConst.getTitleNotification();

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationConst.getChannelId())
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_add_shopping_cart)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker(message)
                .setNumber(number)
                .setContentIntent(productDetailAction)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(title))
                .addAction(
                        R.drawable.ic_action_share,
                        NotificationConst.getProductListName(),
                        productListAction)
                .setAutoCancel(true);

        return builder.build();
    }

    private static PendingIntent create(final Context context, final String actionName) {

        return PendingIntent.getActivity(
                context,
                0,
                new Intent(actionName)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent create(final Context context, final String actionName, final ExtraParameter[] extraParameters){

        final Intent intent = new Intent()
                .setAction(actionName)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        for (ExtraParameter parameters: extraParameters)
            intent.putExtra(parameters.getParameterName(), parameters.getParameterValue());

        return PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
