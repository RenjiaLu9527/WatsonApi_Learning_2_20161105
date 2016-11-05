package com.watsonapi_learning.wheee.watsonapi_learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.acorn.cakeviewwidget.R;
import com.watsonapi_learning.wheee.watsonapi_learning.CakeSurfaceView.Gravity;
import com.watsonapi_learning.wheee.watsonapi_learning.CakeSurfaceView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wheee on 2016/11/3.
 */
public class ResultActivity extends AppCompatActivity {
    private CakeSurfaceView cakeSurfaceView;
    String Ttl_Dt_Emotion[] = new String[]{"Anger&-生气 恼怒度-", "Disgust&-反感 厌恶度-", "Fear&-害怕 敬畏度-", "Joy&-开心 快乐度-",
            "Sadness&-悲痛 悲伤度-"};
    String Ttl_Dt_NULL[] = new String[]{"待显示1&-程度-", "待显示2&-程度-", "待显示3&-程度-", "待显示4&-程度-"};
    double arNULL[] = new double[]{0.1, 0.1, 0.1, 0.1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        Intent intent = getIntent();//获取用于激活它的意图对象
        Bundle bundle = intent.getExtras();
        String title_detail[] = Ttl_Dt_Emotion;
        double array[] = bundle.getDoubleArray("EMOTION");
        String json = bundle.getString("JSON_RE");
        String text = bundle.getString("TEXT");

        TextView tv_title = (TextView) findViewById(R.id.tv_restitle);
        tv_title.setText("\"" + text + "\"");
        TextView tv_log = (TextView) findViewById(R.id.tv_log);
     

        cakeSurfaceView = (CakeSurfaceView) findViewById(R.id.cakeSurfaceView2);
        List<CakeSurfaceView.CakeValue> cakeValues2 = new ArrayList<CakeSurfaceView.CakeValue>();

        double temp = array[0];
        int maxindex = 0;
        for (int i = 0; i < 5; i++) {
            cakeValues2.add(new CakeSurfaceView.CakeValue(
                    title_detail[i].substring(0, title_detail[i].indexOf("&")), (float) array[i],
                    title_detail[i].substring(title_detail[i].indexOf("&") + 1, title_detail[i].length())));
            if (temp < array[i]) {
                temp = array[i];
                maxindex = i;
            }
        }
        String yourMood = "";
        switch (maxindex){
            case 0:
                yourMood = "You are anger for sth / calm down!";
                break;
            case 1:
                yourMood = "you are disgust about sth / why dont you find another things what you like? ";
                break;
            case 2:
                yourMood = "you are fear of sth / Take your courage face to it!";
                break;
            case 3:
                yourMood = "you feel happy / yes!this is our beautiful life!";
                break;
            case 4:
                yourMood = "You look so sad / Come on! Let's do something for fun together,Forget the sad things.";
                break;
        }
        tv_log.setText("\n " + yourMood);

//        List<CakeSurfaceView.CakeValue> cakeValues2 = new ArrayList<CakeSurfaceView.CakeValue>();
//        cakeValues2.add(new CakeSurfaceView.CakeValue("猫猫猫", 12f, "详细信息"));
//        cakeValues2.add(new CakeSurfaceView.CakeValue("狗狗狗", 0f, "详细信息自动换行"));
//        cakeValues2.add(new CakeSurfaceView.CakeValue("acorn", 24f, "橡果"));
//        cakeValues2.add(new CakeSurfaceView.CakeValue("人人人", 0f));
//        cakeValues2.add(new CakeSurfaceView.CakeValue("瓜皮", 0f));
//        cakeValues2.add(new CakeSurfaceView.CakeValue("鸭嘴兽", 1f));
        cakeSurfaceView.setData(cakeValues2);
        //设置饼图信息的显示位置(目前只有bottom模式支持点击动画)
        cakeSurfaceView.setGravity(Gravity.bottom);
        //设置饼图信息与饼图的间隔(dp)
        cakeSurfaceView.setDetailTopSpacing(15);
        //设置饼图的每一项的点击事件
        cakeSurfaceView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                //Toast.makeText(ResultActivity.this, "点击:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(ResultActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }
}
