package purchases.application.purchasescollection.common.componenent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SwipeChildRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    private View scrollUpChild;

    public SwipeChildRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public SwipeChildRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollUpChild(View scrollUpChild) {

        this.scrollUpChild = scrollUpChild;
    }
}
