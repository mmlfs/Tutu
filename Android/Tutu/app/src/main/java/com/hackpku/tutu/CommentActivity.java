package com.hackpku.tutu;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.GridView;

import com.hackpku.tutu.mylib.Images;
import com.hackpku.tutu.mylib.PhotoWallAdapter;

/**
 * Created by duyanpku on 16/4/9.
 */
public class CommentActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
    }
}
