package com.watsonapi_learning.wheee.watsonapi_learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.acorn.cakeviewwidget.R;

/**
 * Created by Wheee on 2016/11/5.
 */
public class TranslatorActivity extends Activity {
    WebView webView;
    //https://watson-api-explorer.mybluemix.net/language-translator/api/v2/translate?source=en&target=fr&text=hello%2Ci%20am%20a%20student
    String URL_TRANSLATOR ="https://watson-api-explorer.mybluemix.net/language-translator/api/v2/translate?source=en&target=fr&text=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translator_layout);
        webView = (WebView)findViewById(R.id.wv_tl); 
        Intent intent = getIntent();//获取用于激活它的意图对象
        Bundle bundle = intent.getExtras();
        String url = URL_TRANSLATOR + bundle.getString("URL");

   
        Log.i("######", "Translator 的url=" + url);
        webView.loadUrl(url);
     
        Toast.makeText(TranslatorActivity.this, "Only English translator to French", Toast.LENGTH_SHORT).show();
//        intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri content_url = Uri.parse(url);
//        intent.setData(content_url);
//        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//        startActivity(intent);
        
//        TextView tv_content = (TextView)findViewById(R.id.tv_content);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        String str = bundle.getString("TRANSLATOR");
//        tv_content.setText(str);
    }
}
