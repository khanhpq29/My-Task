package ht.pq.khanh.api

import ht.pq.khanh.multitask.BuildConfig
import ht.pq.khanh.util.Common
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by khanhpq on 9/28/17.
 */
object ApiBuilder {
    lateinit var sRetrofit: Retrofit

    fun <T> createApiService(clazz: Class<T>): T {
        return retrofitInstance.create(clazz)
    }

    private val retrofitInstance: Retrofit
        get() {
            synchronized(ApiBuilder::class) {
                sRetrofit = init()
            }
            return sRetrofit
        }

    private val TIME_OUT = 15

    private fun init(): Retrofit {
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            httpBuilder.addInterceptor(logger)
        }

        return Retrofit.Builder()
                .client(httpBuilder.build())
                .baseUrl(Common.URL_WEATHER_BASE)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}