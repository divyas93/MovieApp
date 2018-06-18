package in.movieapp.com.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import in.movieapp.com.movieapp.Network.RetorfitAPIInterface;
import in.movieapp.com.movieapp.Network.RetrofitClient;
import in.movieapp.com.movieapp.POJO.MovieResultsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DivyaSethi.
 */

public class MainActivity extends AppCompatActivity{

    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private MenuItem topRating;
    private MenuItem popularity;

    private Retrofit retrofitClient;
    private RetorfitAPIInterface retorfitAPIInterface;
    private List<MovieResultsResponse.MovieResultsInfo> movieResultsResponseData;
    private boolean showTopRatingMenuOption = true;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieRecyclerView = (RecyclerView) findViewById(R.id.movieRecyclerView);
        retrofitClient = getRetrofitAPIClient();
        retorfitAPIInterface = getRetorfitAPIInterface();
        getPopularMovieResults();
    }

    private Retrofit getRetrofitAPIClient() {
        return RetrofitClient.getRetrofitAPIClient();
    }

    private RetorfitAPIInterface getRetorfitAPIInterface() {
        return retrofitClient.create(RetorfitAPIInterface.class);
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
                Log.d("responseGET", response.body().getMovieResults().toString());
                progressDialog.dismiss();
                movieResultsResponseData = response.body().getMovieResults();
                showMoviesInRecyclerView();
            }

            @Override
            public void onFailure(Call<MovieResultsResponse> call, Throwable t) {
                progressDialog.dismiss();
                showErrorScreen();
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
                Log.d("responseGET", response.body().getMovieResults().toString());
                progressDialog.dismiss();
                movieResultsResponseData = response.body().getMovieResults();
                showMoviesInRecyclerView();
            }

            @Override
            public void onFailure(Call<MovieResultsResponse> call, Throwable t) {
                progressDialog.dismiss();
                showErrorScreen();
            }
        });
    }

    private void showErrorScreen(){
        if(toast!=null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, getString(R.string.errorString), Toast.LENGTH_LONG);
        toast.show();
    }

    private void showMoviesInRecyclerView(){
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

        else {
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

        return super.onOptionsItemSelected(item);
    }
}
