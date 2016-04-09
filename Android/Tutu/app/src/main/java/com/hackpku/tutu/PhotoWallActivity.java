package com.hackpku.tutu;

/**
 * Created by duyanpku on 16/4/9.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import com.hackpku.tutu.mylib.Images;
import com.hackpku.tutu.mylib.PhotoWallAdapter;

/**
 * 照片墙主活动，使用GridView展示照片墙。
 *
 * @author guolin
 */
public class PhotoWallActivity extends Activity {

    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_wall);
        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        adapter.cancelAllTasks();
    }

}

