package in.movieapp.com.movieapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by DivyaSethi on 24/07/18.
 */

@Entity(tableName = "favoriteMovies")
public class FavouriteMovieEntity {

    @PrimaryKey
    @NonNull
    private String movieTitle;
    private String releaseDate;
    private Float userRating;
    private String overview;
    private String userReviews;
    private int movieId;
    private String posterPath;

    public FavouriteMovieEntity(String posterPath, String movieTitle, String releaseDate, Float userRating, String overview, int movieId) {
        this.posterPath = posterPath;
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.overview = overview;
        this.movieId = movieId;
    }

    public Float getUserRating() {
        return userRating;
    }

    public void setUserRating(Float userRating) {
        this.userRating = userRating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(String userReviews) {
        this.userReviews = userReviews;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
