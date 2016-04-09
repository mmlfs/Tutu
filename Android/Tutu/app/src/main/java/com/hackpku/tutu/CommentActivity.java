package com.hackpku.tutu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by duyanpku on 16/4/9.
 */
public class CommentActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Toast toast=Toast.makeText(getApplicationContext(), bundle.getString("url"), Toast.LENGTH_LONG);
        toast.show();
    }
}
