package me.tikitoo.demo.rxjavademo.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.tikitoo.demo.rxjavademo.RetrofitService;
import me.tikitoo.demo.rxjavademo.api.GithubService;

@Module
public class RxModule {
    private final Application mApplication;

    public RxModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Context provideContext() {
        return mApplication;
    }

    @Provides
    public GithubService provideGithubService() {
        return RetrofitService.getInstance().create();
    }
}
