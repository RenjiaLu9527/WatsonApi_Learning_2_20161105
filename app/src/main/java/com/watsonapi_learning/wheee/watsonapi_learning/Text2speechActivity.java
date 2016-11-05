package com.watsonapi_learning.wheee.watsonapi_learning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.acorn.cakeviewwidget.R;

/**
 * Created by Wheee on 2016/11/4.
 */
public class Text2speechActivity extends AppCompatActivity {

    WebView webView;
    String URL_TEXT2SPEECH = "https://watson-api-explorer.mybluemix.net/text-to-speech/api/v1/synthesize?accept=audio%2Fogg%3Bcodecs%3Dopus&voice=en-US_MichaelVoice&text=";
    //hello%20%2Cwhat%60s%20the%20matter%20";
    String testurl = "https://watson-api-explorer.mybluemix.net/text-to-speech/api/v1/synthesize?accept=audio%2Fogg%3Bcodecs%3Dopus&voice=en-US_MichaelVoice&text=hellohello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text2speech_activity);

        Intent intent = getIntent();//获取用于激活它的意图对象
        Bundle bundle = intent.getExtras();
        String url = URL_TEXT2SPEECH + bundle.getString("URL");


        webView = (WebView) findViewById(R.id.webView);


        //webView.loadUrl("file:///android_asset/index.html");
        Log.i("######", "T2S 的url=" + url);
        //  webView.loadUrl("http://baidu.com");
        webView.loadUrl(testurl);
        Toast.makeText(Text2speechActivity.this, "Success", Toast.LENGTH_SHORT).show();
        intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        startActivity(intent);

    }


}
