package com.hackpku.tutu.mylib;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by duyanpku on 16/4/9.
 */
public class NetWorkMethods {

    public static JSONObject uploadPicture(String filePath, double longitude, double latitude) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // CompressFormat set up to JPG, you can change to PNG or whatever you want;
        //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();

        JSONObject result = GetPostUtil.sendPost("http://121.201.58.48/api/img/upload_file/", "Filedata=" + data);
        Log.i("DUYAN", result.toString());
        JSONObject ans = null;
        try {
            String path = "http://121.201.58.48/Tutu/Tutu/img/upload/" + result.getString("data");
            String params = "longitude=" + String.valueOf(longitude) + "&";
            params += "latitude=" + String.valueOf(latitude) + "&";
            params += "path=" + path;
            ans = GetPostUtil.sendGet("http://121.201.58.48/api/img/upload/", params);
            Log.i("DUYAN", "asdas" + result.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static JSONObject getPictures(double longitude, double latitude) {
        String params = "longitude=" + String.valueOf(longitude) + "&" + "latitude=" + String.valueOf(latitude);
        return GetPostUtil.sendGet("http://121.201.58.48/api/img/list_around_images/", params);
    }
}
