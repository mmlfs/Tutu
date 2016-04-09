package com.hackpku.tutu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

public class MapActivity extends Activity {

    private MapView mapView;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        Button btnCamera = (Button) findViewById(R.id.entryCameraBtn);
        btnCamera.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MapActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        init();
/*
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.936713, 116.386475), 19));
        LatLngBounds bounds = new LatLngBounds.Builder().include(new LatLng(39.935029, 116.384377))
                .include(new LatLng(39.939577, 116.388331)).build();
        GroundOverlayOptions groundoverlay = aMap.addGroundOverlay(new GroundOverlayOptions()
                                    .anchor(0.5f, 0.5f).transparency(0.1f)
                                    .image(BitmapDescriptorFactory.fromResource(R.mipmap.)))
                                    */
    }
    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();

        }

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
