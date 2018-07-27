package in.movieapp.com.movieapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by DivyaSethi on 24/07/18.
 */

@Dao
public interface FavouriteTableDao {

    @Query("SELECT * FROM favoriteMovies")
    LiveData<List<FavouriteMovieEntity>> fetchAllFavoriteMovie();

    @Query("SELECT movieTitle FROM favoriteMovies WHERE movieTitle LIKE :movieName")
    LiveData<String> fetchFavoriteMovie(String movieName);

    @Insert
    void insertMovie(FavouriteMovieEntity favouriteMovieEntity);

    @Delete()
    void deleteMovie(FavouriteMovieEntity favouriteMovieEntity);
}
