package me.tikitoo.demo.rxjavademo.db;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.sqlbrite.BriteDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.tikitoo.demo.rxjavademo.R;
import me.tikitoo.demo.rxjavademo.utils.DbUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.support.v4.view.MenuItemCompat.*;

public class ListsFragment extends Fragment {


    private Subscription mSubscription;

    interface Listener {
        void onNewListClicked();
        void onListClicked(long id);
    }
    @Bind(android.R.id.empty)
    ImageView mEmpty;
    @Bind(android.R.id.list)

    ListView mListView;

    private BriteDatabase mDb;

    private ListsAdapter mAdapter;
    private Listener mListener;

    public static ListsFragment newInstance() {
        Bundle args = new Bundle();
        ListsFragment fragment = new ListsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);

        mDb = DbUtils.createDb(activity);

        mAdapter = new ListsAdapter(activity);
        mListener = ((Listener) activity);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(R.string.new_list)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mListener.onNewListClicked();
                        return true;
                    }
                });

        MenuItemCompat.setShowAsAction(item, SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lists, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mListView.setEmptyView(mEmpty);
        mListView.setAdapter(mAdapter);
        listClicked();
    }

    void listClicked() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        BriteDatabase.Transaction transaction = null;
                        try {
                            transaction = mDb.newTransaction();
                            mDb.delete(UserList.TABLE, "_id = ?",
                                    String.valueOf(mAdapter.getItem(position).id()));
                            transaction.markSuccessful();
                        } finally {
                            transaction.end();

                        }
                    }
                });

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        mSubscription = mDb.createQuery(UserList.TABLE, UserList.QUERY_SELECT)
                .mapToList(UserList.MAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSubscription.unsubscribe();
    }

}
