package com.hy.backstagerender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private final String TAG = "ABC";
    private LinearLayout linearLayout;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.container);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        linearLayout.postDelayed(() -> {
            TextView textView = new TextView(MainActivity.this);
            textView.setText("Delay");
            textView.setTextColor(0xff0000);
            linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }, 2000L);
        ViewTreeObserver observer = linearLayout.getViewTreeObserver();
        // onGlobalLayout仅在页面处于可见状态时才能接收到调用。
        // 这里可以看到在onStop之后依然能接收到ViewRootImpl调用。
        observer.addOnGlobalLayoutListener(() -> {
            Log.i(TAG, "Layout count: " + ++count);
            Log.i(TAG, "Child count: " + linearLayout.getChildCount());
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");

    }
}