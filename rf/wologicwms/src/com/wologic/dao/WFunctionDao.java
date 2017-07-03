package com.wologic.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.WBaseInfo;
import com.wologic.domain.WFuction;
import com.wologic.util.Common;
import com.wologic.util.DbManager;

public class WFunctionDao {

	public List<WFuction> GetAllFunctionInfo(String clientId)
	{
		 List<WFuction> list=null;
		 String sql="select * from wfuction where ClientId='"+clientId+"' order by fuctionseq ";
			SQLiteDatabase db=DbManager.getDb();
			 Cursor cursor=db.rawQuery(sql,null);
			 if(cursor!=null)
			 {
				 list=new ArrayList<WFuction>();
				 while(cursor.moveToNext())
				 {
					 WFuction info=new WFuction();
					 info.setClientId(cursor.getString(cursor.getColumnIndex("ClientId")));
					 info.setVersionId(cursor.getString(cursor.getColumnIndex("VersionId")));
					 info.setFuctionid(cursor.getString(cursor.getColumnIndex("fuctionid")));
					 info.setFuctionname(cursor.getString(cursor.getColumnIndex("fuctionname")));
					 info.setFuctionseq(cursor.getInt(cursor.getColumnIndex("fuctionseq")));
					 info.setFuninfo1(cursor.getString(cursor.getColumnIndex("funinfo1")));
					 info.setFuninfo2(cursor.getString(cursor.getColumnIndex("funinfo2")));
					 info.setFuninfo3(cursor.getString(cursor.getColumnIndex("funinfo3")));
					 info.setFuninfo4(cursor.getString(cursor.getColumnIndex("funinfo4")));
					 info.setFuninfo5(cursor.getString(cursor.getColumnIndex("funinfo5")));
					 info.setFuninfo6(cursor.getString(cursor.getColumnIndex("funinfo6")));
					 list.add(info);
				 }
				
			 }
			db.close();
		 return list;
	}
}
