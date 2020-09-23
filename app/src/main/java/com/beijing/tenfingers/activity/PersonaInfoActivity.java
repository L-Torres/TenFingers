package com.beijing.tenfingers.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.Base.MyApplication;
import com.beijing.tenfingers.Base.MyConfig;
import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.bean.ClientGet;
import com.beijing.tenfingers.bean.FileUploadResult;
import com.beijing.tenfingers.bean.MyData;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.rxpermissions2.Permission;
import com.beijing.tenfingers.rxpermissions2.RxPermissions;
import com.beijing.tenfingers.until.BaseUtil;
import com.beijing.tenfingers.until.ImageUtils;
import com.beijing.tenfingers.view.BottomThreeDialog;
import com.beijing.tenfingers.view.SwImageWay;
import com.beijing.tenfingers.view.ToastDialog;
import com.bigkoo.pickerview.TimePickerView;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.ToastUtil;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.view.RoundedImageView;
import com.hemaapp.hm_FrameWork.view.ShowLargeImageView;

import org.jaaksi.pickerview.picker.TimePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;
import io.reactivex.rxjava3.functions.Consumer;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;

public class PersonaInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back, iv_right;
    private RoundedImageView iv_head;
    private TextView tv_title, tv_nickname, tv_phone, tv_sex, tv_bir;
    private final int REQUEST_CODE_PICK_IMAGE = 1;// 相册获取
    private final int REQUEST_CODE_CAPTURE_CAMEIA = 2;// 相机获取
    private final int EDIT_IMAGE = 3;// 编辑照片

    private String imagePathCamera;// 相机拍照路径
    private String tempPath;// 临时路径
    private SwImageWay imageWay;

    private int ImageWidth;
    private int ImageHeight;
    private ShowLargeImageView mView;// 展示大图
    private View ll_nick, ll_phone;
    private final int INPUT_NICKNAME = 4;// 输入昵称
    private final int INPUT_PHONE = 5;//设置电话号码
    private View ll_sex, ll_pwd, ll_address, ll_hobby, ll_bir;
    private String filename;//文件名
    private String user_photo;//压缩后的base64数据 头像
    private ClientGet clientGet;
    private Button btn_login_out;
    private LinearLayout ll_back;
    private TimePicker mTimePicker;//生日时间
    private LinearLayout ll_head;
    private BottomThreeDialog phptpDialog;
    private BottomThreeDialog sexDialog;
    private String dateStr,mouth1,day1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personalinfo);
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            imageWay = new SwImageWay(mContext, 1, 2);
        } else {
            imagePathCamera = savedInstanceState.getString("imagePathCamera");
            imageWay = new SwImageWay(mContext, 1, 2);
        }

        getNetWorker().ClientGet(MyApplication.getInstance().getUser().getToken());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (imageWay != null) {
            outState.putString("imagePathCamera", imageWay.getCameraImage());
            imagePathCamera = imageWay.getCameraImage();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {

        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {

            case UPDATE_BIRTHDAY:
            case CLIENT_LOGINOUT:
            case CLIENT_GET:
            case UPLOAD_HEADER:
                showProgressDialog("加载中...");

                break;
        }
    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case UPDATE_BIRTHDAY:
            case CLIENT_LOGINOUT:
            case CLIENT_GET:
            case UPLOAD_HEADER:
                cancelProgressDialog();
                break;
        }
    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case FILE_UPLOAD:
                HemaArrayResult<FileUploadResult> fResult = (HemaArrayResult<FileUploadResult>) baseResult;
                FileUploadResult fileUploadResult=fResult.getObjects().get(0);
                getNetWorker().myinfo_update(MyApplication.getInstance().getUser().getToken(),"4",
                        "","","",fileUploadResult.getItem1());
                break;
            case UPDATE_BIRTHDAY:
                break;
            case CLIENT_GET:
                HemaArrayResult<ClientGet> cResult = (HemaArrayResult<ClientGet>) baseResult;
                clientGet = cResult.getObjects().get(0);
                setData();
                break;
            case MY_INFO_UPDATE:
            case UPDATE_SEX:
            case UPLOAD_HEADER:
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.UPDATE_HEAD));
                ToastUtil.showLongToast(mContext, "修改成功！");
                break;
            case CLIENT_LOGINOUT:
                break;
        }
    }
    //设置个人数据
    private void setData(){
        BaseUtil.loadCircleBitmap(mContext,clientGet.getU_image_link(),R.mipmap.ic_launcher_round,iv_head);
        tv_nickname.setText(clientGet.getU_nickname());
        if(clientGet.getU_sex()!=null && !isNull(clientGet.getU_sex())){
            if("2".equals(clientGet.getU_sex())){
                tv_sex.setText("女");
            }else{
                tv_sex.setText("男");
            }
        }
        tv_bir.setText(clientGet.getU_birthday());
    }


    @Override
    protected void callBackForServerFailed(HemaNetTask netTask, HemaBaseResult baseResult) {
        MyHttpInformation information = (MyHttpInformation) netTask.getHttpInformation();
        switch (information) {
            case UPDATE_NICKNAME:
            case CLIENT_LOGINOUT:
            case UPDATE_SEX:
            case CLIENT_GET:
            case UPLOAD_HEADER:
                break;
        }
    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected boolean onKeyBack() {
        return false;
    }


    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected boolean onKeyMenu() {
        return false;
    }

    @Override
    protected void findView() {
        ll_head=findViewById(R.id.ll_head);
        tv_bir = findViewById(R.id.tv_bir);
        ll_sex = findViewById(R.id.ll_sex);
        iv_back = findViewById(R.id.iv_back);
        iv_right = findViewById(R.id.iv_right);
        tv_title = findViewById(R.id.tv_title);
        iv_head = findViewById(R.id.iv_head);
        ll_nick = findViewById(R.id.ll_nick);
        ll_phone = findViewById(R.id.ll_phone);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_phone = findViewById(R.id.tv_phone);
        tv_sex = findViewById(R.id.tv_sex);
        ll_address = findViewById(R.id.ll_address);
        ll_back = findViewById(R.id.ll_back);
        ll_bir = findViewById(R.id.ll_bir);
    }

    @Override
    protected void getExras() {

    }

    @Override
    protected void setListener() {
        tv_title.setText("个人信息");
        iv_back.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        ll_head.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        ll_nick.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        ll_sex.setOnClickListener(this);
        ll_bir.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        iv_head.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case INPUT_PHONE:
                String phone = data.getStringExtra("content");
                tv_phone.setText(phone);
                break;
            case INPUT_NICKNAME:
                String nickname = data.getStringExtra("content");
                tv_nickname.setText(nickname);
                getNetWorker().myinfo_update(MyApplication.getInstance().getUser().getToken(),"1",
                        nickname,"","","");
                break;
            case REQUEST_CODE_CAPTURE_CAMEIA:
                String imagepath = imageWay.getCameraImage();
                if (!isNull(imagepath))
                    imagePathCamera = imagepath;
                editImage(imagePathCamera, EDIT_IMAGE);
                break;
            case REQUEST_CODE_PICK_IMAGE:
                album(data);
                break;
            case EDIT_IMAGE:
                iv_head.setCornerRadius(100);
                BaseUtil.loadBitmap(tempPath, R.mipmap.ic_launcher_round, iv_head);
                user_photo = ImageUtils.bitmapToString(tempPath);
                getNetWorker().fileUpload(MyApplication.getInstance().getUser().getToken(),tempPath);
                break;
            default:
                break;
        }
    }


    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_bir://生日选择
                TimePickerView pvTime = new TimePickerView.Builder(PersonaInfoActivity.this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date2, View v) {//选中事件回调
                        String time = getTime(date2);
                        tv_bir.setText(time);
                        getNetWorker().myinfo_update(MyApplication.getInstance().getUser().getToken(),"3",
                                "","",time,"");
                    }
                })
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(20)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                        .setTitleColor(Color.BLACK)//标题文字颜色
                        .setSubmitColor(R.color.green_55)//确定按钮文字颜色
                        .setCancelColor(R.color.green_55)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.ll_sex:
                selectSex();
                break;
            case R.id.ll_nick:
                String nickName = tv_nickname.getText().toString();
                intent = new Intent(mContext,
                        InputActivity.class);
                intent.putExtra("title", "设置个人昵称");
                intent.putExtra("InputParams", nickName);
                intent.putExtra("hint", "请输入昵称");
                startActivityForResult(intent, INPUT_NICKNAME);
                changeAc();
                break;
            case R.id.ll_phone:
