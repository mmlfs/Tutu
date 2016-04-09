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
import android.widget.TextView;

import com.hackpku.tutu.mylib.HorizontalScrollViewAdapter;
import com.hackpku.tutu.mylib.MyHorizontalScrollView;
import com.hackpku.tutu.mylib.NetWorkMethods;
import com.hackpku.tutu.mylib.Picture;
import com.hackpku.tutu.mylib.Position;

@TargetApi(15)
public class ScrollPhotoActivity extends Activity {

    public static LruCache<String, Bitmap> mMemoryCache;

    private MyHorizontalScrollView mHorizontalScrollView;
    private HorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;
    private Position position;
    private List<Picture> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scroll_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = new Position();
        mDatas = NetWorkMethods.getUrls(bundle.getDouble("longitude"), bundle.getDouble("latitude"), position);

        mImg = (ImageView) findViewById(R.id.id_content);
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picture info = (Picture) view.getTag();
                Bundle data = new Bundle();
                data.putString("url", info.url);
                data.putString("content", info.comment);
                data.putString("age", String.valueOf(info.age));
                data.putString("attractive", String.valueOf(info.attractive));
                data.putString("male", String.valueOf(info.male));
                data.putString("female", String.valueOf(info.female));
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
                        HorizontalScrollViewAdapter.setImageView(mDatas.get(position).url, mImg);
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
                HorizontalScrollViewAdapter.setImageView(mDatas.get(position).url, mImg);
                mImg.setTag(mDatas.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);

        TextView textView = (TextView) findViewById(R.id.text);
        String content = "";
        content += "标签: " + position.tag + "\n";
        content += "平均年龄: " + String.valueOf(position.age) + "\n";
        content += "平均态度: " + String.valueOf(position.attitude) + "\t\t";
        content += "平均颜值: " + String.valueOf(position.attractive) + "\n";
        content += "女生数: " + String.valueOf(position.female) + "\t\t";
        content += "男生数: " + String.valueOf(position.male) + "\n";
        textView.setText(content);
    }

}
