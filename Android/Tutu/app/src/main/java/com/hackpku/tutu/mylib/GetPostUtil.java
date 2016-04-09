package com.hackpku.tutu.mylib;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
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

    /* 模拟表单上传文件的方法 */
    public static JSONObject uploadFile(String srcPath, String uploadUrl)
    {
        String result = null;
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try
        {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"Filedata\"; filename=\""
                    + srcPath.substring(srcPath.lastIndexOf("/") + 1)
                    + "\"" + end);
            dos.writeBytes(end);
            //将SD 文件通过输入流读到Java代码中-++++++++++++++++++++++++++++++`````````````````````````
            FileInputStream fis = new FileInputStream(srcPath);
            byte[] buffer = new byte[8192000]; // 8k
            int count = 0;
            while ((count = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
            }
            fis.close();
            System.out.println("file send to server............");
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            //读取服务器返回结果
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            result = br.readLine();
            while ((line = br.readLine()) != null) {
                result += "\n" + line;
            }
            System.out.println(result);

            dos.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
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
