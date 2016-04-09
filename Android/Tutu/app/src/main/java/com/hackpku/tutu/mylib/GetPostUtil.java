package com.hackpku.tutu.mylib;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by duyanpku on 16/4/9.
 */
public class GetPostUtil {
    public static JSONObject sendGet(String url, String params) {
        String result = null;
        BufferedReader in = null;
        try {
            String urlName = url + "?" + params;
            URL realUrl = new URL(urlName);
            URLConnection conn = realUrl.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();

            for  (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = in.readLine();
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        }
        catch (Exception e) {
            System.out.println("发送Get请求出现异常!" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        JSONObject ans = null;
        try {
            ans = new JSONObject(result);
        }
        catch (Exception e) {
            System.out.println("JSON转化错误!");
            e.printStackTrace();
        }
        return ans;
    }

    public static JSONObject sendPost(String url, String params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());
            out.print(params);
            out.flush();

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = in.readLine();
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        }
        catch (Exception e) {
            System.out.println("发送Post请求出现异常!" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        JSONObject ans = null;
        try {
            ans = new JSONObject(result);
        }
        catch (Exception e) {
            System.out.println("JSON转化错误!");
            e.printStackTrace();
        }

        return ans;
    }

    public static JSONObject uploadPicture(Bitmap bitmap)
    {
        String result = null;
        OutputStream out = null;
        InputStream in = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // CompressFormat set up to JPG, you can change to PNG or whatever you want;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();

        try {
            URL url = new URL("http://121.201.58.48/api/img/upload_file/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //上传图片的一些参数设置
            conn.setRequestProperty("Accept", "image/gif,   image/x-xbitmap,   image/jpeg,   image/pjpeg,   application/vnd.ms-excel,   application/vnd.ms-powerpoint,   application/msword,   application/x-shockwave-flash,   application/x-quickviewplus,   */*");
            conn.setRequestProperty("Accept-Language", "zh-cn");
            conn.setRequestProperty("Content-type", "multipart/form-data;   boundary=---------------------------7d318fd100112");
            conn.setRequestProperty("Accept-Encoding", "gzip,   deflate");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0   (compatible;   MSIE   6.0;   Windows   NT   5.1)");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(true);
            out = conn.getOutputStream();

            //写入图片流
            out.write(data);
            out.flush();

            //读取响应数据
            int code = conn.getResponseCode();
            String line;
            in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = reader.readLine();
            while ((line = reader.readLine()) != null)
                result += "\n" + line;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        JSONObject ans = null;
        try {
            ans = new JSONObject(result);
        }
        catch (Exception e) {
            System.out.println("JSON转化错误!");
            e.printStackTrace();
        }
        return ans;
    }
}
