package purchases.application.purchasescollection.common.utilities.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {

    private Handler mainThreadHandler;

    public MainThreadExecutor() {
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mainThreadHandler.post(command);
    }
}
