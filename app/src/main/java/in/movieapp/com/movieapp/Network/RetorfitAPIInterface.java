package in.movieapp.com.movieapp.Network;

import in.movieapp.com.movieapp.AppConstants;
import in.movieapp.com.movieapp.POJO.MovieResultsResponse;
import in.movieapp.com.movieapp.POJO.ReviewResultsResponse;
import in.movieapp.com.movieapp.POJO.TrailerResultResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DivyaSethi.
 */

public interface RetorfitAPIInterface {

    @GET(AppConstants.popularMoviesPath)
    Call<MovieResultsResponse> getPopularMovieResults(@Query(AppConstants.apiKey) String api_key);

    @GET(AppConstants.topRated)
    Call<MovieResultsResponse> getTopRatingMovieResults(@Query(AppConstants.apiKey) String api_key);

    @GET(AppConstants.videos)
    Call<TrailerResultResponse> getTrailerResults(@Query(AppConstants.apiKey) String api_key);

    @GET(AppConstants.reviews)
    Call<ReviewResultsResponse> getMovieReviews(@Query(AppConstants.apiKey) String api_key);
}
