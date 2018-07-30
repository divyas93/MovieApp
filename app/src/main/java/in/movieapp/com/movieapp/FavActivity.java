package in.movieapp.com.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.movieapp.com.movieapp.Adapter.FavoriteAdapter;
import in.movieapp.com.movieapp.database.FavouriteMovieEntity;

public class FavActivity extends AppCompatActivity {
    @BindView(R.id.movieRecyclerView) RecyclerView movieRecyclerView;
    @BindView(R.id.favoriteMovie) TextView mTextView;
    private FavoriteAdapter favoriteAdapter;
    private static final String TAG = FavActivity.class.getSimpleName();
    private Parcelable layoutManagerSavedstate;
    private final String SAVED_LAYOUT_MANAGER = "layoutManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showFavMoviesInRecyclerView();
        loadFavMoviesData();
    }

    private void showFavMoviesInRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        movieRecyclerView.setLayoutManager(layoutManager);
        favoriteAdapter = new FavoriteAdapter( this);
        movieRecyclerView.setAdapter(favoriteAdapter);
    }

    private void loadFavMoviesData(){
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
                    favoriteAdapter.setFavouriteMovieEntities(favouriteMovieEntities);
                    restoreLayoutState();
                    favoriteAdapter.notifyDataSetChanged();
                }
            }
        });
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

}
