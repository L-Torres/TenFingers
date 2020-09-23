package com.beijing.tenfingers.activity;

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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.ValueImageAdapter;
import com.beijing.tenfingers.bean.FileUploadResult;
import com.beijing.tenfingers.bean.Order;
import com.beijing.tenfingers.bean.OrderDetail;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.view.BottomThreeDialog;
import com.beijing.tenfingers.view.SwImageWay;
import com.beijing.tenfingers.view.ToastUtils;
import com.beijing.tenfingers.view.XtomGridView;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ValueActivity  extends BaseActivity implements View.OnClickListener {
    private RatingBar rating_sender,rating_service,rating_at,rating_tec;
    private BottomThreeDialog dialog;
    private SwImageWay imageWay;
    private XtomGridView gridview;
    private ValueImageAdapter imageAdapter;
    private ArrayList<String> images = new ArrayList<String>();
    private View rootView;
    private float star=5;
    private float serviceStar=5;
    private float atitudeStar=5;
    private float techStar=5;
    private String images_value="";
    private TextView tv_right,tv_title,tv_name,tv_content;
    private LinearLayout ll_back;
    private String id="";
    private String tid="";
    private String pid="";
    private RoundedImageView iv_good,iv_tech;
    private EditText ed_value;
    private String content="";
    private int orderby = 0;
    private String replyImgPath = "";
    private String order_id;
    private OrderDetail detail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_value);
        super.onCreate(savedInstanceState);
        imageWay = new SwImageWay(mContext, 1, 2) {
            @Override
            public void album() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, albumRequestCode);
                    return;
                }
                // 注意：若不重写该方法则使用系统相册选取(对应的onActivityResult中的处理方法也应不同)
                Intent it = new Intent(mContext, AlbumActivity.class);
                it.putExtra("limitCount", 3 - images.size());// 图片选择张数限制
                startActivityForResult(it, albumRequestCode);
            }
        };
        imageAdapter = new ValueImageAdapter(mContext, rootView, images);
        gridview.setAdapter(imageAdapter);
        getNetWorker().order_detail(MyApplication.getInstance().getUser().getToken(),order_id);

    }
    private void fileUpload(String path) {
        orderby++;
        getNetWorker().fileUpload(MyApplication.getInstance().getUser().getToken(), path);
    }
    private void submit(){
        star=rating_sender.getRating();
        serviceStar=rating_service.getRating();
        atitudeStar=rating_at.getRating();
        techStar=rating_tec.getRating();
        content=ed_value.getText().toString().trim();
        if(images.size()>0){
           fileUpload(images.get(0));
        }else{
            getNetWorker().order_comment_create(MyApplication.getInstance().getUser().getToken(),order_id,
                    detail.getT_id(),detail.getP_id(),String.valueOf(star),content,replyImgPath,
                    String.valueOf(serviceStar),String.valueOf(atitudeStar),
                    String.valueOf(techStar));

        }
//        String token, String id, String tid, String pid,
//                String star, String content, String images,String serviceStar,
//                String atitudeStar,String techStar

    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case FILE_UPLOAD:
                showProgressDialog("");
                break;
        }

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case ORDER_COMMENT_CREATE:
            case FILE_UPLOAD:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case ORDER_COMMENT_CREATE:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.UPDATE_ORDER));
                ToastUtils.show("评价成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finishAc(mContext);
                    }
                },1000);
                break;
            case FILE_UPLOAD:
                HemaArrayResult<FileUploadResult> fResult = (HemaArrayResult<FileUploadResult>) baseResult;
                FileUploadResult fileUploadResult=fResult.getObjects().get(0);
                dealImageInfor(fileUploadResult);
                break;
            case ORDER_GET:
                HemaArrayResult<OrderDetail> oResult= (HemaArrayResult<OrderDetail>) baseResult;
                detail=oResult.getObjects().get(0);
                if(detail!=null){
                   setDate();
                }
                break;
        }
    }
    //拼接路径
    private void dealImageInfor(FileUploadResult fileInfor) {
        images.remove(0);
        if (isNull(replyImgPath)) {
            replyImgPath = fileInfor.getItem1();
        } else
            replyImgPath = replyImgPath+ "," + fileInfor.getItem1() ;
        if (images.size() > 0) {
            fileUpload(images.get(0));
        } else {
            getNetWorker().order_comment_create(MyApplication.getInstance().getUser().getToken(),order_id,
                    detail.getT_id(),detail.getP_id(),String.valueOf(star),content,replyImgPath,
                    String.valueOf(serviceStar),String.valueOf(atitudeStar),
                    String.valueOf(techStar));
        }
    }
    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        super.callBackForServerFailed(netTask, baseResult);
        MyHttpInformation information= (MyHttpInformation) netTask.getHttpInformation();
        switch (information){
            case ORDER_COMMENT_CREATE:
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
        iv_tech=findViewById(R.id.iv_tech);
        ed_value=findViewById(R.id.ed_value);
        tv_content=findViewById(R.id.tv_content);
        tv_name=findViewById(R.id.tv_name);
        iv_good=findViewById(R.id.iv_good);
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        tv_right=findViewById(R.id.tv_right);
        gridview = findViewById(R.id.gridview);
        rating_service=findViewById(R.id.rating_service);
        rating_at=findViewById(R.id.rating_at);
        rating_tec=findViewById(R.id.rating_tec);
        rating_sender=findViewById(R.id.rating_sender);
        rootView = findViewById(R.id.father);
    }

    private void setDate(){

        BaseUtil.loadBitmap(mContext,detail.getT_image_link(),R.mipmap.icon_service,iv_tech,true);
        BaseUtil.loadBitmap(mContext,detail.getP_image_link(),R.mipmap.icon_service,iv_good,true);
        tv_name.setText(detail.getP_name());
        tv_content.setText(detail.getP_desc());
    }
    @Override
    protected void getExras() {
        order_id=mIntent.getStringExtra("order_id");
//        order= (Order) mIntent.getSerializableExtra("order");
    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("评价");
        tv_right.setText("提交");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
        BaseUtil.setRatingBarHeight(mContext, R.mipmap.star_y, rating_sender);
        BaseUtil.setRatingBarHeight(mContext, R.mipmap.star_y, rating_service);
        BaseUtil.setRatingBarHeight(mContext, R.mipmap.star_y, rating_at);
        BaseUtil.setRatingBarHeight(mContext, R.mipmap.star_y, rating_tec);
    }

    public void showImageWay() {
        dialog = new BottomThreeDialog(mContext);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        String imagepath = imageWay.getCameraImage();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finishAc(mContext);
                break;
            case R.id.tv_right:
                submit();
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
