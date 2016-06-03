package me.tikitoo.demo.rxjavademo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String CREATE_USER_LIST = ""
            + "CREATE TABLE " + UserList.TABLE + "("
            + UserList.ID + " INTEGER NOT NULL PRIMARY KEY, "
            + UserList.NAME + " TEXT NOT NULL, "
            + UserList.INFO + " TEXT"
             + ")";

    public DbOpenHelper(Context context) {
        super(context, "todo.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_LIST);

        db.insert(UserList.TABLE, null, new UserList.Builder()
                .name("tikitoo")
                .info("android")
                .build());

        db.insert(UserList.TABLE, null, new UserList.Builder()
                .name("Hello")
                .info("C language")
                .build());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
