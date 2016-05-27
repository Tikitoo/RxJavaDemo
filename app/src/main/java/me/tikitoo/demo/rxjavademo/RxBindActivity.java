package me.tikitoo.demo.rxjavademo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.jakewharton.rxbinding.widget.RxCompoundButton;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * https://github.com/f2prateek/rx-preferences/blob/master/rx-preferences-sample
 * /src/main/java/com/f2prateek/rx/preferences/sample/SampleActivity.java
 */
public class RxBindActivity extends AppCompatActivity {
    CompositeSubscription mSubscription;
    Preference<Boolean> fooPreference, barPreference;
    private CheckBox mCheckBox, mCheckBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bind);
        initView();

        SharedPreferences preferences = getSharedPreferences("RxCompoundButton", Context.MODE_PRIVATE);
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);

        fooPreference = rxPreferences.getBoolean("foo");
        barPreference = rxPreferences.getBoolean("bar");
    }

    private void initView() {
        mCheckBox = (CheckBox) findViewById(R.id.check1);
        mCheckBox3 = (CheckBox) findViewById(R.id.check3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSubscription = new CompositeSubscription();
        bindPreference(mCheckBox, fooPreference);
        bindPreference(mCheckBox3, barPreference);
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
