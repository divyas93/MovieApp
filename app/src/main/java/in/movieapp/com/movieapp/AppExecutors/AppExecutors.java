package in.movieapp.com.movieapp.AppExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by DivyaSethi on 25/07/18.
 */
public class AppExecutors {

    private final static String TAG = AppExecutors.class.getSimpleName();
    private final static Object lock = new Object();
    private static AppExecutors appExecutors;
    private final Executor diskIO;

    private AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static AppExecutors getAppExecutors() {
        if (appExecutors == null) {
            synchronized (lock) {
                appExecutors = new AppExecutors(Executors.newSingleThreadExecutor());
            }
        }
        return appExecutors;
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
