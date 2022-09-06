package com.example.part4_11;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Lab11_2Activity extends AppCompatActivity implements View.OnClickListener{

    Button lineBtn;
    Button barBtn;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab112);

        webView = findViewById(R.id.webview);
        lineBtn = findViewById(R.id.btn_chart_line);
        barBtn = findViewById(R.id.btn_chart_bar);
        lineBtn.setOnClickListener(this);
        barBtn.setOnClickListener(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/test.html");
        webView.addJavascriptInterface(new JavascriptTest(), "android");
        webView.setWebViewClient(new MyWebClient());
        webView.setWebChromeClient(new MyWebChrome());
    }

    @Override
    public void onClick(View v) {
        if (v==lineBtn)
        {
            webView.loadUrl("javascript:lineChart()");
        }
        else if (v==barBtn)
        {
            webView.loadUrl("javascript:barChart()");
        }
    }

    class JavascriptTest
    {
        @JavascriptInterface
        public String getChartData()
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            for (int i = 0; i < 14; i++)
            {
                buffer.append("["+i+","+Math.sin(i)+"]");
                Log.d("kkang", i+","+Math.sin(i));
                if (i < 13)
                    buffer.append(",");
            }
            buffer.append("]");
            return buffer.toString();
        }
    }

    class MyWebClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Toast t = Toast.makeText(Lab11_2Activity.this, url, Toast.LENGTH_SHORT);
            t.show();
            return true;
        }
    }

    class MyWebChrome extends WebChromeClient
    {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result)
        {
            Toast t = Toast.makeText(Lab11_2Activity.this, url, Toast.LENGTH_SHORT);
            t.show();
            result.confirm();
            return true;
        }
    }
}