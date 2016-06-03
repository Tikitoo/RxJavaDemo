package me.tikitoo.demo.rxjavademo.dagger;


import android.content.Context;

import dagger.Component;
import me.tikitoo.demo.rxjavademo.api.GithubService;

@Component(modules = RxModule.class)
public interface RxComponent {
    Context context();
    GithubService githubService();
}
