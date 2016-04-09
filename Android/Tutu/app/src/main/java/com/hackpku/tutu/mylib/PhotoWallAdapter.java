package com.hackpku.tutu.mylib;

/**
 * Created by duyanpku on 16/4/9.
 */
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hackpku.tutu.PhotoWallActivity;
import com.hackpku.tutu.R;

/**
 * GridView的适配器，负责异步从网络上下载图片展示在照片墙上。
 *
 * @author guolin
 */
@TargetApi(15)
public class PhotoWallAdapter extends ArrayAdapter<String> {
    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private LruCache<String, Bitmap> mMemoryCache;

    /**
     * GridView的实例
     */
    private GridView mPhotoWall;

    public PhotoWallAdapter(Context context, int textViewResourceId, String[] objects,
                            GridView photoWall) {
        super(context, textViewResourceId, objects);
        mPhotoWall = photoWall;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String imageURL = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, null);
        } else {
            view = convertView;
        }
        final ImageView photo = (ImageView) view.findViewById(R.id.photo);
        // 给ImageView设置一个Tag，保证异步加载图片时不会乱序
        photo.setTag(imageURL);

        setImageView(imageURL, photo);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getContext(), "你好啊", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return view;
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
    private void setImageView(String imageUrl, ImageView imageView) {
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
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            PhotoWallActivity.mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key
     *            LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return PhotoWallActivity.mMemoryCache.get(key);
    }
}
