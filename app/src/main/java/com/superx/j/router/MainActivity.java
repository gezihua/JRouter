package com.superx.j.router;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.superx.j.regedit.JRegeditApi;

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
        findViewById(R.id.router).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRouter(view);
            }
        });
    }

    private void onClickRouter(View view) {
//        JRouterApi.API.build("/test/main").withString("s").navigation();



    }

    private void onClickMock(View view) {
        JrInterface serviceByInterface = JRegeditApi.API.findServiceByInterface(this, JrInterface.class);
        Log.e("MainActivity", serviceByInterface.hashCode() + "");
        serviceByInterface.sayHello();
    }
}
