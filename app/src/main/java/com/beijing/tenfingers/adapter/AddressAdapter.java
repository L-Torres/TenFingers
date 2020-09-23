package com.beijing.tenfingers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.activity.AddAddressActivity;
import com.beijing.tenfingers.activity.AddressListActivity;
import com.beijing.tenfingers.activity.SelectShopPositionActivity;
import com.beijing.tenfingers.bean.Address;
import com.beijing.tenfingers.view.BaseRecycleAdapter;

import java.util.ArrayList;

import xtom.frame.XtomActivityManager;

import static android.app.Activity.RESULT_OK;

public class AddressAdapter extends BaseRecycleAdapter<Address> {

    private ArrayList<Address> list = new ArrayList<>();
    private Context context;
    private String type;
    public AddressAdapter(Context contex, ArrayList<Address> datas,String type) {
        super(datas);
        this.context = contex;
        this.list = datas;
        this.type=type;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        Address address=list.get(position);
        ((TextView) holder.getView(R.id.tv_name)).setText(address.getUsername());
        ((TextView) holder.getView(R.id.tv_phone)).setText(address.getMobile());
        if("1".equals(address.getIs_default())){
            ((TextView) holder.getView(R.id.tv_default)).setVisibility(View.VISIBLE);
        }else{
            ((TextView) holder.getView(R.id.tv_default)).setVisibility(View.INVISIBLE);
        }
        ((TextView) holder.getView(R.id.tv_content)).setText(address.getAddr_content()+address.getAddr_detail());
        holder.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, AddAddressActivity.class);
                it.putExtra("object",address);
                context.startActivity(it);
            }
        });
        holder.getView(R.id.ll_all).setTag(address);

        holder.getView(R.id.ll_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address ad= (Address) v.getTag();
                Activity activityMap = XtomActivityManager.getActivityByClass(AddressListActivity.class);
                if (activityMap != null) {
                    if("1".equals(type)){
                        Intent mIntent = new Intent();
                        mIntent.putExtra("address", ad);
                        activityMap.setResult(RESULT_OK, mIntent);
                        activityMap.finish();
                    }else{
                        Intent it=new Intent(context, AddAddressActivity.class);
                        it.putExtra("object",ad);
                        context.startActivity(it);
                    }
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_address;
    }
}
