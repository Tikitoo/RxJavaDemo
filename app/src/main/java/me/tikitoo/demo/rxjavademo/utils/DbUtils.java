package me.tikitoo.demo.rxjavademo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.concurrent.atomic.AtomicInteger;

import me.tikitoo.demo.rxjavademo.db.DbOpenHelper;
import me.tikitoo.demo.rxjavademo.db.UserList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DbUtils {
    public static final String TABLE_USERS = "users";

    BriteDatabase mDb = null;


    public static BriteDatabase createDb(Context context) {
        BriteDatabase db = null;
        if (db == null) {
            DbOpenHelper helper = new DbOpenHelper(context);
            SqlBrite sqlBrite = SqlBrite.create();
            db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        }
        return db;
    }

    private void read() {
        mDb.createQuery(UserList.TABLE, UserList.QUERY_SELECT)
        .mapToList(UserList.MAPPER)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe();

    }

    private void insertUserList(Observable<SqlBrite.Query> users) {
        final AtomicInteger queries = new AtomicInteger();
        users.subscribe(new Action1<SqlBrite.Query>() {
            @Override
            public void call(SqlBrite.Query query) {
                queries.getAndDecrement();
            }
        });

        System.out.println("DbUtils Queries: " + queries.get());

        BriteDatabase.Transaction transaction = mDb.newTransaction();
        try {
            mDb.insert(TABLE_USERS, createUser("name", "bjfdjlaj"));
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        System.out.println("DbUtils Queries: " + queries.get());
    }

    @NonNull
    private ContentValues createUser(String field, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(field, value);
        return contentValues;
    }
}
