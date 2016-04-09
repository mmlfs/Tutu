package com.hackpku.tutu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scroll_layout);

        Intent intent = getIntent();

        mImg = (ImageView) findViewById(R.id.id_content);
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("url", (String) view.getTag());
                Intent intent = new Intent(ScrollPhotoActivity.this, CommentActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });

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
                        mImg.setTag(mDatas.get(position));
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
                mImg.setTag(mDatas.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);

    }

}
