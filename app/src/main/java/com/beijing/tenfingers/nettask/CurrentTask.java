package com.beijing.tenfingers.nettask;

import com.beijing.tenfingers.Base.MyHttpInformation;
import com.beijing.tenfingers.Base.MyNetTask;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import org.json.JSONObject;

import java.util.HashMap;

import xtom.frame.exception.DataParseException;

/**
 * 无返回结果的任务类
 */
public class CurrentTask extends MyNetTask {

	public CurrentTask(MyHttpInformation information,
					   HashMap<String, String> params) {
		super(information, params);
	}

	@Override
	public Object parse(JSONObject jsonObject) throws DataParseException {
		return new HemaBaseResult(jsonObject);
	}

}
