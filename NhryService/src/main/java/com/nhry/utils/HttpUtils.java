package com.nhry.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

public class HttpUtils {


    public static byte[] streamToByte(InputStream inputstream) throws Exception {
        byte bys[] = new byte[1024];
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while ((len = inputstream.read(bys)) != -1) {
            out.write(bys, 0, len);
        }
        return out.toByteArray();
    }


    public static String getLocalIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String request(String uri, Map<String, Object> params) throws IOException {
        return request(uri, params, "utf-8");
    }

    /**
     * @param uri
     * @param params
     * @return
     * @throws IOException
     *             ����һ��http ���󷽷�Ϊpost
     *
     */
    public static String request(String uri, Map<String, Object> params, String encoding) throws IOException {
        long currentTime = System.currentTimeMillis();
        URL postUrl = new URL(uri);
        // ������
        HttpURLConnection connection = (HttpURLConnection)postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);

        connection.setRequestMethod("POST");

        connection.setUseCaches(false);

        connection.setInstanceFollowRedirects(true);

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        StringBuffer content = new StringBuffer();
        Object[] arrays = params.entrySet().toArray();
        for (int index = 0; index < arrays.length; index++) {
            Entry<String, Object> value = (Entry<String, Object>)arrays[index];
            Object target = value.getValue();
            if (target instanceof Collection) {
                Object array[] = ((Collection)target).toArray();
                for (int i = 0; i < array.length; i++) {
                    content.append(value.getKey() + "=" + URLEncoder.encode(array[i].toString(), encoding));
                    if (i <= array.length - 1) {
                        content.append("&");
                    }
                }
                continue;
            } else {
                content.append(value.getKey() + "=" +
                               URLEncoder.encode(target == null ? "" : target.toString(), encoding));
            }

            if (index <= arrays.length - 1) {
                content.append("&");
            }
        }

        out.writeBytes(content.toString());
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuffer result = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        connection.disconnect();
        return result.toString();
    }


    public static byte[] request2(String uri, Map<String, Object> params, String encoding) throws IOException {
        long currentTime = System.currentTimeMillis();
        URL postUrl = new URL(uri);
        // ������
        HttpURLConnection connection = (HttpURLConnection)postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);

        connection.setRequestMethod("POST");

        connection.setUseCaches(false);

        connection.setInstanceFollowRedirects(true);

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        StringBuffer content = new StringBuffer();
        Object[] arrays = params.entrySet().toArray();
        for (int index = 0; index < arrays.length; index++) {
            Entry<String, Object> value = (Entry<String, Object>)arrays[index];
            Object target = value.getValue();
            if (target instanceof Collection) {
                Object array[] = ((Collection)target).toArray();
                for (int i = 0; i < array.length; i++) {
                    content.append(value.getKey() + "=" + URLEncoder.encode(array[i].toString(), encoding));
                    if (i <= array.length - 1) {
                        content.append("&");
                    }
                }
                continue;
            } else {
                content.append(value.getKey() + "=" +
                               URLEncoder.encode(target == null ? "" : target.toString(), encoding));
            }

            if (index <= arrays.length - 1) {
                content.append("&");
            }
        }

        out.writeBytes(content.toString());
        out.flush();
        out.close();
        InputStream input = connection.getInputStream();
        int len = 0;
        byte[] bs = new byte[2048];
        ByteArrayOutputStream outbyte = new ByteArrayOutputStream();
        while ((len = input.read(bs)) != -1) {
            outbyte.write(bs, 0, len);
        }
        connection.disconnect();
        return outbyte.toByteArray();
    }


    public static void main(String[] args) throws Exception {
    	String msg = HttpUtils.request("http://test.nhry-dev.com/xiexiaojun/api/v1/dic/items/1015", new HashMap<String,Object>());
    	System.out.println(msg);
    }
}
