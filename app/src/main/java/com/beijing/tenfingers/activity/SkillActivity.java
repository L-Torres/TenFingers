package com.beijing.tenfingers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.Base.BaseActivity;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.until.BaseUtil;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;
import com.hemaapp.hm_FrameWork.showlargepic.ShowLargePicActivity;

import java.util.ArrayList;

public class SkillActivity  extends BaseActivity implements View.OnClickListener {


    private LinearLayout ll_back;
    private TextView tv_title;
    private ImageView iv_health,iv_skill;
    private String health="";
    private String skill="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_renzheng);
        super.onCreate(savedInstanceState);
        BaseUtil.loadBitmap(health,0,iv_health);
        BaseUtil.loadBitmap(skill,0,iv_skill);
    }
    @Override
    public void onEventMainThread(EventBusModel event) {

    }

    @Override
    protected void callBeforeDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callAfterDataBack(HemaNetTask netTask) {

    }

    @Override
    protected void callBackForServerSuccess(HemaNetTask netTask, HemaBaseResult baseResult) {

    }

    @Override
    protected void callBackForGetDataFailed(HemaNetTask netTask, int failedType) {

    }

    @Override
    protected void findView() {
        ll_back=findViewById(R.id.ll_back);
        tv_title=findViewById(R.id.tv_title);
        iv_health=findViewById(R.id.iv_health);
        iv_skill=findViewById(R.id.iv_skill);

    }

    @Override
    protected void getExras() {
        health=mIntent.getStringExtra("health");
        skill=mIntent.getStringExtra("skill");

    }

    @Override
    protected void setListener() {
        ll_back.setOnClickListener(this);
        tv_title.setText("资格认证");
        iv_health.setOnClickListener(this);
        iv_skill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it=null;
        switch (v.getId()){
            case R.id.iv_health:
                ArrayList<String> mImgs = new ArrayList<>();
                ArrayList<String> bigImgs = new ArrayList<>();
                mImgs.add(health);
                bigImgs.add(health);
                 it = new Intent(mContext, ShowLargePicActivity.class);
                it.putExtra("position", 0);
                it.putExtra("imagelist", mImgs);
                it.putExtra("bigImageList", bigImgs);
                startActivity(it);
                break;
            case R.id.iv_skill:
                ArrayList<String> mSkill = new ArrayList<>();
                ArrayList<String> bigSkill = new ArrayList<>();
                mSkill.add(skill);
                bigSkill.add(skill);
                it = new Intent(mContext, ShowLargePicActivity.class);
                it.putExtra("position", 0);
                it.putExtra("imagelist", mSkill);
                it.putExtra("bigImageList", bigSkill);
                startActivity(it);
                break;
            case R.id.ll_back:
                finishAc(mContext);
                break;
        }
    }
}
