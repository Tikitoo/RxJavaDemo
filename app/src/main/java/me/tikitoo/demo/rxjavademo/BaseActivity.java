package me.tikitoo.demo.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
    }

    Class<?>[] mClasses = new Class<?>[] {MainActivity.class, RxBindActivity.class};
    Button button = null;
    LinearLayout mLayoutRoot = null;

    private void initView() {
        mLayoutRoot = (LinearLayout) findViewById(R.id.layout_top);
        for (int i = 0; i < mClasses.length; i++) {
            button = new Button(BaseActivity.this);
            button.setId(i);
            button.setText(mClasses[i].getSimpleName());
            button.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(this);
            mLayoutRoot.addView(button);
        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(BaseActivity.this, mClasses[v.getId()]));
    }


}
