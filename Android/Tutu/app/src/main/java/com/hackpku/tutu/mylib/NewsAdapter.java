package com.hackpku.tutu.mylib;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackpku.tutu.CommentActivity;
import com.hackpku.tutu.R;
import com.hackpku.tutu.ScrollPhotoActivity;

@TargetApi(15)
public class NewsAdapter extends ArrayAdapter<String>
{
    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mDatas;// 长为2,0是图片的url,1是图片的介绍

    public NewsAdapter(Context context, int textViewResourceId, ArrayList<String> mDatas)
    {
        super(context, textViewResourceId, mDatas);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            if (position == 0) {
                convertView = mInflater.inflate(
                        R.layout.photo, parent, false);
                viewHolder.mImg = (ImageView) convertView
                        .findViewById(R.id.news_photo);
            }
            else {
                convertView = mInflater.inflate(
                        R.layout.text, parent, false);
                viewHolder.mText = (TextView) convertView
                        .findViewById(R.id.news_content);
            }
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0)
            viewHolder.mImg.setImageBitmap(HorizontalScrollViewAdapter.getBitmapFromMemoryCache(mDatas.get(0)));
        else
            viewHolder.mText.setText(mDatas.get(1));

        return convertView;
    }

    private class ViewHolder
    {
        ImageView mImg;
        TextView mText;
    }
}
