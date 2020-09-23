package com.beijing.tenfingers.nettask;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.Base.MyNetTask;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import xtom.frame.exception.DataParseException;

/**
 * 通用网络任务类(不分页)
 * Created by Torres on 2017/10/10.
 */

public class CommonNetTask <T> extends MyNetTask {

    Class<T> classType;

    public CommonNetTask(MyHttpInformation information, HashMap<String, String> params, Class<T> classType) {
        super(information, params);
        this.classType = classType;
    }

    public CommonNetTask(MyHttpInformation information, HashMap<String, String> params) {
        super(information, params);
    }

    public CommonNetTask(MyHttpInformation information, HashMap<String, String> params, HashMap<String, String> files, Class<T> classType) {
        super(information, params, files);
        this.classType = classType;
    }

    @Override
    public Object parse(JSONObject jsonObject) throws DataParseException {
        return new Result(jsonObject);
    }

    private class Result extends HemaArrayResult<T> {

        public Result(JSONObject jsonObject) throws DataParseException {
            super(jsonObject);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public T parse(JSONObject jsonObject) throws DataParseException {
            Object classT = null;
            try {
                Constructor e = classType.getConstructor(new Class[]{JSONObject.class});  //获取带参数的构造函数
                e.setAccessible(true);
                classT = e.newInstance(new Object[]{jsonObject});  //通过带参数的构造函数创建ConstructorDemo类的实例
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException var5) {
                var5.printStackTrace();
            }
            return (T) classT;
        }

    }
}
