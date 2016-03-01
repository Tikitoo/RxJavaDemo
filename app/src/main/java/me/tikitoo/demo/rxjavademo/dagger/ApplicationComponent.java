package me.tikitoo.demo.rxjavademo.dagger;


import android.content.Context;

import dagger.Component;
import me.tikitoo.demo.rxjavademo.api.GithubService;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
    GithubService githubService();
}
