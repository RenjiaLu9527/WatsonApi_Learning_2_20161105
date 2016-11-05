package com.watsonapi_learning.wheee.watsonapi_learning;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.acorn.cakeviewwidget.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Wheee on 2016/7/25.
 */
public class Fankui extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fankui_layout);

        Button bn_submit = (Button) findViewById(R.id.bn_submit);
        final EditText et_comment = (EditText) findViewById(R.id.et_comment);
        bn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交
                RxPermissions.getInstance(Fankui.this)
                        .request(Manifest
                                .permission
                                .SEND_SMS)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            //权限通过

                                            String time = new String(et_comment.getText().toString());
                                            sendSMS("15652962191", "" + time + "\r\n手机系统信息 " + android.os.Build.MODEL);
                                            Log.i("-----", "权限通过");

                                            super.run();

                                        }
                                    }.start();
                                }
                            }
                        });
                Log.i("-----", "finish");
                finish();
            }
        });
    }

    /**
     * 直接调用短信接口发短信
     *
     * @param phoneNumber
     * @param message
     */
    public void sendSMS(String phoneNumber, String message) {
        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, null, null);
        }
    }
}
