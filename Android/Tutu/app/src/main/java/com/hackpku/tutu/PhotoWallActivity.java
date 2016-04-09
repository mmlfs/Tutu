package com.hackpku.tutu;

/**
 * Created by duyanpku on 16/4/9.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.hackpku.tutu.mylib.Images;
import com.hackpku.tutu.mylib.PhotoWallAdapter;

/**
 * 照片墙主活动，使用GridView展示照片墙。
 *
 * @author guolin
 */
@TargetApi(15)
public class PhotoWallActivity extends Activity implements OnClickListener {

    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapter adapter;

    public static LruCache<String, Bitmap> mMemoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_wall);
        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(adapter);

        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}

