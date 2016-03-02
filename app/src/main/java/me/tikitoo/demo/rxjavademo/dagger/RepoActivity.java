package me.tikitoo.demo.rxjavademo.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.tikitoo.demo.rxjavademo.R;
import me.tikitoo.demo.rxjavademo.repo.Repo;
import me.tikitoo.demo.rxjavademo.repo.RepoListAdapter;
import me.tikitoo.demo.rxjavademo.repo.RepoPresenter;
import me.tikitoo.demo.rxjavademo.repo.RepoView;

public class RepoActivity extends AppCompatActivity implements RepoView {
    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private RepoComponent mRepoComponent;
    private RepoListAdapter mRepoListAdapter;
    private RepoPresenter<RepoView> mPresenter;
    private static final String TAG = RepoActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        ButterKnife.bind(this);

        mRepoComponent = DaggerRepoComponent.builder()
                .applicationComponent(((AndroidApplication)getApplication()).getAppComponent())
                .build();

        createAdapter();
        setupReyclerView();
        createPresenter();

    }

    private void setupReyclerView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mRepoListAdapter);
    }

    private void createAdapter() {
        mRepoListAdapter = mRepoComponent.adater();
    }

    private void createPresenter() {
        mPresenter = mRepoComponent.presenter();
        mPresenter.setView(this);
        mPresenter.loadRepoList();
    }

    @Override
    public void showRepos(List<Repo> repos) {
        mRepoListAdapter.setRepos(repos);
    }

    @Override
    public void hideLoading() {
        mProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Log.d(TAG, "msg: " + message);
    }
}
