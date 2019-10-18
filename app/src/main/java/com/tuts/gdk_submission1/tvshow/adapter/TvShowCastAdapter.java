package com.tuts.gdk_submission1.tvshow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuts.gdk_submission1.R;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowCastDataModel;

import java.util.ArrayList;

public class TvShowCastAdapter extends RecyclerView.Adapter<TvShowCastAdapter.TvShowCastViewHolder> {

    private ArrayList<TvShowCastDataModel> tvShowCastDataModels;
    private Context context;

    public TvShowCastAdapter(ArrayList<TvShowCastDataModel> tvShowCastDataModels, Context context) {
        this.tvShowCastDataModels = tvShowCastDataModels;
        this.context = context;
    }

    @NonNull
    @Override
    public TvShowCastAdapter.TvShowCastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contentcast_item_layout, parent, false);
        return new TvShowCastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowCastAdapter.TvShowCastViewHolder holder, int position) {

        Glide.with(context)
                .load(tvShowCastDataModels.get(position).getTvShowCastProfilePath())
                .placeholder(R.drawable.cast_placeholder_image)
                .into(holder.imageContentCastPhoto);

        holder.txtContentCastRealName.setText(tvShowCastDataModels.get(position).getTvShowCastRealName());
        holder.txtContentCastCharacter.setText(tvShowCastDataModels.get(position).getTvShowCastCharacter());

    }

    @Override
    public int getItemCount() {
        return tvShowCastDataModels.size();
    }

    class TvShowCastViewHolder extends RecyclerView.ViewHolder {

        ImageView imageContentCastPhoto;
        TextView txtContentCastRealName, txtContentCastCharacter;

        TvShowCastViewHolder(@NonNull View itemView) {
            super(itemView);

            imageContentCastPhoto = itemView.findViewById(R.id.imageContentCastPhoto);
            txtContentCastRealName = itemView.findViewById(R.id.txtContentCastRealName);
            txtContentCastCharacter = itemView.findViewById(R.id.txtContentCastCharacter);

        }
    }
}
