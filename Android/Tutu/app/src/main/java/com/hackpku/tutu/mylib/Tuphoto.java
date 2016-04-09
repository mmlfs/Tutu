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
    public Tuphoto(double w, double j, BitmapDescriptor photo, String c){
        wei = w;
        jing = j;
        Bitmap bm = photo.getBitmap();
        zoom = 300f/bm.getWidth();
        bm = Bitmap.createScaledBitmap(bm, (int)(bm.getWidth()*zoom), (int)(bm.getHeight()*zoom), true);
        bitmap = BitmapDescriptorFactory.fromBitmap(bm);
        comment = c;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
    public MarkerOptions getMarker(){
        return new MarkerOptions().anchor(0.5f, 0.5f).icon(bitmap).position(new LatLng(wei, jing));
    }
}
