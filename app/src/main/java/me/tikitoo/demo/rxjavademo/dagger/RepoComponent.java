package me.tikitoo.demo.rxjavademo.dagger;

import dagger.Component;
import me.tikitoo.demo.rxjavademo.repo.RepoListAdapter;
import me.tikitoo.demo.rxjavademo.repo.RepoPresenterImpl;

@Component(dependencies = RxComponent.class)
public interface RepoComponent {
    RepoPresenterImpl presenter();
    RepoListAdapter adater();
}
