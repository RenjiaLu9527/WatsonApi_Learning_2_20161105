package com.watsonapi_learning.wheee.watsonapi_learning;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 上传工具类
 *
 * @author spring sky<br>
 *         Email :vipa1888@163.com<br>
 *         QQ: 840950105<br>
 *         支持上传文件和参数
 */
public class UploadUtil {
    private static UploadUtil uploadUtil;
    private static final String BOUNDARY = "*****";// UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

    private UploadUtil() {

    }

    /**
     * 单例模式获取上传工具类
     *
     * @return
     */
    public static UploadUtil getInstance() {
        if (null == uploadUtil) {
            uploadUtil = new UploadUtil();
        }
        return uploadUtil;
    }

    private static final String TAG = "UploadUtil";
    private int readTimeOut = 10 * 1000; // 读取超时
    private int connectTimeout = 10 * 1000; // 超时时间
    /***
     * 请求使用多长时间
     */
    private static int requestTime = 0;

    private static final String CHARSET = "utf-8"; // 设置编码

    /***
     * 上传成功
     */
    public static final int UPLOAD_SUCCESS_CODE = 1;
    /**
     * 文件不存在
     */
    public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
    /**
     * 服务器出错
     */
    public static final int UPLOAD_SERVER_ERROR_CODE = 3;
    protected static final int WHAT_TO_UPLOAD = 1;
    protected static final int WHAT_UPLOAD_DONE = 2;

    /**
     * android上传文件到服务器
     *
     * @param filePath   需要上传的文件的路径
     * @param fileKey    在网页上<input type=file name=xxx/> xxx就是这里的fileKey
     * @param RequestURL 请求的URL
     */
    public void uploadFile(String filePath, String fileKey, String RequestURL,
                           Map<String, String> param) {
        if (filePath == null) {
            sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE, "文件不存在");
            return;
        }
        try {
            File file = new File(filePath);
            uploadFile(file, fileKey, RequestURL, param);
        } catch (Exception e) {
            sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE, "文件不存在");
            e.printStackTrace();
            return;
        }
    }

    /**
     * android上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param fileKey    在网页上<input type=file name=xxx/> xxx就是这里的fileKey
     * @param RequestURL 请求的URL
     */
    public void uploadFile(final File file, final String fileKey,
                           final String RequestURL, final Map<String, String> param) {

        if (file == null || (!file.exists())) {
            sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE, "文件不存在");
            return;
        }

        Log.i(TAG, "请求的URL=" + RequestURL);
        Log.i(TAG, "请求的fileName=" + file.getName());
        Log.i(TAG, "请求的fileKey=" + fileKey);
        new Thread(new Runnable() { //开启线程上传文件
            @Override
            public void run() {
                toUploadFile(file, fileKey, RequestURL, param);
            }
        }).start();

    }

    private void toUploadFile(File file, String fileKey, String RequestURL,
                              Map<String, String> param) {
        String result = null;
        requestTime = 0;

        long requestTime = System.currentTimeMillis();
        long responseTime = 0;
        String str = "https://watson-api-explorer.mybluemix.net/visual-recognition/api/v3/detect_faces?version=2016-05-20";
        // 
        String filePath = "/storage/emulated/0/115yun/tupian/test.jpg";
        String fileName = "myphoto";
        try {
            URL url = new URL(str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("FileName", fileName);
            connection.setRequestProperty("content-type", "text/html");

            BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());

            // 读取文件上传到服务器
            //file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int numReadByte = 0;
            int curLen = 0;
            while ((numReadByte = fileInputStream.read(bytes, 0, 1024)) > 0) {
                curLen += numReadByte;
                out.write(bytes, 0, numReadByte);
                onUploadProcessListener.onUploadProcess(curLen);
            }

            out.flush();
            fileInputStream.close();
            int resCode = connection
                    .getResponseCode();
            // 读取URLConnection的响应
            // DataInputStream in = new
            // DataInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            String line = "";
            String RespondJson = resCode+"&&";
            while ((line = reader.readLine()) != null) {
                // line = new String(line.getBytes(), "utf-8");
                RespondJson += (line + "\n");
                System.out.println(line);
            }
            reader.close();
            result = RespondJson.toString();
            Log.e(TAG, "result : " + result);
            sendMessage(UPLOAD_SUCCESS_CODE, result);
            connection.disconnect();
            return;


        } catch (MalformedURLException e) {
            sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
            sendMessage(UPLOAD_SERVER_ERROR_CODE, "上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        }
    }


    /**
     * 发送上传结果
     *
     * @param responseCode
     * @param responseMessage
     */
    private void sendMessage(int responseCode, String responseMessage) {
        onUploadProcessListener.onUploadDone(responseCode, responseMessage);
    }

    /**
     * 下面是一个自定义的回调函数，用到回调上传文件是否完成
     *
     * @author shimingzheng
     */
    public static interface OnUploadProcessListener {
        /**
         * 上传响应
         *
         * @param responseCode
         * @param message
         */
        void onUploadDone(int responseCode, String message);

        /**
         * 上传中
         *
         * @param uploadSize
         */
        void onUploadProcess(int uploadSize);

        /**
         * 准备上传
         *
         * @param fileSize
         */
        void initUpload(int fileSize);
    }

    private OnUploadProcessListener onUploadProcessListener;

    public void setOnUploadProcessListener(
            OnUploadProcessListener onUploadProcessListener) {
        this.onUploadProcessListener = onUploadProcessListener;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 获取上传使用的时间
     *
     * @return
     */
    public static int getRequestTime() {
        return requestTime;
    }

    public static interface uploadProcessListener {

    }

}