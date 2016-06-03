package me.tikitoo.demo.rxjavademo.db;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.squareup.sqlbrite.BriteDatabase;

import butterknife.ButterKnife;
import me.tikitoo.demo.rxjavademo.R;
import me.tikitoo.demo.rxjavademo.utils.DbUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class NewListFragment extends DialogFragment {
    public static NewListFragment newInstance() {
        NewListFragment fragment = new NewListFragment();
        return fragment;
    }

    private final PublishSubject<String> createClicked = PublishSubject.create();

    private BriteDatabase mDb;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mDb = new DbUtils().createDb(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.new_list, null);
        EditText mInput = ButterKnife.findById(view, android.R.id.input);

        Observable.combineLatest(createClicked, RxTextView.textChanges(mInput),
                new Func2<String, CharSequence, String>() {
                    @Override
                    public String call(String s, CharSequence text) {
                        return text.toString();
                    }
                })
        .observeOn(Schedulers.io())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String name) {
                mDb.insert(UserList.TABLE,
                        new UserList.Builder().name(name).info(name + "haha").build());
            }
        });

        return new AlertDialog.Builder(context)
                .setTitle("New List")
                .setView(view)
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createClicked.onNext("clicked");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }


}
