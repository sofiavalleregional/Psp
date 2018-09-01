package com.worldskills.psp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.worldskills.psp.activities.TimeLog;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void pasar(View view) {
        Intent intent= new Intent(this, TimeLog.class);
        startActivity(intent);
    }
}
