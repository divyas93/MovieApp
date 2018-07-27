package in.movieapp.com.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.movieapp.com.movieapp.AppConstants;
import in.movieapp.com.movieapp.DetailActivity;
import in.movieapp.com.movieapp.R;
import in.movieapp.com.movieapp.database.FavouriteMovieEntity;

/**
 * Created by DivyaSethi on 26/07/18.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteMovieViewHolder> {

    private List<FavouriteMovieEntity> favouriteMovieEntities;
    Context mContext;

    public FavoriteAdapter(List<FavouriteMovieEntity> favouriteMovieEntities, Context context) {
        this.favouriteMovieEntities = favouriteMovieEntities;
        mContext = context;
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int testLayoutId = R.layout.posterview;
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(testLayoutId, viewGroup, false);
        FavoriteAdapter.FavoriteMovieViewHolder movieViewHolder = new FavoriteAdapter.FavoriteMovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteMovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return favouriteMovieEntities.size();
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieImageView;

        public FavoriteMovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieImageView.setOnClickListener(this);

        }

        void bind(int position) {
            String url = AppConstants.movieThumbnailBaseURL + favouriteMovieEntities.get(position).getPosterPath();
            Picasso.with(itemView.getContext()).load(url).into(movieImageView);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(AppConstants.titleKey, favouriteMovieEntities.get(getAdapterPosition()).getMovieTitle());
            intent.putExtra(AppConstants.releaseDateKey, favouriteMovieEntities.get(getAdapterPosition()).getReleaseDate());
            intent.putExtra(AppConstants.overviewKey, favouriteMovieEntities.get(getAdapterPosition()).getOverview());
            intent.putExtra(AppConstants.posterPathKey, favouriteMovieEntities.get(getAdapterPosition()).getPosterPath());
            intent.putExtra(AppConstants.ratingKey, favouriteMovieEntities.get(getAdapterPosition()).getUserRating());
            intent.putExtra(AppConstants.id, favouriteMovieEntities.get(getAdapterPosition()).getMovieId());
            mContext.startActivity(intent);

        }
    }
}
