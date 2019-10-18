package com.tuts.gdk_submission1.movie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.movie.callback.MovieClickCallback;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;
import com.tuts.gdk_submission1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<MovieDataModel> movieDataModels;
    private Context context;

    private MovieClickCallback movieClickCallback;

    public MovieAdapter(ArrayList<MovieDataModel> movieDataModels, Context context, MovieClickCallback movieClickCallback) {
        this.movieDataModels = movieDataModels;
        this.context = context;
        this.movieClickCallback = movieClickCallback;
    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setListMovie(ArrayList<MovieDataModel> movieList) {
        this.movieDataModels = movieList;
        notifyDataSetChanged();
    }

    public void clearData() {
        movieDataModels.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieViewHolder holder, int position) {

        Glide.with(context)
                .load(ApiRepository.POSTER_URL + movieDataModels.get(position).getPoster_path())
                .placeholder(R.drawable.poster_placeholder_image)
                .into(holder.imageMovieItemPhoto);

        holder.txtMovieItemTitle.setText(movieDataModels.get(position).getTitle());
        holder.txtMovieItemReleaseDate.setText(movieDataModels.get(position).getRelease_date());
        holder.txtMovieItemOverview.setText(movieDataModels.get(position).getOverview());

        Double countAverage = movieDataModels.get(position).getVote_average()*10;
        String txtVoteAverage = new DecimalFormat("##").format(countAverage);
        holder.txtMovieItemRating.setText(txtVoteAverage + "%");

        holder.bind(movieDataModels.get(position));
        holder.txtMovieGoToDetail.setOnClickListener(view ->
                movieClickCallback.onClick(holder.movieDataModel));

    }

    @Override
    public int getItemCount() {
        return movieDataModels.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imageMovieItemPhoto;
        TextView txtMovieItemTitle, txtMovieItemReleaseDate, txtMovieItemOverview,
                txtMovieItemRating, txtMovieGoToDetail;
        MovieDataModel movieDataModel;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imageMovieItemPhoto = itemView.findViewById(R.id.imageMovieItemPhoto);
            txtMovieItemTitle = itemView.findViewById(R.id.txtMovieItemTitle);
            txtMovieItemReleaseDate = itemView.findViewById(R.id.txtMovieItemReleaseDate);
            txtMovieItemOverview = itemView.findViewById(R.id.txtMovieItemOverview);
            txtMovieItemRating = itemView.findViewById(R.id.txtMovieItemRating);
            txtMovieGoToDetail = itemView.findViewById(R.id.txtMovieGoToDetail);

        }

        void bind (MovieDataModel movieDataModel) {
            this.movieDataModel = movieDataModel;
        }

    }

}
