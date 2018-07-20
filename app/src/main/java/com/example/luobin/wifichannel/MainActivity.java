package com.example.luobin.wifichannel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ChannelRatingAdapter mChannelRatingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mChannelRatingAdapter = new ChannelRatingAdapter(getApplicationContext());
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChannelRatingAdapter.update(WiFiBand.GHZ2);
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChannelRatingAdapter.update(WiFiBand.GHZ5);
            }
        });
    }
}
