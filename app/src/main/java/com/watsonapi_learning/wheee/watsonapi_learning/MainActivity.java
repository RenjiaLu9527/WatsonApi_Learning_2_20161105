package com.watsonapi_learning.wheee.watsonapi_learning;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acorn.cakeviewwidget.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {



    private static String url_str_test = "https://watson-api-explorer.mybluemix.net/tone-analyzer/api/v3/tone?"
            + "version=2016-05-19" + "&text=what%20the%20fuck!";
    private static String url_str = "https://watson-api-explorer.mybluemix.net/tone-analyzer/api/v3/tone?version=2016-05-19&text=it%20beautiful!";
    private static String url_str_Base = "https://watson-api-explorer.mybluemix.net/tone-analyzer/api/v3/tone?version=2016-05-19&text=";
    private CakeSurfaceView cakeSurfaceView;

    String Ttl_Dt_Emotion[] = new String[]{"Anger&-生气 恼怒度-", "Disgust&-反感 厌恶度-", "Fear&-害怕 敬畏度-", "Joy&-开心 快乐度-",
            "Sadness&-悲痛 悲伤度-"};
    String Ttl_Dt_NULL[] = new String[]{"待显示1&-程度-", "待显示2&-程度-", "待显示3&-程度-", "待显示4&-程度-"};
    double arNULL[] = new double[]{0.1, 0.1, 0.1, 0.1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //载入动画
        final Animation rotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        final Animation fan_rotate = AnimationUtils.loadAnimation(this, R.anim.fan_anim_rotate);
        final Animation translate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        final Animation scale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        final Animation alpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        
        Button bn_parse = (Button) findViewById(R.id.btn);
        final EditText et_text = (EditText) findViewById(R.id.edt);
        //   final TextView tv_title = (TextView) findViewById(R.id.tv_title);
        final Button btn_t2s = (Button) findViewById(R.id.btn_t2s);

        final Handler mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 0) {
                    // 与 php 传输数据流时出错
                    //tv_title.setText("[!]错误:android-php网络出错 请稍后重试\n耗时:" + msg.getWhen());
                    Toast.makeText(MainActivity.this, "网络出错 请稍后重试", Toast.LENGTH_LONG).show();
                    Log.i("####mainHandler", "[!]网络出错 请稍后重试");
                } else if (msg.what != 0 && !msg.obj.equals(null)) {
                    // 成功
                    // Toast.makeText(MainActivity.this, "succeed!\n" + msg.obj.toString(), Toast.LENGTH_LONG).show();
                    Log.i("########", " 成功返回 ");
                    String MSGOBJ_ARR[] = (msg.obj.toString()).split("&&_TEXT_&&");
                    msg.obj = MSGOBJ_ARR[0];
                    Log.i("########", "  msg.obj " + MSGOBJ_ARR[0]);
                    String text = MSGOBJ_ARR[1];
                    Log.i("########", " text= " + MSGOBJ_ARR[1]);

                    // ParseJson pj = new ParseJson(msg.obj.toString());
                    // Document_tone dt = pj.Parse();

                    //  double d=Double.valueOf(str).doubleValue();
                    String s = msg.obj.toString();
                    int i = 0;
                    double arr_score[] = new double[13];
                    while (s.indexOf("\"score\":") >= 0) {
                        s = s.substring((s.indexOf("\"score\":") + 8), s.length());
                        Log.i("########", " s.substring 之后 " + s);
                        String num = s.substring(0, (s.indexOf(",")));
                        Log.i("########", "num 等于 " + num);
                        arr_score[i++] = Double.parseDouble(num);
                        Log.i("########", " arr_score[" + (i - 1) + "] 等于 " + arr_score[i - 1]);
                    }

//                    Iterator<Tones> it_emotion = dt.getTone_categories().get(0).getTones().iterator();
//                    Iterator<Tones> it_language = dt.getTone_categories().get(1).getTones().iterator();
//                    Iterator<Tones> it_social = dt.getTone_categories().get(2).getTones().iterator();
//                    double arEmotion[] = new double[5];
//                    double arLanguage[] = new double[5];
//                    double arSocial[] = new double[5];
//                    int i = 0;
//                    for (; it_emotion.hasNext() && it_social.hasNext(); ) {
//                        arEmotion[i] = it_emotion.next().getScore();
//                        if (i < 3)
//                            arLanguage[i] = it_language.next().getScore();
//                        arSocial[i] = it_social.next().getScore();
//                        i++;
//                    }
                    // Init(Ttl_Dt_Emotion, arEmotion, arEmotion.length);
                    // Init(Ttl_Dt_Emotion, arr_score, 5);//只显示  Emotion
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putDoubleArray("EMOTION", arr_score);
                    bundle.putString("JSON_RE", msg.obj.toString());
                    bundle.putString("TEXT", text);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
            }// handleMessage_end
        };

        bn_parse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("#########", "" + "按钮被点击" + url_str);

                // TODO 自动生成的方法存根
                String text = et_text.getText().toString();
                String text_copy = text = text.length() <= 1 ? "hi!nice to meet you!" : text;
                try {
                    text = url_str_Base + URLEncoder.encode(text, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                    Log.i("#########", "" + "输入文字转换出错" + url_str);

                } // 编码处理 完毕
              //  Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
              
                new netThread(text, mhandler, text_copy).start();
                //new netThread(url_str_test, mhandler).start();


            }
        });// bn_parse.setOnClickListener_end
        btn_t2s.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Text2speechActivity.class);
                Bundle bundle = new Bundle();

                String text = et_text.getText().toString();
                String text_copy = text = text.length() <= 1 ? "hi!nice to meet you!" : text;
                try {
                    text = URLEncoder.encode(text, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                    Log.i("#########", "" + "bnt_t2s输入文字转换出错" + url_str);

                }
                bundle.putString("URL", text);
                intent.putExtras(bundle);
                Toast.makeText(MainActivity.this, "WatsonAPI_ForLearn_beta2.1无法解析\n正在带您跳转到默认浏览器", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });//btn_t2s_end
        
        //btn_3 上传图片
 
        final Button btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.startAnimation(scale);
        btn_3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_3.clearAnimation();
                btn_3.startAnimation(rotate);
             
                
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PhotoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });

        final Button bn_translator = (Button)findViewById(R.id.btn_fanyi);
        bn_translator.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TranslatorActivity.class);
                Bundle bundle = new Bundle();

                String text = et_text.getText().toString();
                String text_copy = text = text.length() <= 1 ? "hi!nice to meet you!" : text;
                try {
                    text = URLEncoder.encode(text, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                    Log.i("#########", "" + "bnt_translator输入文字转换出错" + url_str);

                }
                bundle.putString("URL", text);
                intent.putExtras(bundle);
               // Toast.makeText(MainActivity.this, "WatsonAPI_ForLearn_beta2.1无法解析\n正在带您跳转到默认浏览器", Toast.LENGTH_SHORT).show();
                startActivity(intent);
               
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
            }
        });
    }// onCreate_end


    /**
     * 发起http请求获取返回结果
     *
     * @param req_url 请求地址
     * @return
     */
    public static String httpRequest(String req_url) {
        StringBuffer buffer = new StringBuffer();
        Log.i("#########", "" + "进入httpRequest函数" + req_url);

        try {
            URL url = new URL(req_url);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());

            Log.i("####!!!!!!", "出错！！！！！！！！！！！" + e.toString());
        }

        Log.i("####httpRequest即将返回", "httpRequest返回值:" + buffer.toString());
        return buffer.toString();
    }// httpRequest_end
    // 线程处理耗时操作

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // setContentView(R.layout.activity_main);
    }

    //创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.action_items, menu);
        return super.onCreateOptionsMenu(menu);

    }
    //监听菜单项

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Toast.makeText(this, "你选择了 " + item.getTitle(), Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.item_1: {//反馈
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Fankui.class);
                startActivity(intent);
                break;
            }
            case R.id.item_2: {
                //设置
                Toast.makeText(MainActivity.this, "设置 " + "这么简单的程序还需要设置什么呢~~~", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.item_3: {
                //版本信息
                Toast.makeText(MainActivity.this, "版本信息: " + "WatsonAPI_ForLearn_beta2.1", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.item_4: {
                startActivity((new Intent().setClass(MainActivity.this, MyInfo.class)));
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
