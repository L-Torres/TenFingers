package com.beijing.tenfingers.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.AlbumActivity;
import com.beijing.tenfingers.activity.ValueActivity;
import com.beijing.tenfingers.adapter.ValueImageAdapter;
import com.beijing.tenfingers.adapter.ZizhiImageAdapter;
import com.beijing.tenfingers.bean.FileUploadResult;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.view.BottomThreeDialog;
import com.beijing.tenfingers.view.SwImageForFragmentWay;
import com.beijing.tenfingers.view.SwImageWay;
import com.beijing.tenfingers.view.ToastUtils;
import com.beijing.tenfingers.view.XtomGridView;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

import static android.app.Activity.RESULT_OK;

public class ServiceProFragment extends MyBaseFragment implements View.OnClickListener {

    public ServiceProFragment() {
        super();
    }

    public static ServiceProFragment getInstance(int position) {
        ServiceProFragment fragment = new ServiceProFragment();
        return fragment;
    }

    private BottomThreeDialog dialog;
    private SwImageForFragmentWay imageWay;
    private XtomGridView gridview;
    private ZizhiImageAdapter imageAdapter;
    private ArrayList<String> images = new ArrayList<String>();
    private EditText ed_product, ed_phone, ed_name;
    private TextView tv_join;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_servicepro);
        super.onCreate(savedInstanceState);
        imageWay = new SwImageForFragmentWay(this, 1, 2) {
            @Override
            public void album() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, albumRequestCode);
                    return;
                }
                // 注意：若不重写该方法则使用系统相册选取(对应的onActivityResult中的处理方法也应不同)
                Intent it = new Intent(getActivity(), AlbumActivity.class);
                it.putExtra("limitCount", 1 - images.size());// 图片选择张数限制
                startActivityForResult(it, albumRequestCode);
            }
        };
        imageAdapter = new ZizhiImageAdapter(getActivity(), rootView, images,this);
        gridview.setAdapter(imageAdapter);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:
            case FILE_UPLOAD:
                showProgressDialog("");
                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:
            case FILE_UPLOAD:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:
                ToastUtils.show("提交成功，稍后将有客服人员联系您！");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.GO_TO_JOIN));
                    }
                },2000);

                break;
            case FILE_UPLOAD:
                HemaArrayResult<FileUploadResult> fResult = (HemaArrayResult<FileUploadResult>) baseResult;
                FileUploadResult fileUploadResult=fResult.getObjects().get(0);
                getNetWorker().my_join(s_name,s_mobile,"3","1","商品服务商","未知",fileUploadResult.getItem1(),products);
                break;
        }
    }

    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case MY_JOIN:
            case FILE_UPLOAD:
                ToastUtils.show(baseResult.getError_message());
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        tv_join= (TextView) findViewById(R.id.tv_join);
        gridview= (XtomGridView) findViewById(R.id.gridview);
        ed_product = (EditText) findViewById(R.id.ed_product);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_name = (EditText) findViewById(R.id.ed_name);
        rootView = findViewById(R.id.father);

    }

    @Override
    protected void setListener() {
        tv_join.setOnClickListener(this);

    }

    public void showImageWay() {
        dialog = new BottomThreeDialog(getActivity());
        dialog.setTop("图片选择");
        dialog.setTopButtonText("拍照");
        dialog.setMiddleButtonText("从相册中选择");
        dialog.setButtonListener(new BottomThreeDialog.OnButtonListener() {
            @Override
            public void onTopButtonClick(BottomThreeDialog dialog) {
                // TODO Auto-generated method stub
                dialog.cancel();
                imageWay.camera();
            }
            @Override
            public void onMiddleButtonClick(BottomThreeDialog dialog) {
                // TODO Auto-generated method stub
                dialog.cancel();
                imageWay.album();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);   //this
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case 1:// 相册选择图片
                // albumSystem(data);
                album(data);
                break;
            case 2:// 拍照
                camera();
                break;

        }
    }

    private void camera() {
        String imagepath = this.imageWay.getCameraImage();
        new CompressPicTask().execute(imagepath);
    }

    // 自定义相册选择时处理方法
    private void album(Intent data) {
        if (data == null)
            return;
        ArrayList<String> imgList = data.getStringArrayListExtra("images");
        if (imgList == null)
            return;
        for (String img : imgList) {
            log_i(img);
            new CompressPicTask().execute(img);
        }
    }
    private String s_name="";
    private String s_mobile="";
    private String products="";
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_join:
                s_name=ed_name.getText().toString().trim();
                s_mobile=ed_phone.getText().toString().trim();
                if(isNull(s_name)){
                    ToastUtils.show("请填写姓名！");
                    return;
                }
                if(isNull(s_mobile)){
                    ToastUtils.show("请填联系电话！");
                    return;
                }
                products=ed_product.getText().toString().trim();
                if(isNull(products)){
                    ToastUtils.show("请填写能能提供的产品！");
                    return;
                }
                if(images.size()==0){
                    ToastUtils.show("请上传您的相关资质！");
                    return;
                }

                getNetWorker().fileUpload(MyApplication.getInstance().getUser().getToken(),images.get(0));

                break;
        }

    }

    /**
     * 压缩图片
     */
    private class CompressPicTask extends AsyncTask<String, Void, Integer> {
        String compressPath;

        @Override
        protected Integer doInBackground(String... params) {
            try {
                String path = params[0];
//                String savedir = XtomFileUtil.getTempFileDir(mContext);
//                compressPath = XtomImageUtil.compressPictureDepthWithSaveDir(
//                        path, MyConfig.IMAGE_HEIGHT, MyConfig.IMAGE_WIDTH,
//                        MyConfig.IMAGE_QUALITY, savedir, mContext);
//                compressPath = BaseUtil.getScaledImage(mContext, path);
                compressPath = path;
                return 0;
            } catch (Exception e) {
                return 1;
            }
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog("正在压缩图片");
        }

        @Override
        protected void onPostExecute(Integer result) {
            cancelProgressDialog();
            switch (result) {
                case 0:
                    images.add(compressPath);
                    imageAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    showTextDialog("图片压缩失败");
                    break;
            }
        }
    }

}
