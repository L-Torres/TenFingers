package com.hemaapp.hm_FrameWork.newnet;

import android.util.Base64;
import android.util.Log;

import com.hemaapp.hm_FrameWork.HemaUser;
import com.hemaapp.hm_FrameWork.HemaUtil;
import com.hemaapp.hm_FrameWork.SHA;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import xtom.frame.XtomActivityManager;
import xtom.frame.XtomConfig;
import xtom.frame.exception.DataParseException;
import xtom.frame.exception.HttpException;
import xtom.frame.util.Md5Util;
import xtom.frame.util.XtomDeviceUuidFactory;
import xtom.frame.util.XtomFileTypeUtil;
import xtom.frame.util.XtomJsonUtil;
import xtom.frame.util.XtomLogger;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomStreamUtil;
import xtom.frame.util.XtomTimeUtil;

public class BaseHttpUtil {
    private static final String TAG = "BaseHttpUtil";
    public static String sessionID = null;
    private static final String END = "\r\n";
    private static final String TWOHYPHENS = "--";
    private static final String BOUNDARY = "yztdhr";

    public BaseHttpUtil() {
    }

    public static JSONObject sendPOSTWithFilesForJSONObject(String path, HashMap<String, String> files, HashMap<String, String> params, String encoding) throws DataParseException, HttpException {
        return XtomJsonUtil.toJsonObject(sendPOSTWithFilesForString(path, files, params, encoding));
    }

