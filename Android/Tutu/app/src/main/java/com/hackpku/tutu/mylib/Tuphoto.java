package com.hackpku.tutu.mylib;

import com.amap.api.maps2d.model.BitmapDescriptor;
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
        bitmap = photo;
        comment = c;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        zoom = 300f/width;
    }
    public MarkerOptions getMarker(){
        return new MarkerOptions().anchor(zoom, zoom).icon(bitmap).position(new LatLng(wei, jing));
    }
}
