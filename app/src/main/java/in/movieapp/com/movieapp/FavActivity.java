package in.movieapp.com.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import in.movieapp.com.movieapp.Adapter.FavoriteAdapter;
import in.movieapp.com.movieapp.database.FavouriteMovieEntity;

public class FavActivity extends AppCompatActivity {
    private RecyclerView movieRecyclerView;
    private TextView mTextView;
    private FavoriteAdapter favoriteAdapter;
    private static final String TAG = FavActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieRecyclerView = (RecyclerView) findViewById(R.id.movieRecyclerView);
        mTextView= (TextView) findViewById(R.id.favoriteMovie);

        setupFavMovies();

    }

    private void setupFavMovies(){
        AppViewModel viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.fetchAllFavMovies().observe(this, new Observer<List<FavouriteMovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteMovieEntity> favouriteMovieEntities) {
                Log.d(TAG, "Receiving database update from LiveData");
                if(favouriteMovieEntities.size() == 0) {
                    setMovieRecyclerViewAndDefaultTextViewVisibility(View.GONE, View.VISIBLE);
                    mTextView.setText(getString(R.string.noFavMovieText));
                }
                else {
                    setMovieRecyclerViewAndDefaultTextViewVisibility(View.VISIBLE, View.GONE);
                    showFavMoviesInRecyclerView(favouriteMovieEntities);
                }
            }
        });
    }

    private void showFavMoviesInRecyclerView(List<FavouriteMovieEntity> favouriteMovieEntities){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        movieRecyclerView.setLayoutManager(layoutManager);
        favoriteAdapter = new FavoriteAdapter(favouriteMovieEntities, this);
        movieRecyclerView.setAdapter(favoriteAdapter);
    }

    private void setMovieRecyclerViewAndDefaultTextViewVisibility(int recyclerViewVisibility, int textViewVisibility) {
        movieRecyclerView.setVisibility(recyclerViewVisibility);
        mTextView.setVisibility(textViewVisibility);
    }

}
