package com.wologic.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.WBaseInfo;
import com.wologic.util.Common;
import com.wologic.util.DbManager;

public class WBaseInfoDao {

	public List<WBaseInfo> GetAllBasicInfo(String clientId)
	{
		 List<WBaseInfo> list=null;
		 String sql="select * from wbaseinfo where ClientId='"+clientId+"'";
			SQLiteDatabase db=DbManager.getDb();
			 Cursor cursor=db.rawQuery(sql,null);
			 if(cursor!=null)
			 {
				 list=new ArrayList<WBaseInfo>();
				 while(cursor.moveToNext())
				 {
					 WBaseInfo info=new WBaseInfo();
					 info.setClientId(cursor.getString(cursor.getColumnIndex("ClientId")));
					 info.setInfoId(cursor.getString(cursor.getColumnIndex("infoId")));
					 info.setInfoname(cursor.getString(cursor.getColumnIndex("infoname")));
					 info.setIsShow(cursor.getString(cursor.getColumnIndex("IsShow")));
					 info.setBase1(cursor.getString(cursor.getColumnIndex("base1")));
					 if(cursor.getString(cursor.getColumnIndex("updatetime"))!=null&&!cursor.getString(cursor.getColumnIndex("updatetime")).equals(""))
					 {
						 info.setUpdatetime(Common.StrToDate(cursor.getString(cursor.getColumnIndex("updatetime"))));
					 }
					 list.add(info);
				 }
				
			 }
			db.close();
		 return list;
	}
	
	
	public WBaseInfo GetBasicInfo(String infoid)
	{
		 WBaseInfo info=null;
		 String sql="select * from wbaseinfo where infoid='"+infoid+"'";
			SQLiteDatabase db=DbManager.getDb();
			 Cursor cursor=db.rawQuery(sql,null);
			 if(cursor!=null)
			 {
				 info=new WBaseInfo();
				 while(cursor.moveToNext())
				 {
					 info.setClientId(cursor.getString(cursor.getColumnIndex("ClientId")));
					 info.setInfoId(cursor.getString(cursor.getColumnIndex("infoId")));
					 info.setInfoname(cursor.getString(cursor.getColumnIndex("infoname")));
					 info.setIsShow(cursor.getString(cursor.getColumnIndex("IsShow")));
					 info.setBase1(cursor.getString(cursor.getColumnIndex("base1")));
					 if(cursor.getString(cursor.getColumnIndex("updatetime"))!=null&&cursor.getString(cursor.getColumnIndex("updatetime")).equals(""))
					 {
						 info.setUpdatetime(Common.StrToDate(cursor.getString(cursor.getColumnIndex("updatetime"))));
					 }
				 }
			 }
			db.close();
		 return info;
	}
	
	
	public int UpdateBasicInfoTime(String infoid)
	{
		   String sql=" update wbaseinfo set updatetime=datetime()  where infoid='"+infoid+"'";
		   SQLiteDatabase db=DbManager.getDb();
			db.execSQL(sql);
			db.close();
			return 0;
	}
}
