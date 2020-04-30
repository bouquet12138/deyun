package com.example.common_lib.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.example.baselib.base.MVPBaseActivity;
import com.example.baselib.util.MatisseUtil;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public abstract class SelectImagePermissionActivity extends AppMvpBaseActivity {

    protected final int PERMISSIONS_READ_CONTACTS = 0;//读取本地图片
    protected final int SELECT_PICTURE = 1;//选择图片
    protected int mMaxImgNum = 1;

    /**
     * 从相册选择图片 权限
     */
    protected void selectImageAuthor() {
        List<String> permissionList = new ArrayList<>();//权限组
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);//读取权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);//写权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.CAMERA);//相机权限*/

        if (permissionList.size() != 0) {
            // 如果用户已经拒绝了当前权限,shouldShowRequestPermissionRationale返回true，此时我们需要进行必要的解释和处理
            for (String str : permissionList) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
                    Toast.makeText(this, "用户拒绝了权限，不能读取本地图片，" +
                            "请到设置界面打开", Toast.LENGTH_SHORT).show();
                    return;//返回出去
                }
            }
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), PERMISSIONS_READ_CONTACTS);//读取相册

        } else {
            selectImage();//从本地选择图片
        }
    }

    /**
     * 选择相片
     */
    protected void selectImage() {
        MatisseUtil.selectImage(this, mMaxImgNum, getResources(), SELECT_PICTURE);
    }

    /**
     * 请求权限的响应
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_READ_CONTACTS:
                // 从数组中取出返回结果
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "用户拒绝权限无法打开相册", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    selectImage();//从本地选择图片
                }
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            List<Uri> uriList = Matisse.obtainResult(data);
            List<String> stringList = Matisse.obtainPathResult(data);
            addImage(uriList, stringList);
        }
    }

    /**
     * 添加图片
     *
     * @param uriList
     */
    protected abstract void addImage(List<Uri> uriList, List<String> stringList);

}
