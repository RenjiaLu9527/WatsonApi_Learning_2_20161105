package com.watsonapi_learning.wheee.watsonapi_learning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acorn.cakeviewwidget.R;

import java.util.Random;

/**
 * Created by Wheee on 2016/7/25.
 */
public class MyDialog extends Activity {
    static Button bn_yes;
    static Button bn_no;
    static TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog_layout);

        bn_yes = (Button) findViewById(R.id.bn_yes);
        bn_no = (Button) findViewById(R.id.bn_no);
        tv_content = (TextView) findViewById(R.id.tv_content);

        Intent i = this.getIntent();
        final String time = i.getStringExtra("time");
        tv_content.setText("本次共蹲了 " + time + " 继续保持~~~\n分享一下？\n");
        bn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str =  "本次蹲坑时长 " + time + " 击败了全国 " + (new Random().nextInt(89) + 10) + "%的人！继续保持健康的生活方式哦~~"+"\r\n"+"--来自[蹲坑进行时v1.01] id["+(new Random().nextInt(8999) + 1000)+"]";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra("time",str);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "title");
                intent.putExtra(Intent.EXTRA_TEXT, str);
                startActivity(Intent.createChooser(intent, "title"));
            }
        });
        bn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
