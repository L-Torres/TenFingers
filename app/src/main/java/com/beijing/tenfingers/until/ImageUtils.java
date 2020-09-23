package com.beijing.tenfingers.until;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static com.beijing.tenfingers.until.BaseUtil.getBitmapOptions;


/**
 * 处理图片工具类
 */
public class ImageUtils {

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String path) {
        File var2 = new File(path);
        if(!var2.exists()) {
            BitmapFactory.Options option = getBitmapOptions(path);
            option.inJustDecodeBounds = true;
            return  BitmapFactory.decodeFile(path, option);
        } else {
            long length = var2.length();
            if(length <= 102400L) {
//                BitmapFactory.Options option = getBitmapOptions(path);
//                option.inJustDecodeBounds = true;
//                return   getBitmapOptions(path);
                return  BitmapFactory.decodeFile(path);
            } else {
                BitmapFactory.Options option = getBitmapOptions(path);
                int h = option.outHeight;
                int w = option.outWidth;

                int r = h/w;
                int inSampleSize = 0;
                if(r>3 && h>2000){
                    if(length <= 204800L){
                        return BitmapFactory.decodeFile(path, option);
                    }
                    inSampleSize = Math.round((float)length / 204800);
                }

                if(inSampleSize ==0){
                    inSampleSize = calculateInSampleSize(option, 1080, 1920);
                }

                option.inSampleSize = inSampleSize;
                option.inJustDecodeBounds = false;
                Bitmap newbmp = BitmapFactory.decodeFile(path, option);
                return newbmp;
            }
        }
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    //把bitmap转换成String
    public static String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        //这里的JPEG 如果换成PNG，那么压缩的就有600kB这样
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        Log.d("d", "压缩后的大小=" + b.length);
        String  temp=Base64.encodeToString(b, Base64.NO_WRAP);
        if(filePath.endsWith("jpg") || filePath.endsWith("jpe") || filePath.endsWith("jpeg")){

            temp="data:image_jpeg;base64,"+temp;
        }else if(filePath.endsWith("bmp")){
            temp="data:image_x-ms-bmp;base64,"+temp;
        }else if(filePath.endsWith("cod")){
            temp="data:image_cod;base64,"+temp;
        }else if(filePath.endsWith("gif")){
            temp="data:image_gif;base64,"+temp;
        }else if(filePath.endsWith("ief")){
            temp="data:image_ief;base64,"+temp;
        }else if(filePath.endsWith("png")){
            temp="data:image_png;base64,"+temp;
        }else if(filePath.endsWith("tif") || filePath.endsWith("tiff") ){
            temp="data:image_tiff;base64,"+temp;
        }else if(filePath.endsWith("wbmp")){
            temp="data:image_vnd.wap.wbmp;base64,"+temp;
        }else if(filePath.endsWith("ico")){
            temp="data:image_x-icon;base64,"+temp;
        }else if(filePath.endsWith("jng")){
            temp="data:image_x-jng;base64,"+temp;
        }else if(filePath.endsWith("svg")){
            temp="data:image_svg+xml;base64,"+temp;
        }else if(filePath.endsWith("webp")){
            temp="data:image_webp;base64,"+temp;
        }
        return temp;
//        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

}
