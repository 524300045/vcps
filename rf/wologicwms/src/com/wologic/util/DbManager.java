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
	/*	// ��ȡ���������Ϊ���ݿ���Ҫͨ�����������ܹ���ȡ  
		AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();  
		// ͨ����������ȡ���ݿ�  
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
