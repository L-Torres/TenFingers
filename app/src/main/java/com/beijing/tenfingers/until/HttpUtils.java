package com.beijing.tenfingers.until;

import com.hemaapp.hm_FrameWork.HemaUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    private static String URL_PATH="http://101.200.154.97:9001/order/code?=";
    private static HttpURLConnection httpURLConnection = null;
    public HttpUtils(){

    }

    public static String  shuchu(String id){
        InputStream inputStream = getInputStream(id);
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            result = "";
            String line = "";
            try {
                while((line = reader.readLine())!= null){
                    result = result+ line;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(result);
            httpURLConnection.disconnect();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 获取服务端的数据，以InputStream返回
     * @return
     */
    public static InputStream getInputStream(String id){
        InputStream inputStream = null;

        try {
            URL url = new URL(URL_PATH+id);
            if(url != null){
                try {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    //超时时间
                    httpURLConnection.setConnectTimeout(3000);
                    //表示设置本次http请求使用GET方式
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("token", HemaUser.token);
//                    int responsecode = httpURLConnection.getResponseCode();
//
//                    if(responsecode == HttpURLConnection.HTTP_OK){
                        inputStream = httpURLConnection.getInputStream();
//                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inputStream;
    }
}
