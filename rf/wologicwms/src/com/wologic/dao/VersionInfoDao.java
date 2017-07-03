package com.wologic.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.VersionInfo;
import com.wologic.util.DbManager;

public class VersionInfoDao {

	public VersionInfo GetVersionInfo(String clientId)
	{
		VersionInfo info=null;
		 String sql="select * from versionInfo where ClientId='"+clientId+"'";
			SQLiteDatabase db=DbManager.getDb();
			 Cursor cursor=db.rawQuery(sql,null);
			 if(cursor!=null)
			 {
				 info=new VersionInfo();
				 while(cursor.moveToNext())
				 {
					 
					 info.setClientId(cursor.getString(cursor.getColumnIndex("ClientId")));
					 info.setVersionId(cursor.getString(cursor.getColumnIndex("VersionId")));
					 info.setVersionName(cursor.getString(cursor.getColumnIndex("VersionName")));
	                 break;
				 }
				
			 }
			db.close();
		 return info;
	}
}
