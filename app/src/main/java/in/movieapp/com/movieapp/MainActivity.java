package in.movieapp.com.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

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
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private MenuItem topRating;
    private MenuItem popularity;

    private RetorfitAPIInterface retorfitAPIInterface;
    private boolean showTopRatingMenuOption = true;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieRecyclerView = (RecyclerView) findViewById(R.id.movieRecyclerView);
        mTextView= (TextView) findViewById(R.id.favoriteMovie);
        retorfitAPIInterface = new NetworkUtils().getRetorfitAPIInterface(AppConstants.movieBaseURL);
        getPopularMovieResults();
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
                    showMoviesInRecyclerView(movieResultsResponseData);
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
                    showMoviesInRecyclerView(movieResultsResponseData);
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

    private void showMoviesInRecyclerView(List<MovieResultsResponse.MovieResultsInfo> movieResultsResponseData){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(movieResultsResponseData, this);
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
        if (showTopRatingMenuOption) {
            topRating.setVisible(true);
            popularity.setVisible(false);
        }

        else if (!showTopRatingMenuOption) {
            topRating.setVisible(false);
            popularity.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sortByPopularity) {
            getPopularMovieResults();
            showTopRatingMenuOption = true;
        }

        else if(item.getItemId() == R.id.sortByRatingAction) {
            getTopRatedMovieResults();
            showTopRatingMenuOption = false;
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
}
