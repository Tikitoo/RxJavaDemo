package me.tikitoo.demo.rxjavademo.dagger;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class RxApp extends Application {
    private RxComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        mAppComponent = DaggerRxComponent.builder()
                .rxModule(new RxModule(this))
                .build();

    }

    public static RxComponent getAppComponent(Context context) {
        return ((RxApp) context.getApplicationContext()).mAppComponent;
    }
}
