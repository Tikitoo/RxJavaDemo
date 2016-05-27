package me.tikitoo.demo.rxjavademo.dagger;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class AndroidApplication extends Application {
    private ApplicationComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}
