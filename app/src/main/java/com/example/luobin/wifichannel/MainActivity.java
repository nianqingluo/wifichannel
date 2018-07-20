package com.example.luobin.wifichannel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelRatingAdapter channelRatingAdapter =new ChannelRatingAdapter(getApplicationContext(),null);
                channelRatingAdapter.update();
            }
        });
    }
}
