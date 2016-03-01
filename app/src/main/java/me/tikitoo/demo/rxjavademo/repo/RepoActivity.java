package me.tikitoo.demo.rxjavademo.repo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.tikitoo.demo.rxjavademo.R;
import me.tikitoo.demo.rxjavademo.RetrofitService;
import me.tikitoo.demo.rxjavademo.api.GithubService;

public class RepoActivity extends AppCompatActivity implements RepoView {

    @Bind(R.id.progressbar)
    ProgressBar mProgressbar;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    RepoPresenter mRepoPresenter;
    private RepoListAdapter mAdapter;
    private GithubService mGithubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        ButterKnife.bind(this);
        init();
        setupReyclerView();
        createPresenter();

    }

    private void setupReyclerView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mAdapter);

    }

    private void init() {
        mGithubService = RetrofitService.getInstance().create();

        mAdapter = new RepoListAdapter();
    }

    private void createPresenter() {
        mRepoPresenter = new RepoPresenterImpl(mGithubService, this);
        mRepoPresenter.loadRepoList();
    }


    @Override
    public void showRepos(List<Repo> repos) {
        mAdapter.setRepos(repos);
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
        Toast.makeText(this, "msg: " + message, Toast.LENGTH_SHORT).show();
    }


}
