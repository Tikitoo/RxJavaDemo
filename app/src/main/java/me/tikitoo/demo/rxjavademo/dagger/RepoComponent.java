package me.tikitoo.demo.rxjavademo.dagger;

import dagger.Component;
import me.tikitoo.demo.rxjavademo.repo.RepoListAdapter;
import me.tikitoo.demo.rxjavademo.repo.RepoPresenterImpl;

@Component(dependencies = ApplicationComponent.class)
public interface RepoComponent {
    RepoPresenterImpl presenter();
    RepoListAdapter adater();
}
