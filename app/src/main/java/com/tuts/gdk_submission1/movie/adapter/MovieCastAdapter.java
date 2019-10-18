package com.tuts.gdk_submission1.movie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuts.gdk_submission1.movie.datamodel.MovieCastDataModel;
import com.tuts.gdk_submission1.R;

import java.util.ArrayList;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.MovieCastViewHolder> {

    private ArrayList<MovieCastDataModel> movieCastDataModels;
    private Context context;

    public MovieCastAdapter(ArrayList<MovieCastDataModel> movieCastDataModels, Context context) {
        this.movieCastDataModels = movieCastDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieCastAdapter.MovieCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contentcast_item_layout, parent, false);
        return new MovieCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCastAdapter.MovieCastViewHolder holder, int position) {

        Glide.with(context)
                .load(movieCastDataModels.get(position).getMovieCastProfilePath())
                .placeholder(R.drawable.cast_placeholder_image)
                .into(holder.imageContentCastPhoto);

        holder.txtContentCastRealName.setText(movieCastDataModels.get(position).getMovieCastRealName());
        holder.txtContentCastCharacter.setText(movieCastDataModels.get(position).getMovieCastCharacter());

    }

    @Override
    public int getItemCount() {
        return movieCastDataModels.size();
    }

    class MovieCastViewHolder extends RecyclerView.ViewHolder {

        ImageView imageContentCastPhoto;
        TextView txtContentCastRealName, txtContentCastCharacter;

        MovieCastViewHolder(@NonNull View itemView) {
            super(itemView);

            imageContentCastPhoto = itemView.findViewById(R.id.imageContentCastPhoto);
            txtContentCastRealName = itemView.findViewById(R.id.txtContentCastRealName);
            txtContentCastCharacter = itemView.findViewById(R.id.txtContentCastCharacter);

        }
    }
}
