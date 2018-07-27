package in.movieapp.com.movieapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

/**
 * Created by DivyaSethi on 23/07/18.
 */

@Database(entities = {FavouriteMovieEntity.class}, exportSchema = false, version = 1)
public abstract class FavouriteDatabase extends RoomDatabase {

    private final static String FAVOURITE_DATABASE_NAME = "favouriteMovieDb";
    private final static String TAG = FavouriteDatabase.class.getSimpleName();
    private final static Object lock = new Object();
    private static volatile FavouriteDatabase favouriteDatabase;

    public static FavouriteDatabase getFavouriteDatabaseInstance(Context context) {
        if (favouriteDatabase == null) {
            synchronized (lock) {
                Log.d(TAG, "Creating new db instance");
                favouriteDatabase = Room.databaseBuilder(context, FavouriteDatabase.class, FAVOURITE_DATABASE_NAME).build();
            }
        }

        Log.d(TAG, "returning db instance");
        return favouriteDatabase;
    }

    public abstract FavouriteTableDao favouriteTableDao();

}
