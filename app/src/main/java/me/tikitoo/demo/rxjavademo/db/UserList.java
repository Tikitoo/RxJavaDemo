package me.tikitoo.demo.rxjavademo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import rx.functions.Func1;

@AutoValue
public abstract class UserList implements Parcelable {
    public static final String TABLE = "user_list";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String INFO = "info";

    public static final String QUERY_SELECT = "SELECT * FROM " + TABLE;

    abstract Integer id();
    abstract String name();
    abstract String info();

    public static Func1<Cursor, UserList> MAPPER = new Func1<Cursor, UserList>() {
        @Override
        public UserList call(Cursor cursor) {
            Integer id = Db.getInt(cursor, ID);
            String name = Db.getString(cursor, NAME);
            String info = Db.getString(cursor, INFO);
            return new AutoValue_UserList(id, name, info);
        }
    };

    public static final class Builder {
        private final ContentValues mValues = new ContentValues();

        public Builder id(Integer id) {
            mValues.put(ID, id);
            return this;
        }

        public Builder name(String name) {
            mValues.put(NAME, name);
            return this;
        }

        public Builder info(String info) {
            mValues.put(INFO, info);
            return this;
        }

        public ContentValues build() {
            return mValues;
        }

    }


}
