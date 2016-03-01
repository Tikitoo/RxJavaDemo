package me.tikitoo.demo.rxjavademo.repo;

import java.util.List;

import me.tikitoo.demo.rxjavademo.api.GithubService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepoPresenterImpl implements RepoPresenter {
    private GithubService mGithubService;
    private RepoView mRepoView;

    public RepoPresenterImpl(GithubService githubService, RepoView repoView) {
        mGithubService = githubService;
        mRepoView = repoView;
    }

    @Override
    public void loadRepoList() {
        mGithubService.getReposRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repo>>() {
                    @Override
                    public void onNext(List<Repo> repos) {
                        mRepoView.showRepos(repos);
                    }

                    @Override
                    public void onCompleted() {
                        mRepoView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRepoView.showMessage(e.getMessage());
                        mRepoView.hideLoading();
                    }
                });
    }
}
