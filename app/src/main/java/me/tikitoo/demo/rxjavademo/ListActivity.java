package me.tikitoo.demo.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.tikitoo.demo.rxjavademo.api.GithubService;
import me.tikitoo.demo.rxjavademo.model.Contributor;
import me.tikitoo.demo.rxjavademo.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ListActivity extends AppCompatActivity {
    TextView mText1;
    ListView mListView;
    private GithubService mGithubService;
    private ProgressBar mProgressBar;
    private ArrayAdapter<String> mAdapter;
    private List<String> mList;
    private CompositeSubscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();

//        getData();
//        getDataObservable();
//        getContributors();
        getContributorsObservable();
    }

    private void init() {
        mSubscription = new CompositeSubscription();
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);

        mSubscription = new CompositeSubscription();
        mGithubService = RetrofitService.getInstance().create();

    }

    /**
     * get user by retrofit 2
     * <p/>
     * see{@link #getDataObservable()}
     */
    private void getData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mText1.setVisibility(View.VISIBLE);
        Call<User> userCall = mGithubService.getUser("tikitoo");
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
//                if (user == null) return;
                setTextViewValue(mText1, user);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    /**
     * get user by retrofit 2 + rxjava
     * <p/>
     * see{@link #getData()}
     */
    private void getDataObservable() {
        mProgressBar.setVisibility(View.VISIBLE);
        mText1.setVisibility(View.VISIBLE);
        Observable<User> userObservable = mGithubService.getUserObservable("tikitoo");
        userObservable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mProgressBar.setVisibility(View.VISIBLE);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onNext(User user) {
                        if (user == null) return;
                        setTextViewValue(mText1, user);
                    }

                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void getContributors() {
        mProgressBar.setVisibility(View.VISIBLE);
        final Call<List<Contributor>> contributorCall = mGithubService.getContributors("square", "retrofit");
        contributorCall.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributorList = response.body();
                if (contributorList.isEmpty()) return;
                listToStringList(contributorList);
//                mAdapter.addAll(mList);
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }

    private void getContributorsObservable() {
        Observable<List<Contributor>> listObservable = mGithubService.getContributorsObservable("square", "retrofit");
        Subscription subscription = listObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new Observer<List<Contributor>>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        listToStringList(contributors);
                        mAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);

                    }
                });
        mSubscription.add(subscription);
    }

    private void listToStringList(List<Contributor> contributorList) {
        for (Contributor contributor : contributorList) {
            mList.add(contributor.login);
        }
    }

    private void setTextViewValue(TextView textView, User user) {
        textView.setText("name: " + user.name + "\n"
                + "blog: " + user.blog + "\n"
                + "email: " + user.email + "\n"
                + "location: " + user.location + "\n"
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(this, me.tikitoo.demo.rxjavademo.repo.RepoActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mText1 = (TextView) findViewById(android.R.id.text1);
        mListView = (ListView) findViewById(android.R.id.list);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
    }
}
