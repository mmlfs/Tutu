package com.hackpku.tutu.mylib;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by duyanpku on 16/4/9.
 */
public class NetWorkMethods {

    public static JSONObject uploadPicture(Bitmap bitmap, double longitude, double latitude) {
        Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // CompressFormat set up to JPG, you can change to PNG or whatever you want;
        bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();

        String params = "Filedata=" + data;
        JSONObject result = GetPostUtil.sendPost("121.201.58.48/api/img/upload_file/", params);
        JSONObject ans = null;
        try {
            String path = "121.201.58.48/Tutu/Tutu/img/upload/" + result.getString("data");
            params = "longitude=" + String.valueOf(longitude) + "&";
            params += "latitude=" + String.valueOf(latitude) + "&";
            params += "path=" + path;
            ans = GetPostUtil.sendGet("121.201.58.48/api/img/upload/", params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static JSONObject getPictures(double longitude, double latitude) {
        String params = "longitude=" + String.valueOf(longitude) + "&" + "latitude=" + String.valueOf(latitude);
        return GetPostUtil.sendGet("121.201.58.48/api/img/list_around_images/", params);
    }
}
