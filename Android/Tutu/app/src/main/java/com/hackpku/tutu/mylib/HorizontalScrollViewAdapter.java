package com.hackpku.tutu.mylib;

import java.io.InputStream;
import java.net.URL;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackpku.tutu.CommentActivity;
import com.hackpku.tutu.R;
import com.hackpku.tutu.ScrollPhotoActivity;

@TargetApi(15)
public class HorizontalScrollViewAdapter
{
	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */

	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mDatas;

	public HorizontalScrollViewAdapter(Context context, List<String> mDatas)
	{
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	public int getCount()
	{
		return mDatas.size();
	}

	public Object getItem(int position)
	{
		return mDatas.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_index_gallery_item, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_index_gallery_item_image);
			viewHolder.mText = (TextView) convertView
					.findViewById(R.id.id_index_gallery_item_text);

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		setImageView(mDatas.get(position), viewHolder.mImg);
		viewHolder.mText.setText("some info ");

		return convertView;
	}

	private class ViewHolder
	{
		ImageView mImg;
		TextView mText;
	}

	/**
	 * 给ImageView设置图片。首先从LruCache中取出图片的缓存，设置到ImageView上。如果LruCache中没有该图片的缓存，
	 * 就给ImageView设置一张默认图片。
	 *
	 * @param imageUrl
	 *            图片的URL地址，用于作为LruCache的键。
	 * @param imageView
	 *            用于显示图片的控件。
	 */
	public static void setImageView(String imageUrl, ImageView imageView) {
		Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);

		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			try {
				URL url = new URL(imageUrl);
				InputStream is = url.openStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bitmap != null) {
				addBitmapToMemoryCache(imageUrl, bitmap);
				imageView.setImageBitmap(bitmap);
			}
			else
				imageView.setImageResource(R.mipmap.empty_photo);
		}
	}

	/**
	 * 将一张图片存储到LruCache中。
	 *
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @param bitmap
	 *            LruCache的键，这里传入从网络上下载的Bitmap对象。
	 */
	public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			ScrollPhotoActivity.mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 *
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @return 对应传入键的Bitmap对象，或者null。
	 */
	public static Bitmap getBitmapFromMemoryCache(String key) {
		return ScrollPhotoActivity.mMemoryCache.get(key);
	}
}
