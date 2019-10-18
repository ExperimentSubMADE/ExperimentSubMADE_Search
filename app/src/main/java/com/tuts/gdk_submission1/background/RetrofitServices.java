package com.tuts.gdk_submission1.background;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitServices {

    private final static String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @SuppressWarnings("unchecked")
    static <S> S createService() {
        return retrofit.create((Class<S>) ContentServices.class);
    }


}
