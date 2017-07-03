package com.wologic.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbManager {

	private static MySqlLiteHelper helper;
	
	public static MySqlLiteHelper getInstance(Context context)
	{
		if(helper==null)
		{
			helper=new MySqlLiteHelper(context);
		}
		return helper;
	}
	
	public static SQLiteDatabase getDb()
	{
	/*	// 获取管理对象，因为数据库需要通过管理对象才能够获取  
		AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();  
		// 通过管理对象获取数据库  
		SQLiteDatabase db1 = mg.getDatabase("adpa.db3");
		return db1;*/
		return helper.getWritableDatabase();
	}
	
	public static void execsql(SQLiteDatabase db,String sql)
	{
		if(db!=null)
		{
			db.execSQL(sql);
		}
	}
}
