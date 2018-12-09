package com.bwie.yinzhiyou.denglu.utils;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetUtils {
    private static NetUtils instance;
    private final Gson gson;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private NetUtils(){
        gson = new Gson();
    }
    public static NetUtils getInstance(){
    if (instance==null){
        instance=new NetUtils();
    }
        return instance;
    }
    public interface CallBack<E>{
        void onSuccess(E e);
    }
    public void getResult(final String strl, final Class calzz, final CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Object result = getResult(strl, calzz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(result);
                    }
                });
            }
        }).start();
    }
    public <E> E getResult(String strl,Class calzz){

        return (E) gson.fromJson(getResult(strl),calzz);
    }
    public String getResult(String strl){
        String result="";
        try {
            URL url=new URL(strl);
          HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
          urlConnection.setRequestMethod("GET");
          urlConnection.setConnectTimeout(5000);
          urlConnection.setReadTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode==200){
                result=Stream2String(urlConnection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String Stream2String(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();
        for (String tem=bufferedReader.readLine();tem!=null;tem=bufferedReader.readLine()){
            stringBuffer.append(tem);
        }
        return stringBuffer.toString();
    }
}
