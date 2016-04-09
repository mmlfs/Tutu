package com.hackpku.tutu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnCameraChangeListener;
import com.amap.api.maps2d.AMap.OnMapLongClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.hackpku.tutu.mylib.Tuphoto;

import java.util.LinkedList;
import java.util.Vector;

public class MapActivity extends Activity implements OnCameraChangeListener,OnMapLongClickListener{

    private MapView mapView;
    private AMap aMap;
    private LinkedList<Marker> llm;
    private Vector<Tuphoto> vt;
    private int[] photos = new int[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        photos[0] = R.mipmap.a;photos[1]=R.mipmap.b;photos[2]=R.mipmap.c;
        photos[3] = R.mipmap.d;photos[4]=R.mipmap.e;photos[5]=R.mipmap.f;
        photos[6] = R.mipmap.g;photos[7]=R.mipmap.h;photos[8]=R.mipmap.empty_photo;
        photos[9] = R.mipmap.pku;

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

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.99576396+(Math.random()-0.5)*0.01, 116.30360842+(Math.random()-0.5)*0.01), 16));
        llm = new LinkedList<Marker>();
        vt = getPosPhoto(aMap.getCameraPosition().target);
        for(Tuphoto p: vt) {
            aMap.addMarker(p.getMarker());
        }

        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLngBounds llb = aMap.getProjection().getVisibleRegion().latLngBounds;
        double la1 = llb.northeast.latitude, la2 = llb.southwest.latitude;
        double lo1 = llb.northeast.longitude, lo2 = llb.southwest.longitude;
        Tuphoto p;

        for(int i=0; i<vt.size(); i++){
            if(!llb.contains( new LatLng(vt.get(i).wei, vt.get(i).jing))){
                vt.set(i, new Tuphoto(la1+(la2-la1)*Math.random(),lo1+(lo2-lo1)*Math.random(),
                        BitmapDescriptorFactory.fromResource(photos[(int)(Math.random()*10)])));
            }
        }
        aMap.clear();
        for(Tuphoto tp:vt) aMap.addMarker(tp.getMarker());

        String info = "na:"+la1+"-"+lo1+"\nsw:"+la2+"-"+lo2+"\nphoto num:"+vt.size();
        Toast toast=Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT);
        toast.show();

    }

    Vector<Tuphoto> getPosPhoto(LatLng lng){
        int i;
        Vector<Tuphoto> vt = new Vector<>();
        for(i=0; i<10; i++){
            vt.add(new Tuphoto(lng.latitude+(Math.random()-0.5)*0.003,
                    lng.longitude+(Math.random()-0.5)*0.003,
                    BitmapDescriptorFactory.fromResource(photos[(int)(Math.random()*10)])));
            System.out.println("Photo size:"+vt.get(i).width+"-"+vt.get(i).height+"*"+vt.get(i).zoom);
        }
        return vt;
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

    @Override
    public void onMapLongClick(LatLng latLng) {
        CameraPosition cp=aMap.getCameraPosition();
        String info="维度："+latLng.latitude+"\n精度"+latLng.longitude+"\n缩放:"+cp.zoom+"\n中心维度："+cp.target.latitude+"\n中心精度："+cp.target.longitude;
        Toast toast=Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT);
        toast.show();
    }
}
