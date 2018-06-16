package in.movieapp.com.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by DivyaSethi.
 */

public class DetailActivity extends AppCompatActivity {

    TextView mOriginalTitleText;
    ImageView moviePosterView;
    TextView mUserRatingText;
    TextView mReleaseDateText;
    TextView mOverViewText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String posterPath = getIntent().getStringExtra(AppConstants.posterPathKey);
        String title = getIntent().getStringExtra(AppConstants.titleKey);
        String releaseDate = getIntent().getStringExtra(AppConstants.releaseDateKey);
        String rating = getIntent().getStringExtra(AppConstants.ratingKey);
        String overview = getIntent().getStringExtra(AppConstants.overviewKey);

        mOriginalTitleText = (TextView) findViewById(R.id.originalTitleText);
        moviePosterView = (ImageView) findViewById(R.id.moviePoster);
        mUserRatingText = (TextView) findViewById(R.id.userRatingText);
        mReleaseDateText = (TextView) findViewById(R.id.releaseDateText);
        mOverViewText = (TextView) findViewById(R.id.overViewText);

        if(!Utils.isEmpty(posterPath) && !Utils.isEmpty(title)) {
            mOriginalTitleText.setText(title);
            String url = AppConstants.movieThumbnailBaseURL + posterPath;
            Picasso.with(this).load(url).into(moviePosterView);
            mReleaseDateText.setText(releaseDate);
            mUserRatingText.setText(rating);
            mOverViewText.setText(overview);

        }
        else {
            Toast mToast = Toast.makeText(this, "Oops!!! Something went wrong", Toast.LENGTH_LONG);
            mToast.show();
        }
    }
}
