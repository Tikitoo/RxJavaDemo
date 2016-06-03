package me.tikitoo.demo.rxjavademo.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RxDbActivity extends AppCompatActivity implements ListsFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rx_db);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, ListsFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onNewListClicked() {
        NewListFragment.newInstance().show(getSupportFragmentManager(), "new-list");
    }

    @Override
    public void onListClicked(long id) {

    }
}