//                intent = new Intent(mContext, ChangePhoneSecondActivity.class);
//                startActivity(intent);
//                changeAc();
                break;
            case R.id.ll_back:
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_head:
            case R.id.ll_head:
                ImageWidth = iv_head.getWidth();
                ImageHeight = iv_head.getHeight();
                selectPhoto();
                break;
            case R.id.ll_address:
                intent = new Intent(mContext, ChangePwdActivity.class);
                startActivity(intent);
                changeAc();
                break;
        }
    }



    private void selectSex(){
        if(sexDialog==null){
            sexDialog=new BottomThreeDialog(mContext);
            sexDialog.setCancelable(true);
            sexDialog.setTop("更换性别");
            sexDialog.setTopButtonText("男");
            sexDialog.setMiddleButtonText("女");
            sexDialog
                    .setButtonListener(new BottomThreeDialog.OnButtonListener() {
                        @Override
                        public void onTopButtonClick(BottomThreeDialog dialog) {
                            dialog.cancel();
                            tv_sex.setText("男");
                            getNetWorker().myinfo_update(MyApplication.getInstance().getUser().getToken(),"2",
                                    "","1","","");
                        }

                        @Override
                        public void onMiddleButtonClick(BottomThreeDialog dialog) {
                            dialog.cancel();
                            tv_sex.setText("女");
                            getNetWorker().myinfo_update(MyApplication.getInstance().getUser().getToken(),"2",
                                    "","2","","");
                        }
                    });
        }
        sexDialog.show();
    }

    /**
     * 选择照片
     */
    private void selectPhoto() {
        if (phptpDialog == null) {
            phptpDialog = new BottomThreeDialog(mContext);
            phptpDialog.setCancelable(true);
            phptpDialog.setTop("更换头像");
            phptpDialog.setTopButtonText("拍照");
            phptpDialog.setMiddleButtonText("我的相册");
            phptpDialog
                    .setButtonListener(new BottomThreeDialog.OnButtonListener() {
                        @Override
                        public void onTopButtonClick(BottomThreeDialog dialog) {
                            dialog.cancel();
                            imageWay.camera();// 相机获取
                        }

                        @Override
                        public void onMiddleButtonClick(BottomThreeDialog dialog) {
                            dialog.cancel();
                            imageWay.album(); //相册获取
                        }
                    });
        }
        phptpDialog.show();
    }
    /* 处理图片开始 */

    private void editImage(String path, int requestCode) {
        File file = new File(path);
        startPhotoZoomFixedSize(Uri.fromFile(file), requestCode);
    }

    private void startPhotoZoomFixedSize(Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", MyConfig.IMAGE_WIDTH);
        intent.putExtra("aspectY", MyConfig.IMAGE_WIDTH);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", MyConfig.IMAGE_WIDTH);
        intent.putExtra("outputY", MyConfig.IMAGE_WIDTH);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, requestCode);
    }

    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        String savedir = XtomFileUtil.getTempFileDir(mContext);
        File dir = new File(savedir);
        if (!dir.exists())
            dir.mkdirs();
        // 保存入sdCard
        tempPath = savedir + XtomBaseUtil.getFileName() + ".jpg";// 保存路径
        File file = new File(tempPath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return file;
    }

    /**
     * 通过相册获取图片的后续处理
     *
     * @param data
     */
    private void album(Intent data) {
        if (data == null)
            return;
        Uri selectedImageUri = data.getData();
        startPhotoZoomFixedSize(selectedImageUri, 3);
    }

    /**
     * 从相册获取图片
     */
    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    /* 处理图片结束 */

    //读取相册的权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 2:
                RxPermissions cPermissions = new RxPermissions(this);
                cPermissions.requestEach(Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Throwable {
                                if (permission.granted) {
                                    // 用户已经同意该权限
                                    imageWay.camera();
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                    new ToastDialog.Builder(PersonaInfoActivity.this)
                                            .setType(ToastDialog.Type.WARN)
                                            .setMessage("没有相机权限，请添加后重试！")
                                            .show();
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』
                                    new ToastDialog.Builder(PersonaInfoActivity.this)
                                            .setType(ToastDialog.Type.WARN)
                                            .setMessage("您拒绝授权，无法使用该功能！")
                                            .show();
                                }
                            }

                        });
                break;
            case 1:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Throwable {
                                if (permission.granted) {
                                    // 用户已经同意该权限
                                    imageWay.album();
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                                    new ToastDialog.Builder(PersonaInfoActivity.this)
                                            .setType(ToastDialog.Type.WARN)
                                            .setMessage("没有相册权限，请添加后重试！")
                                            .show();
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』
                                    new ToastDialog.Builder(PersonaInfoActivity.this)
                                            .setType(ToastDialog.Type.WARN)
                                            .setMessage("您拒绝授权，无法使用该功能！")
                                            .show();
                                }
                            }

                        });

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
