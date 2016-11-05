package com.watsonapi_learning.wheee.watsonapi_learning;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class netThread extends Thread {
	private String threadInfo;
	private String urlstring;
	String TEXT = "";
	private Message msg = new Message();

	private Handler ntthrd_handler;// 未指定activity的handler

	// static String SUCCEED_FLAG_WRT = "WOK";// 操作成功后服务器的返回值 write
	// static String SUCCEED_FLAG_RD = "ROK";// read
	// static boolean issucceed = false;

	netThread(String url, Handler h,String text) {
		Log.i("#########", "" + "进入netThread构造函数" + url);

		this.urlstring = url;
		this.ntthrd_handler = h;// 指定activity
		this.TEXT = text;
	}

	@Override
	public void run() {
		Looper.prepare();
		super.run();

		threadInfo = "";
		msg.obj = null;
		msg.what = 0;// 初始化 //0-ERROR

		// 到get请求的反馈信息 Json数据
		threadInfo = MainActivity.httpRequest(urlstring).toString();
		Log.i("#########", "" + "进入netThread Run函数" + threadInfo);
		msg.what = 1;// 正常结束 全部执行完毕

		Log.i("###我是线程：netthread", "网络传输过程的信息threadInfo=" + threadInfo);
		msg.obj = new String(threadInfo+"&&_TEXT_&&"+TEXT);
		// 临时 Message,防止主线程同时访问 msg导致出错
		Message msg_copy = new Message();
		msg_copy.obj = new String(msg.obj.toString());// 新的类，不是引用
		msg_copy.what = msg.what;

		// feedback.mhandler.sendMessage(msg_copy);//发送消息 msg以引用形式被发送至
		// 主线程
		Log.i("#########", "" + "netThread Run即将发送MSG" + msg_copy.obj.toString());
		this.ntthrd_handler.sendMessage(msg_copy);
		Looper.loop();

	}
}// netthread_end