package com.hackpku.tutu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

    private Button mapBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapBtn = (Button) findViewById(R.id.entryMapBtn);
        mapBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.equals(mapBtn)) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
    }
}
