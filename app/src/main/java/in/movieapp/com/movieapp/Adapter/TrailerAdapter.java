package in.movieapp.com.movieapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.movieapp.com.movieapp.AppConstants;
import in.movieapp.com.movieapp.POJO.TrailerResultResponse;
import in.movieapp.com.movieapp.R;

/**
 * Created by DivyaSethi on 17/07/18.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    List<TrailerResultResponse.TrailerResultsInfo> trailerResult = new ArrayList<>();
    Context mContext;
    TrailerClickListener listener;


    public TrailerAdapter(Context context, List<TrailerResultResponse.TrailerResultsInfo> trailerResult, TrailerClickListener listener) {
        mContext = context;
        this.trailerResult = trailerResult;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int trailerLayoutId = R.layout.trailer_view;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(trailerLayoutId, parent, false);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);
        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return trailerResult.size();
    }

    public interface TrailerClickListener {
        void onClick(int postion);
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView trailerImage;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            trailerImage = (ImageView) itemView.findViewById(R.id.trailerImage);
            trailerImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(getAdapterPosition());
        }

        void bind(int position) {
            String url = AppConstants.youtubeTrailerImageBaseUrl + trailerResult.get(position).getKey() + "/0.jpg";
            Picasso.with(itemView.getContext()).load(url).into(trailerImage);
        }

    }

}
