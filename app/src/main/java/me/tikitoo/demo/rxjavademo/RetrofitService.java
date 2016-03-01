package me.tikitoo.demo.rxjavademo;

import me.tikitoo.demo.rxjavademo.api.GithubService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String BASE_URL = "https://api.github.com";

    private static RetrofitService instance = null;

    public RetrofitService() {
    }

    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }

    private static Retrofit retrofit = null;

    // reference https://github.com/square/okhttp/issues/2206
    public static HttpLoggingInterceptor createLogger() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BASIC
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }
    public static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(createLogger())
                .build();

    }
    public static GithubService create() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();
//        return retrofit;
        return retrofit.create(GithubService.class);
    }


}
