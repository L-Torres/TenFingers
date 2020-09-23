package com.beijing.tenfingers.nettask;




import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.Base.MyNetTask;

import org.json.JSONObject;

import java.util.HashMap;

import xtom.frame.exception.DataParseException;

/**
 * 获取所有城市接口
 * Created by Torres on 2016/3/1.
 */
public class DistrictAllGetTask extends MyNetTask {
    public DistrictAllGetTask(MyHttpInformation information, HashMap<String, String> params) {
        super(information, params);
    }

    @Override
    public Object parse(JSONObject jsonObject) throws DataParseException {
//        return new DistrictAllGetResult(jsonObject);
        return 0;
    }
}
