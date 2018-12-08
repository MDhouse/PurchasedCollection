package purchases.application.purchasescollection.common.utilities.executor;

import java.util.concurrent.Executor;

public class ApplicationExecutor {

    private final Executor diskIO;

    private final Executor mainThread;

    public ApplicationExecutor(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    public ApplicationExecutor() {
        this(new DiskIOThreadExecutor(), new MainThreadExecutor());
    }

    public Executor getDiskIO() { return diskIO; }

    public Executor getMainThread() { return mainThread; }
}
