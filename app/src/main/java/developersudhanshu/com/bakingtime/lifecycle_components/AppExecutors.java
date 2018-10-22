package developersudhanshu.com.bakingtime.lifecycle_components;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    public static Executor diskIO;
    public static AppExecutors sInstance;
    public static final Object LOCK = new Object();

    public AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static AppExecutors getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return diskIO;
    }
}
