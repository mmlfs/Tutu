package com.hackpku.tutu.mylib;

import android.graphics.Bitmap;

import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;

/**
 * Created by wkd on 16/4/9.
 */
public class Tuphoto {
    public double wei;
    public double jing;
    public BitmapDescriptor bitmap;
    public int width;
    public int height;
    public float zoom;
    public String comment;
    public Tuphoto(double w, double j, BitmapDescriptor photo){
        this(w, j, photo, "");
    }
    public Tuphoto(double w, double j, Bitmap bmap){
        this(w, j, bmap, "");
    }
    public Tuphoto(double w, double j, Bitmap bmap, String c){
        wei = w;
        jing = j;
        zoom = 300f/bmap.getWidth();
        Bitmap bm = Bitmap.createScaledBitmap(bmap, (int)(bmap.getWidth()*zoom),(int)(bmap.getHeight()*zoom), true);
        bitmap = BitmapDescriptorFactory.fromBitmap(bm);
        comment = c;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
    public Tuphoto(double w, double j, BitmapDescriptor photo, String c){
        this(w, j, photo.getBitmap(), c);
    }
    public MarkerOptions getMarker(){
        return new MarkerOptions().anchor(0.5f, 0.5f).icon(bitmap).position(new LatLng(wei, jing));
    }
}
