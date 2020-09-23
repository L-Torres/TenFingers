package com.beijing.tenfingers.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

import xtom.frame.XtomObject;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomFileUtil;

/**
 * 图片路径
 * Created by Torres on 2017/3/28.
 */

public class SwImageForFragmentWay extends XtomObject {
    protected Activity mContext;// 上下文对象
    protected Fragment mFragment;// 上下文对象
    protected AlertDialog.Builder mBuilder;// 弹出对象
    protected int albumRequestCode;// 相册选择时startActivityForResult方法的requestCode值
    protected int cameraRequestCode;// 拍照选择时startActivityForResult方法的requestCode值
    protected static final String IMAGE_TYPE = ".jpg";// 图片名后缀
    protected String imagePathByCamera;// 拍照时图片保存路径

    /**
     * 创建一个选择图片方式实例
     *
     * @param mContext          上下文对象
     * @param albumRequestCode  相册选择时startActivityForResult方法的requestCode值
     * @param cameraRequestCode 拍照选择时startActivityForResult方法的requestCode值
     */
    public SwImageForFragmentWay(Activity mContext, int albumRequestCode,
                                 int cameraRequestCode) {
        this.mContext = mContext;
        this.albumRequestCode = albumRequestCode;
        this.cameraRequestCode = cameraRequestCode;
    }

    /**
     * 创建一个选择图片方式实例
     *
     * @param mFragment         上下文对象
     * @param albumRequestCode  相册选择时startActivityForResult方法的requestCode值
     * @param cameraRequestCode 拍照选择时startActivityForResult方法的requestCode值
     */
    public SwImageForFragmentWay(Fragment mFragment, int albumRequestCode,
                                 int cameraRequestCode) {
        this.mFragment = mFragment;
        this.albumRequestCode = albumRequestCode;
        this.cameraRequestCode = cameraRequestCode;
    }

    /**
     * 显示图片选择对话
     */
    public void show() {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(mContext == null ? mFragment.getActivity()
                    : mContext);
            mBuilder.setTitle("请选择");
            mBuilder.setItems(com.hemaapp.hm_FrameWork.R.array.imgway, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    click(which);
                }
            });
        }
        mBuilder.show();
    }

    private void click(int which) {
        switch (which) {
            case 0:
                album();
                break;
            case 1:
                camera();
                break;
            case 2:
                break;
        }
    }

    /**
     * 相册获取
     * 2017年4月10日
     * 加入响应式权限和适应Android7.0的特性
     */
    public void album() {
        if(mContext != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(mContext,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, albumRequestCode);
                return;
            }
        }else if(mFragment != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    (ContextCompat.checkSelfPermission(mFragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(mFragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(mFragment.getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, albumRequestCode);
                return;
            }
        }

        Intent it1 = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (mContext != null)
            mContext.startActivityForResult(it1, albumRequestCode);
        else
            mFragment.startActivityForResult(it1, albumRequestCode);
    }

    /**
     * 相机获取
     * 2017年4月10日
     * 加入响应式权限和适应Android7.0的特性
     */
    public void camera() {
        if(mContext != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
                return;
            }
        }else if(mFragment != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    (ContextCompat.checkSelfPermission(mFragment.getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(mFragment.getActivity(), new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
                return;
            }
        }

        String imageName = XtomBaseUtil.getFileName() + IMAGE_TYPE;
        Intent it3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageDir = XtomFileUtil
                .getTempFileDir(mContext == null ? mFragment.getActivity()
                        : mContext);
        imagePathByCamera = imageDir + imageName;
        File file = new File(imageDir);
        if (!file.exists())
            file.mkdir();
        // 设置图片保存路径
        File out = new File(file, imageName);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String packageName = mFragment.getActivity().getPackageName();
            uri = FileProvider.getUriForFile(mFragment.getActivity(), packageName + ".FileProvider", out);
        } else {
            uri = Uri.fromFile(out);
        }

        it3.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (mContext != null)
            mContext.startActivityForResult(it3, cameraRequestCode);
        else
            mFragment.startActivityForResult(it3, cameraRequestCode);
    }

    /**
     * 获取拍照图片路径
     *
     * @return 图片路径
     */
    public String getCameraImage() {
        return imagePathByCamera;
    }

}
