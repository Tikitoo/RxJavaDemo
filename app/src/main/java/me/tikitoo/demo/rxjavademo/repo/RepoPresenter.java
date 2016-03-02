package me.tikitoo.demo.rxjavademo.repo;

public interface RepoPresenter<T> {
    void setView(T t);
    void loadRepoList();
}
