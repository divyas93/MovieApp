package in.movieapp.com.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.movieapp.com.movieapp.POJO.MovieResultsResponse;

/**
 * Created by DivyaSethi.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int numberOfMovies;
    private List<MovieResultsResponse.MovieResultsInfo> movieResultsResponse;
    private MovieThumbnailClickListener clickListener;

    public MovieAdapter(int numberOfMovies, List<MovieResultsResponse.MovieResultsInfo> movieResultsResponse, MovieThumbnailClickListener clickListener) {
        this.numberOfMovies = numberOfMovies;
        this.movieResultsResponse = movieResultsResponse;
        this.clickListener = clickListener;
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

    @Override
    public int getItemCount() {
        return numberOfMovies;
    }

    public interface MovieThumbnailClickListener {
        void movieThumbnailClick(String posterPath, String title, String overView, String releaseDate, String rating);
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
            clickListener.movieThumbnailClick(movieResultsResponse.get(getAdapterPosition()).getPosterPathThumbnail(), movieResultsResponse.get(getAdapterPosition()).getOriginalTitle()
            , movieResultsResponse.get(getAdapterPosition()).getOverview(), movieResultsResponse.get(getAdapterPosition()).getReleaseDate(), movieResultsResponse.get(getAdapterPosition()).getUserRating().toString());
        }
    }
}
