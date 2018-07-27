package in.movieapp.com.movieapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.movieapp.com.movieapp.POJO.ReviewResultsResponse;
import in.movieapp.com.movieapp.R;

/**
 * Created by DivyaSethi on 23/07/18.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    List<ReviewResultsResponse.ReviewResultsInfo> reviewResultsInfoList;
    Context mContext;

    public ReviewAdapter(List<ReviewResultsResponse.ReviewResultsInfo> reviewResultsInfoList, Context mContext) {
        this.mContext = mContext;
        this.reviewResultsInfoList = reviewResultsInfoList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int reviewLayoutId = R.layout.review_view;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(reviewLayoutId, parent, false);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        String text = reviewResultsInfoList.get(position).getContent();
        holder.author.setText(reviewResultsInfoList.get(position).getAuthor() + ":");
        holder.authorReview.setText(text);

    }

    @Override
    public int getItemCount() {
        return reviewResultsInfoList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView authorReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.reviewAuthor);
            authorReview = (TextView) itemView.findViewById(R.id.reviewText);
        }

    }
}
