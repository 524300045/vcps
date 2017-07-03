package com.wologic.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.WorkItem;
import com.wologic.util.Common;
import com.wologic.util.DbManager;

@SuppressLint("SimpleDateFormat") public class RuKuDao {

	
	public RuKuDao(Context context)
	{
		//DbManager.getInstance(context);
	}
	
	public RuKuDao()
	{
		//DbManager.getInstance(context);
	}
	
	
	/**
	 * 根据单号和类型查询是否存在
	 * @param itemno
	 * @param itemtype
	 * @return
	 */
	public List<WorkItem> getWorkItem(String itemno)
	{
		List<WorkItem> list=new ArrayList<WorkItem>();
		String sql="select * from workitem where itemno=?";
		
		SQLiteDatabase db=DbManager.getDb();
		
		 Cursor cursor=db.rawQuery(sql,new String[]{itemno});
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 WorkItem workItem=new WorkItem();
				 workItem.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 workItem.setItemtype(cursor.getString(cursor.getColumnIndex("itemtype")));
				 workItem.setItemstate(cursor.getString(cursor.getColumnIndex("itemstate")));
				 workItem.setWorktime(Common.StrToDate(cursor.getString(cursor.getColumnIndex("worktime"))));
				 list.add(workItem);
			 }
			
		 }
		db.close();
	
		
		return list;
	}

	public List<WorkItem> getWorkItem(String itemno,String itemtype)
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sDateFormat.format(new java.util.Date());
		List<WorkItem> list=new ArrayList<WorkItem>();
		String sql="select * from workitem where  itemtype=?  and worktime >='"+date+"' order by itemstate desc,worktime desc";
		SQLiteDatabase db=DbManager.getDb();

		 Cursor cursor=db.rawQuery(sql,new String[]{itemtype});
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 WorkItem workItem=new WorkItem();
				 workItem.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 workItem.setItemtype(cursor.getString(cursor.getColumnIndex("itemtype")));
				 workItem.setItemstate(cursor.getString(cursor.getColumnIndex("itemstate")));
				 workItem.setWorktime(Common.StrToDate(cursor.getString(cursor.getColumnIndex("worktime"))));
				 list.add(workItem);
			 }
			
		 }
		db.close();
	
		
		return list;
	}
	
	public int insertWorkItem(WorkItem workItem)
	{
		String sql="insert into workitem (itemno,itemtype,itemstate,worktime) values ('"+workItem.getItemno()+"','"+workItem.getItemtype()+"','"+workItem.getItemtype()+"', datetime('now'))";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		return 0;
	}
	
	public int updateWorkItem()
	{
		String sql="insert into workitem (itemno,itemtype,itemstate,worktime) values ('1','w1','s5', datetime('now'))";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		
		return 0;
	}
	
	public int delWorkItem(String itemno)
	{
		String sql=" delete from workitem where itemno='"+itemno+"'";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		return 0;
	}
	/**
	 * 更新状态为已完成（已上传）
	 * @param itemno
	 * @return
	 */
	public int updateWorkItemStatus(String itemno)
	{
		String sql=" update workitem set itemstate='s10'  where itemno='"+itemno+"'";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		return 0;
		
	}
	/**
	 * 更新状态为正在操作（扫描了但是未上传）
	 * @param itemno
	 * @return
	 */
	public int updateWorkItemStatus2(String itemno)
	{
		String sql=" update workitem set itemstate='s09'  where itemno='"+itemno+"'";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		return 0;
		
	}
	/**
	 * 根据单号和类型查询是否存在
	 * @param itemno
	 * @param itemtype
	 * @return
	 */
	public List<WorkItem> getTopNWorkItem()
	{
		List<WorkItem> list=new ArrayList<WorkItem>();
		String sql=" select  * from workitem  order by itemstate desc,worktime desc  limit 4 ";
		SQLiteDatabase db=DbManager.getDb();
		 Cursor cursor=db.rawQuery(sql,null);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 WorkItem workItem=new WorkItem();
				 workItem.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 workItem.setItemtype(cursor.getString(cursor.getColumnIndex("itemtype")));
				 workItem.setItemstate(cursor.getString(cursor.getColumnIndex("itemstate")));
				 workItem.setWorktime(Common.StrToDate(cursor.getString(cursor.getColumnIndex("worktime"))));
				 list.add(workItem);
			 }
			
		 }
		db.close();
	
		
		return list;
	}

	public int deleteAll()
	{
		String sql=" delete from workitem ";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		return 0;
	}

}
