package application.purchases.purchasedbroadcast.notification;


public class NotificationConst {

    private static int NOTIFICATION_ID = 0;
    private static final String TITLE_NOTIFICATION = "Purchased Broadcast";

    private static final String CHANNEL_ID = "BC995FEF-6EE8-413E-8E60-8F122ADD3E00";
    private static final String CHANNEL_NAME = "Purchases Channel Notification";

    private static final String PRODUCT_ID_RECEIVER = "PRODUCT_ID'S";
    private static final String PRODUCT_CONTENT_RECEIVER = "PRODUCT_CONTENT'S";

    private static final String PRODUCT_LIST_NAME = "Product List";
    private static final String PRODUCT_LIST_ACTION_NAME ="purchases.application.purchasescollection.PRODUCT_LIST";
    private static final String PRODUCT_DETAIL_ACTION_NAME = "purchases.application.purchasescollection.PRODUCT_DETAIL";
    private static final String PRODUCT_DETAIL_FIELD_NAME = "PRODUCT_ID";

    public static int getNotificationId() {

        NOTIFICATION_ID++;
        return NOTIFICATION_ID;
    }

    public static String getTitleNotification() {

        return TITLE_NOTIFICATION;
    }

    public static String getChannelId() {

        return CHANNEL_ID;
    }

    public static String getChannelName() {

        return CHANNEL_NAME;
    }

    public static String getProductIdReceiver() {

        return PRODUCT_ID_RECEIVER;
    }

    public static String getProductContentReceiver() {

        return PRODUCT_CONTENT_RECEIVER;
    }

    public static String getProductListName() {

        return PRODUCT_LIST_NAME;
    }

    public static String getProductListActionName() {

        return PRODUCT_LIST_ACTION_NAME;
    }

    public static String getProductDetailActionName() {

        return PRODUCT_DETAIL_ACTION_NAME;
    }

    public static String getProductDetailFieldName() {

        return PRODUCT_DETAIL_FIELD_NAME;
    }
}
