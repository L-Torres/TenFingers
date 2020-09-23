package com.hemaapp.hm_FrameWork.result;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 最基本的服务器返回结果
 */
public class HemaBaseResult extends XtomObject {

	private String  code;// 服务器处理状态
	private String message;// 服务器返回的描述信息
	private int error_code;// 当status==0时，会有一个对应的error_code。详见错误编码表
	private String error_message;//错误描述信息
	private String is_refresh;// 1 刷新 0不刷新
	/**
	 * 实例化一个最基本的服务器返回结果
	 * 
	 * @param jsonObject
	 *            一个JSONObject实例
	 * @throws DataParseException
	 *             数据解析异常
	 */
	public HemaBaseResult(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				if (!jsonObject.isNull("code")) {
					code = jsonObject.getString("code");
				}
				message = get(jsonObject, "message");
				if (!jsonObject.isNull("error_code")) {
					error_code = jsonObject.getInt("error_code");
				}
				error_message=jsonObject.getString("error_message");
				is_refresh=get(jsonObject,"is_refresh");
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}


	public String getIs_refresh() {
		return is_refresh;
	}

	/**
	 * @return 服务器执行状态
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return 服务器返回的描述信息
	 */
	public String getMessage() {
		return message;
	}

	public String getError_message() {
		return error_message;
	}

	/**
	 * 获取error_code值
	 * 
	 * @return 一个整数(当status==0时，会有一个对应的error_code。详见错误编码表)
	 */
	public int getError_code() {
		return error_code;
	}

}