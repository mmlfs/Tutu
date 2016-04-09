package com.hackpku.tutu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.hackpku.tutu.mylib.HorizontalScrollViewAdapter;
import com.hackpku.tutu.mylib.MyHorizontalScrollView;

public class ScrollPhotoActivity extends Activity {

    private MyHorizontalScrollView mHorizontalScrollView;
    private HorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(
            R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d,
            R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h,
            R.mipmap.l));

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scroll_layout);

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
                        mImg.setImageResource(mDatas.get(position));
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
                mImg.setImageResource(mDatas.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);
    }

}
