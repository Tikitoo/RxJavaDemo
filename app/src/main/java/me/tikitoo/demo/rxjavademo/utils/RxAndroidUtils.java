package me.tikitoo.demo.rxjavademo.utils;

import android.widget.CheckBox;

import com.f2prateek.rx.preferences.Preference;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class RxAndroidUtils {
    CompositeSubscription mSubscription;
    public void init() {

    }
    void bindPreference(CheckBox checkBox, Preference<Boolean> preference) {
        preference.asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RxCompoundButton.checked(checkBox));

        mSubscription.add(RxCompoundButton.checkedChanges(checkBox)
                .skip(1)
                .subscribe(preference.asAction()));
    }
}
