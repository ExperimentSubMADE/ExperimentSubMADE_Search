package com.tuts.gdk_submission1.tvshow.adapter;

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
import com.tuts.gdk_submission1.R;
import com.tuts.gdk_submission1.tvshow.callback.TvShowClickCallback;
import com.tuts.gdk_submission1.tvshow.datamodel.TvShowDataModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private ArrayList<TvShowDataModel> tvShowDataModels;
    private Context context;

    private TvShowClickCallback tvShowClickCallback;

    public TvShowAdapter(ArrayList<TvShowDataModel> tvShowDataModels, Context context, TvShowClickCallback tvShowClickCallback) {
        this.tvShowDataModels = tvShowDataModels;
        this.context = context;
        this.tvShowClickCallback = tvShowClickCallback;
    }

    public void setListTvShow(ArrayList<TvShowDataModel> tvShowList) {
        this.tvShowDataModels = tvShowList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tvshow_item_layout, parent, false);
        return new TvShowViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final TvShowViewHolder holder, int position) {

        Glide.with(context)
                .load(ApiRepository.POSTER_URL + tvShowDataModels.get(position).getTvShowPosterPath())
                .placeholder(R.drawable.poster_placeholder_image)
                .into(holder.imageTvShowItemPhoto);

        holder.txtTvShowItemTitle.setText(tvShowDataModels.get(position).getTvShowName());
        holder.txtTvShowItemReleaseDate.setText(tvShowDataModels.get(position).getTvShowReleaseDate());
        holder.txtTvShowItemOverview.setText(tvShowDataModels.get(position).getTvShowOverview());

        Double countAverage = tvShowDataModels.get(position).getTvShowVoteAverage()*10;
        String txtVoteAverage = new DecimalFormat("##").format(countAverage);
        holder.txtTvShowItemRating.setText(txtVoteAverage + "%");

        holder.bind(tvShowDataModels.get(position));
        holder.txtTvShowGoToDetail.setOnClickListener(view ->
                tvShowClickCallback.onClick(holder.tvShowDataModel));

    }

    @Override
    public int getItemCount() {
        return tvShowDataModels.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder {

        ImageView imageTvShowItemPhoto;
        TextView txtTvShowItemTitle, txtTvShowItemReleaseDate, txtTvShowItemOverview,
                txtTvShowItemRating, txtTvShowGoToDetail;
        TvShowDataModel tvShowDataModel;


        TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            imageTvShowItemPhoto = itemView.findViewById(R.id.imageTvShowItemPhoto);
            txtTvShowItemTitle = itemView.findViewById(R.id.txtTvShowItemTitle);
            txtTvShowItemReleaseDate = itemView.findViewById(R.id.txtTvShowItemReleaseDate);
            txtTvShowItemOverview = itemView.findViewById(R.id.txtTvShowItemOverview);
            txtTvShowItemRating = itemView.findViewById(R.id.txtTvShowItemRating);
            txtTvShowGoToDetail = itemView.findViewById(R.id.txtTvShowGoToDetail);

        }

        void bind(TvShowDataModel tvShowDataModel) {
            this.tvShowDataModel = tvShowDataModel;
        }

    }

}
