package com.example.messaging_version_01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout openChat_button = (FrameLayout) findViewById(R.id.openChat_button);
        openChat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();
            }
        });

    }
    public void openChat(){
        Intent intent =new Intent(this, chat_activity.class);
        startActivity(intent);
    }
}
