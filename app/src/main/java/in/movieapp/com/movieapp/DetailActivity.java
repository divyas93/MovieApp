package in.movieapp.com.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.movieapp.com.movieapp.Adapter.ReviewAdapter;
import in.movieapp.com.movieapp.Adapter.TrailerAdapter;
import in.movieapp.com.movieapp.AppExecutors.AppExecutors;
import in.movieapp.com.movieapp.Network.NetworkUtils;
import in.movieapp.com.movieapp.Network.RetorfitAPIInterface;
import in.movieapp.com.movieapp.POJO.ReviewResultsResponse;
import in.movieapp.com.movieapp.POJO.TrailerResultResponse;
import in.movieapp.com.movieapp.database.FavouriteDatabase;
import in.movieapp.com.movieapp.database.FavouriteMovieEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DivyaSethi.
 */

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerClickListener{

    private static final String TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.originalTitleText) TextView mOriginalTitleText;
    @BindView(R.id.moviePoster) ImageView moviePosterView;
    @BindView(R.id.userRatingText) TextView mUserRatingText;
    @BindView(R.id.releaseDateText) TextView mReleaseDateText;
    @BindView(R.id.overViewText) TextView mOverViewText;
    @BindView(R.id.trailerRecyclerViewer) RecyclerView mTrailerRecyclerViewer;
    @BindView(R.id.trailerErrorText) TextView mTrailerErrortextView;
    @BindView(R.id.reviewRecyclerViewer) RecyclerView mReviewRecyclerView;
    @BindView(R.id.reviewErrorText) TextView mReviewErrortextView;

    private List<TrailerResultResponse.TrailerResultsInfo> trailerResultsResponseData;
    private TrailerAdapter trailerAdapter;
    private List<ReviewResultsResponse.ReviewResultsInfo> reviewResultsResponseData;
    private ReviewAdapter reviewAdapter;
    private RetorfitAPIInterface apiInterface;

    private MenuItem fav;
    private MenuItem unFav;
    private FavouriteDatabase mDb;

    private String movieTitle;
    private String releaseDate;
    private Float userRating;
    private String overview;
    private int movieId;
    private String posterPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setTitle("Movie Info");

        mDb = FavouriteDatabase.getFavouriteDatabaseInstance(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(AppConstants.posterPathKey)) {
                posterPath = intent.getStringExtra(AppConstants.posterPathKey);
                String url = AppConstants.movieThumbnailBaseURL + posterPath;
                Picasso.with(this).load(url).into(moviePosterView);
            }

            if (intent.hasExtra(AppConstants.titleKey)) {
                movieTitle = intent.getStringExtra(AppConstants.titleKey);
                mOriginalTitleText.setText(movieTitle);
            }

            if (intent.hasExtra(AppConstants.releaseDateKey)) {
                releaseDate = intent.getStringExtra(AppConstants.releaseDateKey);
                mReleaseDateText.setText(releaseDate);
            }

            if (intent.hasExtra(AppConstants.ratingKey)) {
                userRating = intent.getFloatExtra(AppConstants.ratingKey, 0L);
                mUserRatingText.setText(String.valueOf(userRating));
            }

            if (intent.hasExtra(AppConstants.overviewKey)) {
                overview = intent.getStringExtra(AppConstants.overviewKey);
                mOverViewText.setText(overview);
            }

            if (intent.hasExtra(AppConstants.id)) {
                movieId = intent.getIntExtra(AppConstants.id, -1);
                getTrailerData(movieId);
                getReviewData(movieId);
            }

        } else {
            Toast mToast = Toast.makeText(this, getString(R.string.errorString), Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    private void getTrailerData(int id) {
        String url = AppConstants.movieBaseURL + id + "/";
        apiInterface = new NetworkUtils().getRetorfitAPIInterface(url);
        Call<TrailerResultResponse> trailerResults = apiInterface.getTrailerResults(AppConstants.apiKeyValue);
        trailerResults.enqueue(new Callback<TrailerResultResponse>() {
            @Override
            public void onResponse(Call<TrailerResultResponse> call, Response<TrailerResultResponse> response) {
                if (response.code() == 200) {
                    Log.d("trailerResponseGET", response.body().getTrailerResults().toString());
                    trailerResultsResponseData = response.body().getTrailerResults();
                    loadTrailerData(true);
                    showTrailerRecyclerView();

                }
                else {
                    loadTrailerData(false);
                }
            }

            @Override
            public void onFailure(Call<TrailerResultResponse> call, Throwable t) {
                loadTrailerData(false);
                mTrailerErrortextView.setText(getString(R.string.trailerNetworkErrorText));
            }
        });
    }



    private void loadTrailerData(boolean loadTrailerValue) {
        if(loadTrailerValue) {
            mTrailerErrortextView.setVisibility(View.GONE);
            mTrailerRecyclerViewer.setVisibility(View.VISIBLE);
        }
        else {
            mTrailerErrortextView.setVisibility(View.VISIBLE);
            mTrailerRecyclerViewer.setVisibility(View.GONE);
        }
    }

    private void showTrailerRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTrailerRecyclerViewer.setLayoutManager(layoutManager);
        trailerAdapter = new TrailerAdapter(this, trailerResultsResponseData, this);
        mTrailerRecyclerViewer.setAdapter(trailerAdapter);
    }


    @Override
    public void onClick(int  postion) {
        watchYoutubeVideo(postion);
    }

    public void watchYoutubeVideo(int position){

        String id = trailerResultsResponseData.get(position).getKey();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.youtubeAppBaseUrl + id));

        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(AppConstants.youtubeWebBaseUrl + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    private void getReviewData(int id) {
        String url = AppConstants.movieBaseURL + id + "/";
        apiInterface = new NetworkUtils().getRetorfitAPIInterface(url);
        final Call<ReviewResultsResponse> reviewResultsResponseCall = apiInterface.getMovieReviews(AppConstants.apiKeyValue);
        reviewResultsResponseCall.enqueue(new Callback<ReviewResultsResponse>() {
            @Override
            public void onResponse(Call<ReviewResultsResponse> call, Response<ReviewResultsResponse> response) {
                if(response.code() == 200) {
                    Log.d("reviewResponseGET", response.body().getReviewResults().toString());
                    reviewResultsResponseData = response.body().getReviewResults();
                    if(reviewResultsResponseData.isEmpty()) {
                        loadReviewData(false);
                    }
                    else {
                        loadReviewData(true);
                        showReviewRecyclerView();
                    }

                }
            }

            @Override
            public void onFailure(Call<ReviewResultsResponse> call, Throwable t) {
                loadReviewData(false);
                mReviewErrortextView.setText(getString(R.string.reviewNetworkErrorText));
            }
        });

    }

    private void loadReviewData(boolean loadReviewData) {
        if(loadReviewData) {
            mReviewErrortextView.setVisibility(View.GONE);
            mReviewRecyclerView.setVisibility(View.VISIBLE);
        }
        else {
            mReviewErrortextView.setVisibility(View.VISIBLE);
            mReviewRecyclerView.setVisibility(View.GONE);
        }
    }

    private void showReviewRecyclerView(){
        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewAdapter = new ReviewAdapter(reviewResultsResponseData, this);
        mReviewRecyclerView.setAdapter(reviewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_menu, menu);
        fav = menu.findItem(R.id.markFavourite);
        unFav = menu.findItem(R.id.markUnfavourite);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appViewModel.fetchFavMovieDetails(movieTitle).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String movie) {
                Log.d(TAG, "Receiving database update from LiveData");
                if (movie != null && movie.equalsIgnoreCase(movieTitle)) {
                    //setUnfavVisibility();
                    setFavVisibility(false, 0, 2);
                } else {
                    //setFavVisibility();
                    setFavVisibility(true, 2, 0);
                }
            }
        });


        return super.onPrepareOptionsMenu(menu);
    }

    private void setFavVisibility(boolean favVisible, int favShowAsAction, int unfavShowAsAction) {
        fav.setVisible(favVisible);
        fav.setShowAsAction(favShowAsAction);
        unFav.setVisible(!favVisible);
        unFav.setShowAsAction(unfavShowAsAction);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.markFavourite) {
            final FavouriteMovieEntity favouriteMovieEntity = new FavouriteMovieEntity(posterPath, movieTitle, releaseDate, userRating, overview, movieId);
            AppExecutors.getAppExecutors().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favouriteTableDao().insertMovie(favouriteMovieEntity);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setFavVisibility(false, 0, 2);

                        }
                    });
                }

            });

        }

        if(item.getItemId() == R.id.markUnfavourite) {
            final FavouriteMovieEntity favouriteMovieEntity = new FavouriteMovieEntity(posterPath, movieTitle, releaseDate, userRating, overview, movieId);
            AppExecutors.getAppExecutors().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.favouriteTableDao().deleteMovie(favouriteMovieEntity);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setFavVisibility(true, 2, 0);
                        }
                    });
                }
            });


        }
        return super.onOptionsItemSelected(item);


    }
}