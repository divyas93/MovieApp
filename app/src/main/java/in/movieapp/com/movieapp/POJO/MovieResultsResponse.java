package in.movieapp.com.movieapp.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DivyaSethi.
 */

public class MovieResultsResponse {

    @SerializedName("results")
    public List<MovieResultsInfo> results = new ArrayList<>();


    public ArrayList getMovieResults() {
        return (ArrayList) results;
    }

    public void setMovieResults(List results) {
        this.results = results;
    }

    public class MovieResultsInfo {

        @SerializedName("poster_path")
        public String posterPathThumbnail;
        @SerializedName("original_title")
        public String originalTitle;
        @SerializedName("overview")
        public String overview;
        @SerializedName("vote_average")
        public Float userRating;
        @SerializedName("release_date")
        public String releaseDate;
        @SerializedName("id")
        public int id;

        public String getPosterPathThumbnail() {
            return posterPathThumbnail;
        }

        public void setPosterPathThumbnail(String posterPathThumbnail) {
            this.posterPathThumbnail = posterPathThumbnail;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public Float getUserRating() {
            return userRating;
        }

        public void setUserRating(Float userRating) {
            this.userRating = userRating;
        }


        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
