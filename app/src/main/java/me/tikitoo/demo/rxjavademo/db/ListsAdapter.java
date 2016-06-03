package me.tikitoo.demo.rxjavademo.db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

public class ListsAdapter extends BaseAdapter implements Action1<List<UserList>> {
    private final LayoutInflater mInflater;
    private List<UserList> items = Collections.emptyList();

    public ListsAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public void call(List<UserList> userLists) {
        this.items = userLists;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public UserList getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        UserList userList = getItem(position);
        ((TextView) convertView).setText(userList.name());
        return convertView;
    }
}
