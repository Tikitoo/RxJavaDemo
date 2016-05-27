package me.tikitoo.demo.rxjavademo.repo;

import java.util.List;

import javax.inject.Inject;

import me.tikitoo.demo.rxjavademo.api.GithubService;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepoPresenterImpl implements RepoPresenter<RepoView> {
    private GithubService mGithubService;
    private RepoView mRepoView;

    @Inject
    public RepoPresenterImpl(GithubService githubService) {
        mGithubService = githubService;
    }

    @Override
    public void setView(RepoView repoView) {
        mRepoView = repoView;
    }

    @Override
    public void loadRepoList() {
        mGithubService.getReposRx(1, 10)
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
