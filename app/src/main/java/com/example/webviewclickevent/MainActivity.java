package com.example.webviewclickevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
//    private final Handler handler = new Handler(this);
    WebView webView;
    WebViewClient client;
    private static final String TAG = "MainActivity";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
//        webView.setOnTouchListener(this);
//        client = new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                handler.sendEmptyMessage(CLICK_ON_URL);
//                return false;
//            }
//        };
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(client);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl("https://www.gsmarena.com/samsung-phones-9.php");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webView, url);
                String testScripts1 = "var s=  document.querySelectorAll('#list-brands ul li');" +
                        "s.forEach((element,index)=> {" +
                        " var a = element.querySelector('a');" +
                        " a.setAttribute('onCLick',`androidClick('${a.getAttribute('href')}')`);" +
                        "    a.setAttribute('href','javascript:void(0);');" +
                        "});" +
                        "function androidClick(link){" +
                        "    JSBridge.GetLink(link);" +
                        "}";

                String testScripts2 = "var m = document.querySelector('#search_button');" +
                        "m.addEventListener('click', function(){" +
                        "    JSBridge.ShowToast();" +
                        "});";
                webView.evaluateJavascript(testScripts2,null);
            }
        });
        webView.addJavascriptInterface(new JSBridge (this),"JSBridge");

    }

    class JSBridge{
        private Context context;
        public JSBridge (Context ctx){
            this.context = ctx;
        }

        @JavascriptInterface
        public void ShowToast (){
            Log.d(TAG, "ShowToast: Clicked");
            Toast.makeText(context, "Burger Menu Clicked From Android", Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void GetLink (String link){
            Log.d(TAG, "GetLink: Clicked");
            Toast.makeText(context, link, Toast.LENGTH_LONG).show();
        }
    }



//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (view.getId() == R.id.webView && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
//        }
//        return false;
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }
//
//    @Override
//    public boolean handleMessage(@NonNull Message msg) {
//        if (msg.what == CLICK_ON_URL) {
//            handler.removeMessages(CLICK_ON_WEBVIEW);
//            return true;
//        }
//        if (msg.what == CLICK_ON_WEBVIEW) {
//            Toast.makeText(this, "WebView clicked", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return false;
//    }
}