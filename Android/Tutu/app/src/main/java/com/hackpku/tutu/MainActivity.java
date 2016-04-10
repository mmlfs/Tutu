package com.hackpku.tutu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@TargetApi(15)
public class MainActivity extends Activity implements OnClickListener{

    private Button mapBtn = null;
    private Button photoBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        mapBtn = (Button) findViewById(R.id.entryMapBtn);
        photoBtn = (Button) findViewById(R.id.entryPhotoBtn);
        mapBtn.setOnClickListener(this);
        photoBtn.setOnClickListener(this);

        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8
        ScrollPhotoActivity.mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };

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
