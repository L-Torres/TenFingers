package com.beijing.tenfingers.nettask;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.Base.MyNetTask;
import com.hemaapp.hm_FrameWork.result.HemaPageArrayResult;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import xtom.frame.exception.DataParseException;

/**
 * 通用分页网络任务类（分页）
 * Created by Torres on 2017/10/10.
 */

public class CommonPageNetTask<T> extends MyNetTask {


    Class<T> classType;

    public CommonPageNetTask(MyHttpInformation information, HashMap<String, String> params, Class<T> classType) {
        super(information, params);
        this.classType = classType;
    }

    public CommonPageNetTask(MyHttpInformation information,
                             HashMap<String, String> params) {
        super(information, params);
    }

    public CommonPageNetTask(MyHttpInformation information,
                             HashMap<String, String> params, HashMap<String, String> files) {
        super(information, params, files);
    }

    @Override
    public Object parse(JSONObject jsonObject) throws DataParseException {
        return new Result(jsonObject);
    }

    private class Result extends HemaPageArrayResult<T> {

        public Result(JSONObject jsonObject) throws DataParseException {
            super(jsonObject);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public T parse(JSONObject jsonObject) throws DataParseException {
            Object classT = null;

            try {
                Constructor e = classType.getConstructor(new Class[]{JSONObject.class});
                e.setAccessible(true);
                classT = e.newInstance(new Object[]{jsonObject});
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException var5) {
                var5.printStackTrace();
            }

            return (T) classT;
        }

    }

}


