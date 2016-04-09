package com.hackpku.tutu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.hackpku.tutu.mylib.HorizontalScrollViewAdapter;
import com.hackpku.tutu.mylib.MyHorizontalScrollView;

@TargetApi(15)
public class ScrollPhotoActivity extends Activity {

    public static LruCache<String, Bitmap> mMemoryCache;

    private MyHorizontalScrollView mHorizontalScrollView;
    private HorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList(
            "http://img4.duitang.com/uploads/item/201510/17/20151017000316_diBQY.jpeg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/0d338744ebf81a4c5fa4e3d9d32a6059252da60f.jpg",
            "http://img4.duitang.com/uploads/item/201510/17/20151017000316_diBQY.jpeg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=409601260a7b02080cc93fe952d8f25f/08081501213fb80e557c485436d12f2ebb3894e0.jpg",
            "http://img4.duitang.com/uploads/item/201510/17/20151017000316_diBQY.jpeg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/0d338744ebf81a4c5fa4e3d9d32a6059252da60f.jpg",
            "http://img4.duitang.com/uploads/item/201510/17/20151017000316_diBQY.jpeg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/0d338744ebf81a4c5fa4e3d9d32a6059252da60f.jpg",
            "http://img4.duitang.com/uploads/item/201510/17/20151017000316_diBQY.jpeg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/0d338744ebf81a4c5fa4e3d9d32a6059252da60f.jpg",
            "http://img4.duitang.com/uploads/item/201510/17/20151017000316_diBQY.jpeg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/0d338744ebf81a4c5fa4e3d9d32a6059252da60f.jpg",
            "http://img4.duitang.com/uploads/item/201510/17/20151017000316_diBQY.jpeg",
            "http://g.hiphotos.baidu.com/zhidao/pic/item/0d338744ebf81a4c5fa4e3d9d32a6059252da60f.jpg"));

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scroll_layout);

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

        mImg = (ImageView) findViewById(R.id.id_content);

        mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
        mAdapter = new HorizontalScrollViewAdapter(this, mDatas);
        //添加滚动回调
        mHorizontalScrollView
                .setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener()
                {
                    @Override
                    public void onCurrentImgChanged(int position,
                                                    View viewIndicator)
                    {
                        HorizontalScrollViewAdapter.setImageView(mDatas.get(position), mImg);
                        viewIndicator.setBackgroundColor(Color
                                .parseColor("#AA024DA4"));
                    }
                });
        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener()
        {

            @Override
            public void onClick(View view, int position)
            {
                HorizontalScrollViewAdapter.setImageView(mDatas.get(position), mImg);
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);

    }

}
