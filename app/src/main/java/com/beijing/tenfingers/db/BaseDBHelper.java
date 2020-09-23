package com.beijing.tenfingers.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 基础数据库类
 */
public class BaseDBHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "om.db";
	/**
	 * 系统初始化信息
	 */
	protected static final String SYSINITINFO = "sysinfo";

	/**
	 * 当前登录用户信息
	 */
	protected static final String USER = "user";
	/**
	 * 访问城市缓存信息
	 */
	protected static final String VISIT_CITYS = "visit_citys";
	/**
	 * 搜索词缓存
	 */
	protected static final String SYS_CASCADE_SEARCH = "sys_cascade_search";
	/**
	 * 客户搜索词缓存
	 */
	protected static final String SYS_CASCADE_SEARCH_CLIENT = "sys_cascade_search_client";
	/**
	 * 商品搜索词缓存
	 */
	protected static final String SYS_CASCADE_DISCOVER_SEARCH = "sys_cascade_discover_search";
	/**
	 * 附件下载表
	 */
	protected static final String FILEDOWNLOAD = "musicdownload";

	/**
	 * 知识库 最近使用话术
	 */
	protected static final String KNOWLEDGE_TALK = "knowledge_talk";

	public BaseDBHelper(Context context) {
		super(context, DBNAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建系统初始化信息缓存表
		String sys = "sys_web_service text,sys_plugins text,start_img text,android_must_update text,android_last_version text,iphone_must_update text,"
				+ "iphone_last_version text,sys_chat_ip text,sys_chat_port text,sys_pagesize text,sys_service_phone text,sys_service_qq text,android_update_url text,"
				+ "iphone_update_url text,apad_update_url text,ipad_update_url text,iphone_comment_url text,msg_invite text,recharge_content";
		String sysSQL = "create table " + SYSINITINFO
				+ " (id integer primary key," + sys + ")";
		db.execSQL(sysSQL);

		// 创建当前登录用户信息缓存表
		String user = "id text,username text,email text,nickname text,mobile text,password text,paypassword text,sex text,selfsign text,realname text,company_id text,dept_id text,worker text,sealflag text,"
				+ "adminflag text,viptype text,vipenddate text,level text,score text,feeaccount text,avatar text,avatarbig text,district_name text,validflag text,devicetype text,deviceid text,"
				+ "chanelid text,lastlogintime text,lastloginversion text,regdate text,token text,companyname text,deptname text,level_name text,away_score text";
		String userSQL = "create table " + USER + " (" + user + ")";
		db.execSQL(userSQL);


		String citys = "id text primary key,name text,level text,charindex text";
		String citysSQL = "create table " + VISIT_CITYS + " (" + citys + ")";
		db.execSQL(citysSQL);

		// 创建搜索词缓存表
		String search = "searchname text";
		String searchSQL = "create table " + SYS_CASCADE_SEARCH + " (" + search
				+ ")";

		db.execSQL(searchSQL);
		// 创建附件下载表
		String sql = "create table "
				+ FILEDOWNLOAD
				+ " (id text primary key,name text,imgurl text,imgurlbig text,audiourl text,content text,savePath text,size text,isload text,audiotime text,play text,cur text,curSize text,allSize text,series text,seriesId text)";
		db.execSQL(sql);

		//创建发现搜索词缓存
		String search_discover = "searchname text";
		String searchSQL_discover= "create table " + SYS_CASCADE_DISCOVER_SEARCH + " (" + search_discover
				+ ")";
		db.execSQL(searchSQL_discover);

		//创建客户搜索词缓存
		String search_client = "searchname text";
		String searchSQL_client= "create table " + SYS_CASCADE_SEARCH_CLIENT + " (" + search_client
				+ ")";

		// 创建知识库最近使用话术
		String talk = "id text,tag_id text,tag_name text,title text,content text";
		String talkSQL = "create table " + KNOWLEDGE_TALK + " (" + talk + ")";
		db.execSQL(talkSQL);

		db.execSQL(searchSQL_client);

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {

	}

}
