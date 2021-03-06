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
import in.movieapp.com.movieapp.POJO.MovieResultsResponse;
import in.movieapp.com.movieapp.R;

/**
 * Created by DivyaSethi.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieResultsResponse.MovieResultsInfo> movieResultsResponse;
    Context mContext;

    public MovieAdapter (Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int testLayoutId = R.layout.posterview;
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(testLayoutId, viewGroup, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(position);
    }


    public void setMovieResultsResponse(List<MovieResultsResponse.MovieResultsInfo> movieResultsResponse) {
        this.movieResultsResponse = movieResultsResponse;
    }

    @Override
    public int getItemCount() {
        if(movieResultsResponse == null) {
            return 0;
        }
        else {
            return movieResultsResponse.size();
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieImageView.setOnClickListener(this);

        }

        void bind(int position) {
            String url = AppConstants.movieThumbnailBaseURL + movieResultsResponse.get(position).getPosterPathThumbnail();
            Picasso.with(itemView.getContext()).load(url).into(movieImageView);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(AppConstants.titleKey, movieResultsResponse.get(getAdapterPosition()).getOriginalTitle());
            intent.putExtra(AppConstants.releaseDateKey,movieResultsResponse.get(getAdapterPosition()).getReleaseDate());
            intent.putExtra(AppConstants.overviewKey, movieResultsResponse.get(getAdapterPosition()).getOverview());
            intent.putExtra(AppConstants.posterPathKey,movieResultsResponse.get(getAdapterPosition()).getPosterPathThumbnail());
            intent.putExtra(AppConstants.ratingKey,movieResultsResponse.get(getAdapterPosition()).getUserRating());
            intent.putExtra(AppConstants.id, movieResultsResponse.get(getAdapterPosition()).getId());
            mContext.startActivity(intent);

        }
    }
}
