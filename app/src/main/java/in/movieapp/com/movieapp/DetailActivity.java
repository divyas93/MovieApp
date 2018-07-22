package in.movieapp.com.movieapp;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.movieapp.com.movieapp.Adapter.TrailerAdapter;
import in.movieapp.com.movieapp.Network.RetorfitAPIInterface;
import in.movieapp.com.movieapp.Network.RetrofitClient;
import in.movieapp.com.movieapp.POJO.TrailerResultResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by DivyaSethi.
 */

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerClickListener{

    TextView mOriginalTitleText;
    ImageView moviePosterView;
    TextView mUserRatingText;
    TextView mReleaseDateText;
    TextView mOverViewText;
    RecyclerView mTrailerRecyclerViewer;
    TextView mErrortextView;
    List<TrailerResultResponse.TrailerResultsInfo> trailerResultsResponseData;
    TrailerAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mOriginalTitleText = (TextView) findViewById(R.id.originalTitleText);
        moviePosterView = (ImageView) findViewById(R.id.moviePoster);
        mUserRatingText = (TextView) findViewById(R.id.userRatingText);
        mReleaseDateText = (TextView) findViewById(R.id.releaseDateText);
        mOverViewText = (TextView) findViewById(R.id.overViewText);
        mTrailerRecyclerViewer = (RecyclerView) findViewById(R.id.trailerRecyclerViewer);
        mErrortextView = (TextView) findViewById(R.id.trailerErrorText);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(AppConstants.posterPathKey)) {
                String posterPath = intent.getStringExtra(AppConstants.posterPathKey);
                String url = AppConstants.movieThumbnailBaseURL + posterPath;
                Picasso.with(this).load(url).into(moviePosterView);
            }

            if (intent.hasExtra(AppConstants.titleKey)) {
                mOriginalTitleText.setText(intent.getStringExtra(AppConstants.titleKey));
            }

            if (intent.hasExtra(AppConstants.releaseDateKey)) {
                mReleaseDateText.setText(intent.getStringExtra(AppConstants.releaseDateKey));
            }

            if (intent.hasExtra(AppConstants.ratingKey)) {
                mUserRatingText.setText(intent.getStringExtra(AppConstants.ratingKey));
            }

            if (intent.hasExtra(AppConstants.overviewKey)) {
                mOverViewText.setText(intent.getStringExtra(AppConstants.overviewKey));
            }

            if (intent.hasExtra(AppConstants.id)) {
                int id = intent.getIntExtra(AppConstants.id, -1);
                getTrailerData(id);
            }

        } else {
            Toast mToast = Toast.makeText(this, getString(R.string.errorString), Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    private void loadTrailerData(boolean value) {
        if(value) {
            mErrortextView.setVisibility(View.GONE);
            mTrailerRecyclerViewer.setVisibility(View.VISIBLE);
        }
        else {
            mErrortextView.setVisibility(View.VISIBLE);
            mTrailerRecyclerViewer.setVisibility(View.GONE);
        }
    }

    private void getTrailerData(int id) {
        //http://api.themoviedb.org/3/movie/351286/videos?api_key=9a28e4718476aa866b00834a65ae57b7
        final ProgressDialog progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url = AppConstants.movieBaseURL + id + "/";
        Retrofit client = RetrofitClient.getRetrofitAPIClient(url);
        RetorfitAPIInterface apiInterface = client.create(RetorfitAPIInterface.class);
        Call<TrailerResultResponse> trailerResults = apiInterface.getTrailerResults(AppConstants.apiKeyValue);
        trailerResults.enqueue(new Callback<TrailerResultResponse>() {
            @Override
            public void onResponse(Call<TrailerResultResponse> call, Response<TrailerResultResponse> response) {
                Log.d("responseGET", response.body().getTrailerResults().toString());
                progressDialog.dismiss();
                trailerResultsResponseData = response.body().getTrailerResults();
                Toast.makeText(DetailActivity.this, "divya success", Toast.LENGTH_LONG).show();
                loadTrailerData(true);
                showTrailerRecyclerView();
            }

            @Override
            public void onFailure(Call<TrailerResultResponse> call, Throwable t) {
                progressDialog.dismiss();
                loadTrailerData(false);
                Toast.makeText(DetailActivity.this, "fail", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showTrailerRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mTrailerRecyclerViewer.setLayoutManager(layoutManager);
        trailerAdapter = new TrailerAdapter(this, trailerResultsResponseData, this);
        mTrailerRecyclerViewer.setAdapter(trailerAdapter);
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

    @Override
    public void onClick(int  postion) {
        watchYoutubeVideo(postion);
    }
}