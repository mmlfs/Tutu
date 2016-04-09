package com.hackpku.tutu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

    private Button mapBtn = null;
    private Button photoBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapBtn = (Button) findViewById(R.id.entryMapBtn);
        photoBtn = (Button) findViewById(R.id.entryPhotoBtn);
        mapBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Toast toast=Toast.makeText(getApplicationContext(), "默认的Toast", Toast.LENGTH_SHORT);
        toast.show();
        if(v.equals(mapBtn)) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
        else if(v.equals(photoBtn)) {
            Log.e("pp", "end");
            Intent intent = new Intent(this, ScrollPhotoActivity.class);
            startActivity(intent);
        }
    }
}
