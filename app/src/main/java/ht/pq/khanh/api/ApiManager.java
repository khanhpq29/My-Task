package ht.pq.khanh.api;

import java.util.concurrent.TimeUnit;

import ht.pq.khanh.multitask.BuildConfig;
import ht.pq.khanh.util.Common;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khanhpq on 9/28/17.
 */

public class ApiManager {
    private static Retrofit sRetrofit;

    public static <T> T createApiService(Class<T> clazz) {
        return getRetrofitInstance().create(clazz);
    }

    private static Retrofit getRetrofitInstance() {
        if (sRetrofit == null) {
            synchronized (ApiManager.class) {
                if (sRetrofit == null) {
                    sRetrofit = init();
                }
            }
        }
        return sRetrofit;
    }

    private static final int TIME_OUT = 15;

    private static Retrofit init() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(logger);
        }

        return new Retrofit.Builder()
                .client(httpBuilder.build())
                .baseUrl(Common.INSTANCE.getURL_WEATHER_BASE())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }
}
