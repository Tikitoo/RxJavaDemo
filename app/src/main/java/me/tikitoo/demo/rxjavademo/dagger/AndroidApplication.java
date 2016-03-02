package me.tikitoo.demo.rxjavademo.dagger;

import android.app.Application;

public class AndroidApplication extends Application {
    private ApplicationComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
