package in.movieapp.com.movieapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import in.movieapp.com.movieapp.database.FavouriteDatabase;
import in.movieapp.com.movieapp.database.FavouriteMovieEntity;

/**
 * Created by DivyaSethi on 25/07/18.
 */
public class AppViewModel extends AndroidViewModel {

    private static final String TAG = AppViewModel.class.getSimpleName();
    LiveData<List<FavouriteMovieEntity>> fetchAllFavMovies;
    LiveData<String> fetchMovieDetails;
    FavouriteDatabase mDb;

    public AppViewModel(@NonNull Application application) {
        super(application);
        mDb = initializeDb();
        Log.d(TAG, "Receiving data from db");
    }

    LiveData<List<FavouriteMovieEntity>> fetchAllFavMovies() {
        fetchAllFavMovies = mDb.favouriteTableDao().fetchAllFavoriteMovie();
        return fetchAllFavMovies;
    }

    LiveData<String> fetchFavMovieDetails(String movieName) {
//        mDb = initializeDb();
        fetchMovieDetails = mDb.favouriteTableDao().fetchFavoriteMovie(movieName);
        return fetchMovieDetails;
    }

    public FavouriteDatabase initializeDb() {
        return FavouriteDatabase.getFavouriteDatabaseInstance(this.getApplication());
    }
}
