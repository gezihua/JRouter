package com.superx.j.router;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.superx.j.regedit.JRegedit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.mock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickMock(view);
            }
        });
    }

    private void onClickMock(View view) {

        JRegedit.API.findServiceByInterface(JrInterface.class);


    }
}
