package me.tikitoo.demo.rxjavademo.repo;

import java.util.List;

public interface RepoView extends BaseView {
    void showRepos(List<Repo> repos);
    void hideLoading();
    void showLoading();
}