    public static String sendPOSTWithFilesForString(String path, HashMap<String, String> files, HashMap<String, String> params, String encoding) throws HttpException {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(path);
            XtomLogger.d("BaseHttpUtil", "The HttpUrl is \n" + path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(XtomConfig.TIMEOUT_CONNECT_HTTP);
            conn.setReadTimeout(XtomConfig.TIMEOUT_READ_FILE);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=yztdhr");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", encoding);
            conn.setRequestProperty("clientType", "1");
            conn.setRequestProperty("token", HemaUser.token);
            if (sessionID != null) {
                conn.setRequestProperty("Cookie", sessionID);
            }

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            writeParams(path, dos, params, encoding);
            writeFiles(dos, files);
            dos.writeBytes("--yztdhr--\r\n");
            dos.flush();
            dos.close();
            String cookie = conn.getHeaderField("set-cookie");
            if (cookie != null) {
                sessionID = cookie.substring(0, cookie.indexOf(";"));
            }

            String data = XtomStreamUtil.iputStreamToString(get(conn));
            XtomLogger.d("BaseHttpUtil", "The back data is \n" + data);
            if (conn != null) {
                conn.disconnect();
            }

            return data;
        } catch (Exception var9) {
            if (conn != null) {
                conn.disconnect();
            }

            throw new HttpException(var9);
        }
    }

    private static void writeFiles(DataOutputStream dos, HashMap<String, String> files) throws IOException {
        Iterator var3 = files.entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var3.next();
            FileInputStream fStream = null;

            try {
                dos.writeBytes("--yztdhr\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"" + (String) entry.getKey() + "\";filename=\"" + (String) entry.getValue() + "\"" + "\r\n");
                XtomLogger.d("BaseHttpUtil", "The file path is \n" + (String) entry.getValue());
                String filetype = XtomFileTypeUtil.getFileTypeByPath((String) entry.getValue());
                XtomLogger.d("BaseHttpUtil", "The file type is " + filetype);
                dos.writeBytes("Content-type: " + filetype + "\r\n");
                dos.writeBytes("\r\n");
                int bufferSize = 10240;
                byte[] buffer = new byte[bufferSize];
//                int length = true;
                File file = new File((String) entry.getValue());
                fStream = new FileInputStream(file);

                int length;
                while ((length = fStream.read(buffer)) != -1) {
                    dos.write(buffer, 0, length);
                }

                dos.writeBytes("\r\n");
            } catch (IOException var17) {
                XtomLogger.e("BaseHttpUtil", "The file params key = \n" + (String) entry.getKey());
                XtomLogger.e("BaseHttpUtil", "The file params value = \n" + (String) entry.getValue());
                throw var17;
            } finally {
                if (fStream != null) {
                    try {
                        fStream.close();
                    } catch (IOException var16) {
                        throw var16;
                    }
                }

            }
        }

    }

    private static void writeParams(String path, DataOutputStream dos, HashMap<String, String> params, String encoding) throws IOException {
        StringBuilder data = new StringBuilder();
        String datetime;
        String[] tempPath;
        String sign;
        params.put("app_id", "201811031654563");
        params.put("timestamps", String.valueOf((long) System.currentTimeMillis() / 1000));
//        params.put("device_id", XtomDeviceUuidFactory.get(XtomActivityManager.getLastActivity()));
        params.put("clienttype", "1");
        if (params != null && !params.isEmpty()) {
            Iterator var9 = params.entrySet().iterator();

            while (var9.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var9.next();
                data.append((String) entry.getKey()).append("=");
                data.append((String) entry.getValue());
                data.append("&");
                if (entry.getValue() != null) {
                    dos.writeBytes("--yztdhr\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + (String) entry.getKey() + "\"" + "\r\n");
                    dos.writeBytes("\r\n");
                    dos.write(((String) entry.getValue()).getBytes(encoding));
                    dos.writeBytes("\r\n");
                }
            }

            data.deleteCharAt(data.length() - 1);
            String temp = "p5m4qqM5XMK5k7mZ2HQQ22U3Zo7j5J" + data.toString();
            String base64 = Base64.encodeToString(temp.getBytes(), Base64.DEFAULT);
            String second = Md5Util.getMd5(base64);
            sign = second.toUpperCase();//转大写

            if (XtomConfig.DIGITAL_CHECK) {

//                datetime = XtomTimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//                data.append("&").append("datetime=").append(datetime).append("&");
                tempPath = path.split("/");
//                sign = Md5Util.getMd5(XtomConfig.DATAKEY + "|" + datetime + "|" + tempPath[tempPath.length - 1]);
//                sign = Md5Util.getMd5(XtomConfig.DATAKEY + "|" + tempPath[tempPath.length - 1]);
                data.append("sign=").append(sign);
                dos.writeBytes("--yztdhr\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"datetime\"\r\n");
                dos.writeBytes("\r\n");
//                dos.write(datetime.getBytes(encoding));
                dos.writeBytes("\r\n");
                dos.writeBytes("--yztdhr\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"sign\"\r\n");
                dos.writeBytes("\r\n");
                dos.write(sign.getBytes(encoding));
                dos.writeBytes("\r\n");
            }
        } else if (XtomConfig.DIGITAL_CHECK) {
            datetime = XtomTimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            data.append("datetime=").append(datetime).append("&");
            tempPath = path.split("/");
            sign = Md5Util.getMd5(XtomConfig.DATAKEY + "|" + datetime + "|" + tempPath[tempPath.length - 1]);
            data.append("sign=").append(sign);
            dos.writeBytes("--yztdhr\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"datetime\"\r\n");
            dos.writeBytes("\r\n");
            dos.write(datetime.getBytes(encoding));
            dos.writeBytes("\r\n");
            dos.writeBytes("--yztdhr\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"sign\"\r\n");
            dos.writeBytes("\r\n");
            dos.write(sign.getBytes(encoding));
            dos.writeBytes("\r\n");
        }

        XtomLogger.d("BaseHttpUtil", "The send data is \n" + data.toString());
    }

    public static JSONObject sendPOSTForJSONObject(String path, HashMap<String, String> params, String encoding) throws DataParseException, HttpException {
        return XtomJsonUtil.toJsonObject(sendPOSTForString(path, params, encoding));
    }

    public static String sendPOSTForString(String path, HashMap<String, String> params, String encoding) throws HttpException {

        StringBuilder data = new StringBuilder();
        XtomLogger.d("BaseHttpUtil", "The HttpUrl is \n" + path);
        String conn;
        String[] tempPath;
        String value;
        String sign = "";


        try {
//            params.put("device_id", XtomSharedPreferencesUtil.get(XtomActivityManager.getLastActivity(), "device_id"));
//            params.put("app_version", XtomSharedPreferencesUtil.get(XtomActivityManager.getLastActivity(), "app_version"));
//            params.put("device_type", "Android");
//            String device_model = android.os.Build.BRAND + android.os.Build.MODEL;
//            params.put("device_model", device_model);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        if (params != null && !params.isEmpty()) {
            Iterator var13 = params.entrySet().iterator();
            while (var13.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var13.next();
                data.append((String) entry.getKey()).append("=");
                if (entry.getValue() != null) {
                    value = ((String) entry.getValue()).replace("&", "%26");
                } else {
                    value = (String) entry.getValue();
                }
                data.append(value);
                data.append("&");
            }
            //获取sign的值
            if (params != null && !params.isEmpty()) {
                Object[] key = params.keySet().toArray();
                Arrays.sort(key);
                String temp = "";
                for (int i = 0; i < key.length; i++) {

                    if ("".equals(temp)) {
                        temp = key[i] + params.get(key[i]);
                    } else {
                        temp = temp + key[i] + params.get(key[i]);
                    }
                }
                String pre_three = "";
                String last_three = "";
                Log.i("sang", "value=>" + temp);
                if (temp.length() > 3) {
                    pre_three = temp.substring(0, 3);
                    last_three = temp.substring(temp.length() - 3, temp.length());
                }
                String sha1 = SHA.encrypt1(path);
                sign = pre_three + sha1 + last_three;
                sign = SHA.encrypt1(sign);
            }
//            data.append("&sign=").append(sign);
        } else {
            String var = SHA.encrypt1(path);
            sign = SHA.encrypt1(var);
        }


        XtomLogger.d("BaseHttpUtil", "The send data is \n" + data.toString());
        conn = null;
        HttpURLConnection conn1 = null;
//        String token = XtomSharedPreferencesUtil.get(XtomActivityManager.getLastActivity(), "token");
        try {
            byte[] entity = data.toString().getBytes();
            conn1 = (HttpURLConnection) (new URL(path)).openConnection();
            conn1.setConnectTimeout(XtomConfig.TIMEOUT_CONNECT_HTTP);
            conn1.setReadTimeout(XtomConfig.TIMEOUT_READ_HTTP);
            conn1.setRequestMethod("POST");
            conn1.setRequestProperty("withCredentials", "true");
            conn1.setDoOutput(true);
            conn1.setRequestProperty("Connection", "Keep-Alive");
            conn1.setUseCaches(false);
            conn1.setRequestProperty("Charset", encoding);
            conn1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn1.setRequestProperty("Content-Length", String.valueOf(entity.length));
//            conn1.setRequestProperty("Authorization", "Bearer " + token);
            conn1.setRequestProperty("clientType", "1");
            conn1.setRequestProperty("token", HemaUser.token);
            conn1.setRequestProperty("X-type", "1");

            if (sessionID != null) {
                conn1.setRequestProperty("Cookie", sessionID);
            }

            DataOutputStream dos = new DataOutputStream(conn1.getOutputStream());
            dos.write(entity);
            dos.flush();
            dos.close();
            String cookie = conn1.getHeaderField("set-cookie");
            if (cookie != null) {
                sessionID = cookie.substring(0, cookie.indexOf(";"));
            }


            int code = conn1.getResponseCode();
            XtomLogger.d("BaseHttpUtil", "The responsecode is " + code);
            InputStream in = code == 200 ? conn1.getInputStream() : null;
            String indata = XtomStreamUtil.iputStreamToString(in);
            XtomLogger.d("BaseHttpUtil", "The back data is \n" + indata);
            if (conn != null) {
                conn1.disconnect();
            }

            return indata;
        } catch (Exception var11) {
            if (conn != null) {
                conn1.disconnect();
            }

            throw new HttpException(var11);
        }
    }

    private static InputStream get(HttpURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        XtomLogger.d("BaseHttpUtil", "The responsecode is " + code);
        return code == 200 ? conn.getInputStream() : null;
    }

    public static void clearSession() {
        sessionID = null;
    }
}
