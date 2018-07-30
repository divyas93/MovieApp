package in.movieapp.com.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.movieapp.com.movieapp.Adapter.MovieAdapter;
import in.movieapp.com.movieapp.Network.NetworkUtils;
import in.movieapp.com.movieapp.Network.RetorfitAPIInterface;
import in.movieapp.com.movieapp.POJO.MovieResultsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DivyaSethi.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.movieRecyclerView) RecyclerView movieRecyclerView;
    @BindView(R.id.favoriteMovie) TextView mTextView;

    private MovieAdapter movieAdapter;

    private MenuItem topRating;
    private MenuItem popularity;

    private RetorfitAPIInterface retorfitAPIInterface;

    private Parcelable layoutManagerSavedstate;
    private final String SAVED_LAYOUT_MANAGER = "layoutManager";

    private SharedPreferences preferences;
    private Object lock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        retorfitAPIInterface = new NetworkUtils().getRetorfitAPIInterface(AppConstants.movieBaseURL);
        showMoviesInRecyclerView();
        preferences = getSharedPreferences("MovieAppPref", MODE_PRIVATE);
        String sortOrder = preferences.getString(AppConstants.sortTypeKey, AppConstants.popularMoviesPath);
        populateMovies(sortOrder);
    }

    private void populateMovies(String sortOrder){
        if(sortOrder.equalsIgnoreCase(AppConstants.popularMoviesPath)) {
            getPopularMovieResults();
        }
        else if(sortOrder.equalsIgnoreCase(AppConstants.topRated)) {
            getTopRatedMovieResults();
        }
    }

    private void getPopularMovieResults(){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.progressMessage));
        progressDialog.show();

        Call<MovieResultsResponse> movieResultsCall = retorfitAPIInterface.getPopularMovieResults(AppConstants.apiKeyValue);
        movieResultsCall.enqueue(new Callback<MovieResultsResponse>() {
            @Override
            public void onResponse(Call<MovieResultsResponse> call, Response<MovieResultsResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG + "popularMovieGetResponse", response.body().getMovieResults().toString());
                    progressDialog.dismiss();
                    List<MovieResultsResponse.MovieResultsInfo> movieResultsResponseData = response.body().getMovieResults();
                    setMovieRecyclerViewAndDefaultTextViewVisibility(View.VISIBLE, View.GONE);
                    movieAdapter.setMovieResultsResponse(movieResultsResponseData);
                    restoreLayoutState();
                    movieAdapter.notifyDataSetChanged();
                }
                else {
                    progressDialog.dismiss();
                    setMovieRecyclerViewAndDefaultTextViewVisibility(View.GONE, View.VISIBLE);
                    mTextView.setText(getString(R.string.errorString));
                }
            }

            @Override
            public void onFailure(Call<MovieResultsResponse> call, Throwable t) {
                progressDialog.dismiss();
                setMovieRecyclerViewAndDefaultTextViewVisibility(View.GONE, View.VISIBLE);
                mTextView.setText(getString(R.string.networkErrorString));
            }
        });
    }

    private void getTopRatedMovieResults(){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.progressMessage));
        progressDialog.show();

        Call<MovieResultsResponse> movieResultsCall = retorfitAPIInterface.getTopRatingMovieResults(AppConstants.apiKeyValue);
        movieResultsCall.enqueue(new Callback<MovieResultsResponse>() {
            @Override
            public void onResponse(Call<MovieResultsResponse> call, Response<MovieResultsResponse> response) {
                if(response.code() == 200) {
                    Log.d(TAG + " topMoviesGetResponse", response.body().getMovieResults().toString());
                    progressDialog.dismiss();
                    List<MovieResultsResponse.MovieResultsInfo> movieResultsResponseData = response.body().getMovieResults();
                    setMovieRecyclerViewAndDefaultTextViewVisibility(View.VISIBLE, View.GONE);
                    movieAdapter.setMovieResultsResponse(movieResultsResponseData);
                    restoreLayoutState();
                    movieAdapter.notifyDataSetChanged();
                }
                else {
                    progressDialog.dismiss();
                    setMovieRecyclerViewAndDefaultTextViewVisibility(View.GONE, View.VISIBLE);
                    mTextView.setText(getString(R.string.errorString));
                }
            }

            @Override
            public void onFailure(Call<MovieResultsResponse> call, Throwable t) {
                progressDialog.dismiss();
                setMovieRecyclerViewAndDefaultTextViewVisibility(View.GONE, View.VISIBLE);
                mTextView.setText(getString(R.string.networkErrorString));
            }
        });
    }

    private void showMoviesInRecyclerView(){
        int count = AppUtils.calculateNoOfGridColumns(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,count);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter( this);
        movieRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        popularity = menu.findItem(R.id.sortByPopularity);
        topRating = menu.findItem(R.id.sortByRatingAction);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setSortingOptionsVisibility();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sortByPopularity) {
            getPopularMovieResults();
            putStringInSharedPref(AppConstants.sortTypeKey, AppConstants.popularMoviesPath);
        }

        else if(item.getItemId() == R.id.sortByRatingAction) {
            getTopRatedMovieResults();
            putStringInSharedPref(AppConstants.sortTypeKey, AppConstants.topRated);
        }

        else if(item.getItemId() == R.id.favoritesScreen) {
            Intent intent = new Intent(this, FavActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMovieRecyclerViewAndDefaultTextViewVisibility(int recyclerViewVisibility, int textViewVisibility) {
        movieRecyclerView.setVisibility(recyclerViewVisibility);
        mTextView.setVisibility(textViewVisibility);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER, movieRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            layoutManagerSavedstate = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
        }
    }

    private void restoreLayoutState() {
        if (layoutManagerSavedstate != null) {
            movieRecyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedstate);
        }
    }

    private void putStringInSharedPref(String key, String value) {
        synchronized (this.lock) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        }

    }

    private void setSortingOptionsVisibility() {
        String sortOrder = preferences.getString(AppConstants.sortTypeKey, AppConstants.popularMoviesPath);
        if(sortOrder.equalsIgnoreCase(AppConstants.popularMoviesPath)) {
            topRating.setVisible(true);
            popularity.setVisible(false);
        }
        else if (sortOrder.equalsIgnoreCase(AppConstants.topRated)) {
            topRating.setVisible(false);
            popularity.setVisible(true);
        }

        else {
            topRating.setVisible(true);
            popularity.setVisible(false);
        }
    }
}
