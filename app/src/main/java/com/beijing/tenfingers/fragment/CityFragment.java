package com.beijing.tenfingers.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.beijing.tenfingers.Base.MyBaseFragment;
import com.beijing.tenfingers.R;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.hemaapp.hm_FrameWork.HemaNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

public class CityFragment extends MyBaseFragment implements View.OnClickListener {

    private EditText ed_name,ed_phone,ed_area;
    private TextView tv_join;
    public CityFragment() {
        super();
    }
    public static CityFragment getInstance(int position) {
        CityFragment fragment = new CityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_city);
        super.onCreate(savedInstanceState);
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
        ed_name= (EditText) findViewById(R.id.ed_name);
        ed_phone= (EditText) findViewById(R.id.ed_phone);
        ed_area= (EditText) findViewById(R.id.ed_area);
        tv_join= (TextView) findViewById(R.id.tv_join);
    }

    @Override
    protected void setListener() {
        tv_join.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_join:

                break;
        }
    }
}
