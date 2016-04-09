package com.hackpku.tutu.mylib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by duyanpku on 16/4/9.
 */
public class NetWorkMethods {

    public static JSONObject uploadPicture(String filePath, double longitude, double latitude, String comment) {

        JSONObject ans = null;
        JSONObject result = GetPostUtil.uploadFile(filePath, "http://121.201.58.48/api/img/upload_file/");
        Log.i("DUYAN", "asdas" + result.toString());

        try {
            String path = "http://121.201.58.48/download/" + result.getString("data");
            String params = "longitude=" + String.valueOf(longitude) + "&";
            params += "latitude=" + String.valueOf(latitude) + "&";
            params += "path=" + path;
            ans = GetPostUtil.sendGet("http://121.201.58.48/api/img/upload/", params);

            Log.i("DUYAN", "asdas" + ans.toString());
            path = "http://121.201.58.48/Tutu/Tutu/img/" + String.valueOf(ans.getInt("info")) + "/add_comment/";
            params = "content=" + comment;
            GetPostUtil.sendGet(path, params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static JSONObject getPictures(double longitude, double latitude) {
        String params = "longitude=" + String.valueOf(longitude) + "&" + "latitude=" + String.valueOf(latitude);
        Log.i("DUYAN", "jingwei" + params);
        return GetPostUtil.sendGet("http://121.201.58.48/api/img/list_around_images/", params);
    }

    public static ArrayList<Tuphoto> getBitmaps(double longitude, double latitude) {
        JSONObject result = getPictures(longitude, latitude);

        Log.i("DUYAN", "jingwei" + result.toString());
        ArrayList<Tuphoto> ans = new ArrayList<>();
        try {
            JSONArray jsonArray = result.getJSONArray("data");
            JSONObject jsonObject = null, child = null;
            String imageUrl = null;
            Bitmap bitmap = null;
            String comment = null;
            for (int i = 0; i < jsonArray.length(); ++i) {
                jsonObject = (JSONObject) jsonArray.get(i);
                if (jsonObject.getJSONArray("related_img").length() > 0) {
                    child = (JSONObject) ((jsonObject.getJSONArray("related_img").get(0)));
                    comment = child.getString("content");
                }
                imageUrl = jsonObject.getString("path");
                if(!imageUrl.startsWith("http"))
                    imageUrl = "http://121.201.58.48/download/" + imageUrl;
                bitmap = HorizontalScrollViewAdapter.getBitmapFromMemoryCache(imageUrl);

                if (bitmap == null) {
                    try {
                        URL url = new URL(imageUrl);
                        InputStream is = url.openStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        Log.i("DUYAN", "jingweieererer" + bitmap.toString());
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (bitmap != null) {
                        HorizontalScrollViewAdapter.addBitmapToMemoryCache(imageUrl, bitmap);
                    }
                }
                Tuphoto tuphoto = new Tuphoto(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"), bitmap, comment);
                ans.add(tuphoto);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
