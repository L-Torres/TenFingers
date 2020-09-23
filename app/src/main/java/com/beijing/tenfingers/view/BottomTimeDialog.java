package com.beijing.tenfingers.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beijing.tenfingers.R;
import com.beijing.tenfingers.adapter.TpAdapter;
import com.beijing.tenfingers.bean.TimeData;
import com.beijing.tenfingers.bean.TimePoint;
import com.beijing.tenfingers.eventbus.EventBusModel;
import com.beijing.tenfingers.eventbus.MyEventBusConfig;
import com.beijing.tenfingers.until.BaseUtil;

import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.greenrobot.event.EventBus;
import xtom.frame.XtomObject;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 时间点选择
 */
public class BottomTimeDialog extends XtomObject {


    private Context context;
    private Dialog mDialog;
    private TextView tv_one,tv_two,tv_three;
    private Button btn_confirm;
    private XtomGridView gridview;
    private TpAdapter adpter;
    private ArrayList<TimePoint> list=new ArrayList<>();
    private ArrayList<String> data=new ArrayList<>();
    private String  timeId;
    private LinearLayout pop_layout;
    private String riqi="";
    private TimeData timeData=new TimeData();
    public BottomTimeDialog(Context context,ArrayList<TimePoint> list) {
        this.context = context;
        this.list=list;
        this.data=data;
        data=getDateList(3);
//        getDateList(5);
        mDialog = new Dialog(context, R.style.custom_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_bottom_time, null);
        tv_one=view.findViewById(R.id.tv_one);
        pop_layout=view.findViewById(R.id.pop_layout);
        tv_two=view.findViewById(R.id.tv_two);
        tv_three=view.findViewById(R.id.tv_three);
        tv_one.setText(data.get(0));
        tv_two.setText(data.get(1));
        tv_three.setText(data.get(2));
        gridview=view.findViewById(R.id.gridview);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        tv_one.setTextColor(Color.parseColor("#FF006F55"));
        tv_two.setTextColor(Color.parseColor("#ff808080"));
        tv_three.setTextColor(Color.parseColor("#ff808080"));
        tv_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riqi=tv_one.getText().toString().trim();
                tv_one.setTextColor(Color.parseColor("#FF006F55"));
                tv_two.setTextColor(Color.parseColor("#ff808080"));
                tv_three.setTextColor(Color.parseColor("#ff808080"));
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.TIME_POINT_ONE,data.get(0)));
            }
        });
        tv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riqi=tv_two.getText().toString().trim();
                tv_two.setTextColor(Color.parseColor("#FF006F55"));
                tv_one.setTextColor(Color.parseColor("#ff808080"));
                tv_three.setTextColor(Color.parseColor("#ff808080"));
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.TIME_POINT_TWO,data.get(1)));
            }
        });
        tv_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                riqi=tv_three.getText().toString().trim();
                tv_three.setTextColor(Color.parseColor("#FF006F55"));
                tv_two.setTextColor(Color.parseColor("#ff808080"));
                tv_one.setTextColor(Color.parseColor("#ff808080"));
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.TIME_POINT_THREE,data.get(2)));
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timeId= XtomSharedPreferencesUtil.get(context,"time");
                String timepoint=XtomSharedPreferencesUtil.get(context,"timepoint");
                if(isNull(riqi)){
                    riqi=tv_one.getText().toString().trim();
                }
                String advance=riqi+" "+timepoint+" "+getWeek(riqi);
                String advanceTime=riqi+" "+timepoint;
                timeData=new TimeData(advance,advanceTime,timeId);
                if(isNull(timeId) && list.size()==0){
                    ToastUtils.show("技师当天没有可以预约时间哦～");
                    return;
                }else if(isNull(timeId) && list.size()>0){
                    ToastUtils.show("请选择服务时间点！");
                    return;
                }
                mDialog.cancel();
                EventBus.getDefault().post(new EventBusModel(true, MyEventBusConfig.TIME_POINT,timeData));
            }
        });
        adpter=new TpAdapter(context);
        gridview.setAdapter(adpter);
        mDialog.setCancelable(true);
        mDialog.setContentView(view);
        WindowManager.LayoutParams localLayoutParams = mDialog.getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = BaseUtil.getScreenWidth(context);
        localLayoutParams.height = BaseUtil.getScreenHeight(context)-( BaseUtil.getScreenWidth(context)/4);
        mDialog.onWindowAttributesChanged(localLayoutParams);
    }
    public void setAdpter(ArrayList<TimePoint> timePoints){
        timeId= XtomSharedPreferencesUtil.get(context,"time");
        if(timePoints.size()==0){
            XtomSharedPreferencesUtil.save(context,"time","");


        }
        adpter.setData(timePoints);
        adpter.notifyDataSetChanged();
    }
    /**
     * 设置是否可以取消
     * @param cancelable
     */
    public void setCancelable(boolean cancelable)
    {
        mDialog.setCancelable(cancelable);
    }

    public void show() {
        mDialog.show();

        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        mDialog.getWindow().setAttributes(lp);
    }

    public void cancel() {
        mDialog.cancel();
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static ArrayList<String> getSevendate() {
        ArrayList<String> dates = new ArrayList<String>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        for (int i = 0; i < 3; i++) {
            String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            String  mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            if(Integer.valueOf(mMonth)<10){
                mMonth="0"+mMonth;
            }
            String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
            if(Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(i+1))){
                mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear),(i+1)));
            }
            if(Integer.valueOf(mDay)<10){
                mDay="0"+mDay;
            }
            String date =mYear+"-"+mMonth + "-" + mDay;

            dates.add(date);
        }
        return dates;
    }

    /**得到当年当月的最大日期**/
    public static int MaxDayFromDay_OF_MONTH(int year,int month){
        Calendar time=Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month-1);//注意,Calendar对象默认一月为0                 
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }

    private String getWeek(String  strDate){
        String week="";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
        try {
            Date date = format.parse(strDate);
            week = getWeek(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }
    public static String getWeek(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String week = sdf.format(date);
        return week;

    }



    private ArrayList<String> getDateList(int day) {
        //创建集合储存日期
        ArrayList<String> dateList = new ArrayList<>();
        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date().getTime());
        //此处是我项目的需求，需要保存当前日期在第一位，你也可以根据自己的需求自行决定
        dateList.add(format);
        for (int i = 0; i < day;i++){
            // 将当前的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
            Date date = sdf.parse(format, new ParsePosition(0));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // add方法的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天，你可根据自己的需求自行决定,
            //如果项目中需要多次调用，你也可把这个参数，通过方法动态传入
            calendar.add(Calendar.DATE, +1);
            Date date1 = calendar.getTime();
            format = sdf.format(date1);
            dateList.add(format);
        }
        return dateList;
    }




}
