package com.example.part9_25;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpRequester {

    HttpTask http;

    //외부에서 HTTP 요청 필요 시 호출
    public void request(String url, HashMap<String, String> param, HttpCallback callback) {
        http = new HttpTask(url, param, callback);
        http.execute();
    }

    //외부에서 HTTP 요청 취소 시 호출
    public void cancel() {
        if(http != null)
            http.cancel(true);
    }

    private class HttpTask extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> param;
        HttpCallback callback;

        public HttpTask(String url, HashMap<String, String> param, HttpCallback callback) {
            this.url = url;
            this.param = param;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... nothing) {
            String response = "";
            String postData = "";
            PrintWriter pw = null;
            BufferedReader in = null;

            try {
                URL text = new URL(url);
                HttpURLConnection http = (HttpURLConnection) text.openConnection();
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                http.setConnectTimeout(10*1000);
                http.setReadTimeout(10*1000);
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                Log.d("httpreq", "connection setting");

                // 서버에 전송하기 위한 데이터를 웹의 Query 문자열 형식으로 변형
                if (param != null && param.size() > 0 ) {
                    Iterator<Map.Entry<String, String>> entries = param.entrySet().iterator();
                    int index = 0;

                    while (entries.hasNext()) {
                        if (index != 0) {
                            postData = postData +"&";
                        }
                        Map.Entry<String, String> mapEntry = (Map.Entry<String, String>) entries.next();
                        postData = postData + mapEntry.getKey() + "=" + URLEncoder.encode(mapEntry.getValue(),"UTF-8");
                        index++;
                    }
                    Log.d("httpreq", "Change query to string");
                    // 데이터 서버 전송
                    pw = new PrintWriter(new OutputStreamWriter(http.getOutputStream(), "UTF-8"));
                    pw.write(postData);
                    pw.flush();
                    Log.d("httpreq", "send data to server");
                }
                
                // 서버로부터 데이터 수신
                in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                response = sb.toString();
                Log.d("httpreq", "receiver data from server");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pw != null) {
                        pw.close();
                    }
                } catch (Exception e) { }

                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e) { }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //callback에 결과 데이터 전달
            this.callback.onResult(result);
        }
    }
}
