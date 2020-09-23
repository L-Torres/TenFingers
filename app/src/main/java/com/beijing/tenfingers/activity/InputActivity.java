package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.view.ClearEditText;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

/**
 *
 * Created by Torres on 2016/11/22.
 */

public class InputActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imageQuitActivity;
    private TextView txtTitle, txtNext;
    private ClearEditText editText;
    private String title,content,hint,type;

    private LinearLayout ll_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_input);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onEventMainThread(EventBusModel event) {

    }


    @Override
    protected void callBeforeDataBack(HemaNetTask hemaNetTask) {

    }

    @Override
    protected void callAfterDataBack(HemaNetTask hemaNetTask) {

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask hemaNetTask, HemaBaseResult hemaBaseResult) {

    }

    @Override
    protected void callBackForServerFailed(HemaNetTask hemaNetTask, HemaBaseResult hemaBaseResult) {

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask hemaNetTask, int i) {

    }

    @Override
    protected void findView() {
        imageQuitActivity = (ImageView) findViewById(R.id.iv_back);
        txtNext = (TextView) findViewById(R.id.tv_right);
        txtTitle = (TextView) findViewById(R.id.tv_title);
        txtTitle.setText(title);
        editText = (ClearEditText) findViewById(R.id.editText);
        editText.setText(content);
        ll_back=findViewById(R.id.ll_back);
        editText.setHint(hint);
    }

    @Override
    protected void getExras() {
        title=mIntent.getStringExtra("title");
        content=mIntent.getStringExtra("InputParams");
        hint=mIntent.getStringExtra("hint");
        type=mIntent.getStringExtra("type");
    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        imageQuitActivity.setOnClickListener(this);
        txtNext.setOnClickListener(this);
        txtNext.setOnClickListener(this);
        txtNext.setVisibility(View.VISIBLE);
        if("请输入电话号码".equals(hint)){
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        if("设置个人昵称".equals(title)){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    content=s.toString();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!isNull(s.toString())){

                        editText.setSelection(s.toString().length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if(s.length()>10){

                        String temp=s.subSequence(0,10).toString();
                        editText.setText(temp);
//                        ToastUtil.showLongToast(mContext,"昵称长度不超过10个字！");

                    }

                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
            case R.id.iv_back:
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                finishAc(mContext);
            break;
            case R.id.tv_right:
                if("设置个人昵称".equals(title)){
                  int  length=editText.getText().toString().trim().length();
                  if(length==0){
//                      ToastUtil.showLongToast(mContext,"请输入昵称！");
                      return;
                  }
                  if(length>10 || length==0){
//                      ToastUtil.showLongToast(mContext,"昵称长度不超过10个字！");
                      return;
                  }

                }
                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                Intent data = new Intent();
                data.putExtra("content", editText.getEditableText().toString().trim());
                setResult(RESULT_OK, data);
                finishAc(mContext);
                break;
        }
    }
}
