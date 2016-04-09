package com.hackpku.tutu.mylib;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    public static JSONObject sendData(Bitmap bitmap) {
        Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // CompressFormat set up to JPG, you can change to PNG or whatever you want;
        bmpCompressed.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();
        JSONObject ans = null;
        return ans;
    }
}
