package com.watsonapi_learning.wheee.watsonapi_learning;

/**
 * Created by Wheee on 2016/11/4.
 */

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.acorn.cakeviewwidget.R;

/**
 * @author spring sky<br>
 * Email :vipa1888@163.com<br>
 * QQ: 840950105<br>
 * @version 创建时间：2012-11-22 上午9:20:03
 * 说明：主要用于选择文件操作
 */

public class SelectPicActivity extends Activity implements OnClickListener{

    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    /***
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "photo_path";

    private static final String TAG = "SelectPicActivity";

    private LinearLayout dialogLayout;
    private Button takePhotoBtn,pickPhotoBtn,cancelBtn;

    /**获取到的图片路径*/
    private String picPath;

    private Intent lastIntent ;

    private Uri photoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pic_layout);
        initView();
    }
    /**
     * 初始化加载View
     */
    private void initView() {
        dialogLayout = (LinearLayout) findViewById(R.id.dialog_layout);
        dialogLayout.setOnClickListener(this);
        takePhotoBtn = (Button) findViewById(R.id.btn_take_photo);
        takePhotoBtn.setOnClickListener(this);
        pickPhotoBtn = (Button) findViewById(R.id.btn_pick_photo);
        pickPhotoBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(this);

        lastIntent = getIntent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_layout:
                finish();
                break;
            case R.id.btn_take_photo:
                takePhoto();
                break;
            case R.id.btn_pick_photo:
                pickPhoto();
                break;
            default:
                finish();
                break;
        }
    }

    /**
     * 拍照获取图片
     */
    private void takePhoto() {
//执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if(SDState.equals(Environment.MEDIA_MOUNTED))
        {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
/***
 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
 * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
 */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
/**-----------------*/
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        }else{
            Toast.makeText(this,"内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    /***
     * 从相册中取图片
     */
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
        {
            doPhoto(requestCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode,Intent data)
    {
        // 检查摄像头权限是否已经有效
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 摄像头权限还未得到用户的同意
            requestCameraPermission();
        } else {
            // 摄像头权限以及有效，显示摄像头预览界面

            if (requestCode == SELECT_PIC_BY_PICK_PHOTO) //从相册取图片，有些手机有异常情况，请注意
            {
                if (data == null) {
                    Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                    return;
                }
                photoUri = data.getData();
                if (photoUri == null) {
                    Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            String[] pojo = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                cursor.moveToFirst();
                picPath = cursor.getString(columnIndex);
            
            }else {
                picPath = "/storage/emulated/0/115yun/tupian/test.jpg";
            }//   cursor.close();
            Log.i(TAG, "cursor = " + cursor);
            Log.i(TAG, "imagePath = " + picPath);
            if (picPath != null && (picPath.endsWith(".png") || picPath.endsWith(".PNG") || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
                lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
                setResult(Activity.RESULT_OK, lastIntent);
                finish();
            } else {
                Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void requestCameraPermission() {
        // 摄像头权限已经被拒绝
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // 如果用户已经拒绝劝降，那么提供额外的权限说明           
 
        } else {
            // 摄像头还没有被拒绝，直接申请
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }
}