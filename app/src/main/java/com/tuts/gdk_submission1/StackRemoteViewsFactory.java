package com.tuts.gdk_submission1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.tuts.gdk_submission1.background.ApiRepository;
import com.tuts.gdk_submission1.favorite.ContentDatabase;
import com.tuts.gdk_submission1.movie.datamodel.MovieDataModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<MovieDataModel> movieList;

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        movieList = ContentDatabase.getInstance(context).movieFavoriteDao().getAllMoviesToWidget();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (movieList != null) {
            return movieList.size();
        }
        else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.favoritemovie_widget_item_layout);

        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(ApiRepository.POSTER_URL + movieList.get(position).getPoster_path())
                    .submit(512, 512)
                    .get();
            remoteViews.setImageViewBitmap(R.id.imageViewWidgetItem, bitmap);
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteMovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.imageViewWidgetItem, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
