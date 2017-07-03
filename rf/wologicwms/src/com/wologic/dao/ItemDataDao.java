package com.wologic.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.ItemData;
import com.wologic.domain.WorkItem;
import com.wologic.util.Common;
import com.wologic.util.DbManager;

public class ItemDataDao {

	/**
	 * 获取总的记录数
	 * @param itemno
	 * @return
	 */
	public Long getTotalCount(String itemno)
	{
		String sql="select count(0) from itemdata where itemno=?";
		SQLiteDatabase db=DbManager.getDb();
	  Cursor cursor=db.rawQuery(sql, new String[]{itemno});
	  if(cursor!=null)
	  {
		  cursor.moveToFirst();
		  Long count = cursor.getLong(0);
		  return count;
	  }
		return (Long) 0L;
	}
	
	/**
	 * 获取总的数量
	 * @param itemno
	 * @return
	 */
	public Double GetTotalNum(String itemno)
	{
		String sql="select count(*) from itemdata where itemno=?";
		SQLiteDatabase db=DbManager.getDb();
		  Cursor cursor=db.rawQuery(sql, new String[]{itemno});
		  if(cursor!=null)
		  {
			  cursor.moveToFirst();
			  double count = cursor.getDouble(0);
			  return count;
		  }
	   return (double) 0;
	}
	
	/**
	 * 获取总的数量
	 * @param itemno
	 * @return
	 */
	public Double GetTotalScanNum(String itemno)
	{
		String sql="select sum(work_amount) from itemdata where itemno=?";
		SQLiteDatabase db=DbManager.getDb();
		  Cursor cursor=db.rawQuery(sql, new String[]{itemno});
		  if(cursor!=null)
		  {
			  cursor.moveToFirst();
			  double count = cursor.getDouble(0);
			  return count;
		  }
	   return (double) 0;
	}

	/**
	 * 插入数据
	 * @param itemData
	 * @return
	 */
    public int insertItemData(ItemData itemData)
    {
    	String sql="insert into itemdata (itemno,productno,plan_amount,complete_amount,work_amount,userid,deviceid,state,worktime,Info1,Info2) values (?,?,?,?,?,?,?,?,datetime('now'),?,?)";
        SQLiteDatabase db=DbManager.getDb();
        Object[] bindArgs={itemData.getItemno(),itemData.getProductno(),itemData.getPlanamount(),itemData.getCompleteamount(),itemData.getWork_amount(),itemData.getUserid(),itemData.getDeviceid(),itemData.getState(),itemData.getInfo1(),itemData.getInfo2()};
        db.execSQL(sql, bindArgs);
		//db.execSQL(sql);
		db.close();
		return 0;
    }

    public List<ItemData> GetItemData(String itemno,String barCode)
    {
    	List<ItemData> list=new ArrayList<ItemData>();
		String sql="select "
+" it.[itemno],it.productno,it.[plan_amount],it.[work_amount],it.Info1, "
+" p.[productname],bc.[pkgbarcode],bc.[pkgname],bc.[pkgrate],p.proinfo11  "
+" from itemdata it inner join product p "
+" on p.[productno]=it.productno "
+" inner join barcode bc on bc.[productno]=p.productno where 1=1   ";
		
		SQLiteDatabase db=DbManager.getDb();
		List<String> para=new ArrayList<String>();
		if(itemno!="")
		{
			sql+="  and it.[itemno]=? ";
			para.add(itemno);
		}
		if(barCode!="")
		{
			sql+="  and bc.[pkgbarcode]=? ";
			para.add(barCode);
		}
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		
		 Cursor cursor=db.rawQuery(sql,arr);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 ItemData item=new ItemData();
				 item.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 item.setInfo1(cursor.getString(cursor.getColumnIndex("Info1")));//货位
				 item.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				 item.setPlanamount(cursor.getDouble(cursor.getColumnIndex("plan_amount")));
				 item.setWork_amount(cursor.getDouble(cursor.getColumnIndex("work_amount")));
				 item.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
				 item.setPkgbarcode(cursor.getString(cursor.getColumnIndex("pkgbarcode")));
				 item.setPkgname(cursor.getString(cursor.getColumnIndex("pkgname")));
				 item.setPkgrate(cursor.getDouble(cursor.getColumnIndex("pkgrate")));
				 item.setPrice(cursor.getString(cursor.getColumnIndex("proinfo11")));//价格
				 list.add(item);
			 }
		 }
		db.close();
		return list;
    }
    
    
    public List<ItemData> GetItemDataOne(String itemno,String barCode)
    {
    	List<ItemData> list=new ArrayList<ItemData>();
		String sql="select "
+" it.[itemno],it.productno,it.[plan_amount],it.[work_amount],it.Info1, "
+" p.[productname],p.proinfo11  "
+" from itemdata it inner join product p "
+" on p.[productno]=it.productno "
+" where 1=1   ";
		
		SQLiteDatabase db=DbManager.getDb();
		List<String> para=new ArrayList<String>();
		if(!itemno.equals(""))
		{
			sql+="  and it.[itemno]=? ";
			para.add(itemno);
		}
		if(!barCode.equals(""))
		{
			sql+="  and bc.[pkgbarcode]=? ";
			para.add(barCode);
		}
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		
		 Cursor cursor=db.rawQuery(sql,arr);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 ItemData item=new ItemData();
				 item.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 item.setInfo1(cursor.getString(cursor.getColumnIndex("Info1")));//货位
				 item.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				 item.setPlanamount(cursor.getDouble(cursor.getColumnIndex("plan_amount")));
				 item.setWork_amount(cursor.getDouble(cursor.getColumnIndex("work_amount")));
				 item.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
				// item.setPkgbarcode(cursor.getString(cursor.getColumnIndex("pkgbarcode")));
				 //item.setPkgname(cursor.getString(cursor.getColumnIndex("pkgname")));
				// item.setPkgrate(cursor.getDouble(cursor.getColumnIndex("pkgrate")));
				 item.setPrice(cursor.getString(cursor.getColumnIndex("proinfo11")));//价格
				 list.add(item);
			 }
		 }
		db.close();
		return list;
    }

    /**
     * 更新工作中的数量
     * @param itemno
     * @param productno
     * @param amount
     * @return
     */
    public int updateWorkAmount(String itemno,String productno,Double amount)
    {
    	String sql="update itemdata set  work_amount="+amount+" where itemno='"+itemno+"' and productno='"+productno+"'";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
    	return 0;
    }
    /**
     * 删除当前单号中的当前物料,用于处理数量为0的情况
     * @param itemno
     * @param productno
     * @param amount
     * @return
     */
    public int updateWorkAmount1(String itemno,String productno)
    {
    	String sql="delete from itemdata where itemno='"+itemno+"' and productno='"+productno+"'";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
    	return 0;
    }
    
    public int updateWorkAmountEnd(String itemno,String productno,Double amount)
    {
    	String sql="update itemdata set Info2='1', work_amount=work_amount+"+amount+" where itemno='"+itemno+"' and productno='"+productno+"'";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
    	return 0;
    }
    
    /**
     * 检验本地是否已经下载过此单号的详细信息
     * @param itemno
     * @return
     */
    public boolean ValidateExistOfItemnoInfoFromLocal(String itemno){
    	String[] str = new String[1];
    	str[0] = itemno;
    	String sql = "select * from itemdata where itemno = ?";
    	SQLiteDatabase db=DbManager.getDb();
    	Cursor cursor=db.rawQuery(sql,str);
    	int count;
		if((count =cursor.getCount())<=0){
    		return false;
    	}
    	return true;
    }
    public List<ItemData> GetItemDataNoEnd(String itemno)
    {
    	List<ItemData> list=new ArrayList<ItemData>();
		String sql="select "
+" it.[itemno],it.productno,it.[plan_amount],it.[work_amount],it.deviceid, "
+" p.[productname],p.proinfo11,it.Info1,p.proinfo11 "
+" from itemdata it inner join product p "
+" on p.[productno]=it.productno "
+"  where 1=1   ";
		
		SQLiteDatabase db=DbManager.getDb();
		List<String> para=new ArrayList<String>();
		if(itemno!="")
		{
			sql+="  and it.[itemno]=? ";
			para.add(itemno);
		}
		sql+=" order by Info2,Info1 ";
	
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		 Cursor cursor=db.rawQuery(sql,arr);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 ItemData item=new ItemData();
				 item.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 item.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				 item.setPlanamount(cursor.getDouble(cursor.getColumnIndex("plan_amount")));
				 item.setWorkamount(cursor.getDouble(cursor.getColumnIndex("work_amount")));
				 item.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
				 item.setPrice(cursor.getString(cursor.getColumnIndex("proinfo11")));//价格
				 item.setInfo1(cursor.getString(cursor.getColumnIndex("Info1")));
				 item.setDeviceid(cursor.getString(cursor.getColumnIndex("deviceid")));
				 list.add(item);
			 }
		 }
		db.close();
		return list;
    }
    

    public List<ItemData> GetItemDataEnd(String itemno)
    {
    	List<ItemData> list=new ArrayList<ItemData>();
		String sql=" select "
+" it.[itemno],it.productno,it.[plan_amount],it.[work_amount], "
+" p.[productname],bc.[pkgbarcode],bc.[pkgname],bc.[pkgrate],p.proinfo11 "
+" from itemdata it inner join product p "
+" on p.[productno]=it.productno "
+" inner join barcode bc on bc.[productno]=p.productno where 1=1   ";
		
		SQLiteDatabase db=DbManager.getDb();
		List<String> para=new ArrayList<String>();
		if(itemno!="")
		{
			sql+="  and it.[itemno]=? ";
			para.add(itemno);
		}
	
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		 Cursor cursor=db.rawQuery(sql,arr);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 ItemData item=new ItemData();
				 item.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 item.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				 item.setPlanamount(cursor.getDouble(cursor.getColumnIndex("plan_amount")));
				 item.setWork_amount(cursor.getDouble(cursor.getColumnIndex("work_amount")));
				 item.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
				 item.setPkgbarcode(cursor.getString(cursor.getColumnIndex("pkgbarcode")));
				 item.setPkgname(cursor.getString(cursor.getColumnIndex("pkgname")));
				 item.setPkgrate(cursor.getDouble(cursor.getColumnIndex("pkgrate")));
				 item.setPrice(cursor.getString(cursor.getColumnIndex("proinfo11")));//价格
				 list.add(item);
			 }
		 }
		db.close();
		return list;
    }


    public List<ItemData> GetItemDataByBarCode(String itemno,String barCode)
    {
    	List<ItemData> list=new ArrayList<ItemData>();
		String sql="select "
+" it.[itemno],it.productno,it.[plan_amount],it.[work_amount], "
+" p.[productname],bc.[pkgbarcode],bc.[pkgname],bc.[pkgrate],p.proinfo11  "
+" from itemdata it inner join product p "
+" on p.[productno]=it.productno "
+" inner join barcode bc on bc.[productno]=p.productno where 1=1   ";
		
		SQLiteDatabase db=DbManager.getDb();
		List<String> para=new ArrayList<String>();
		if(itemno!="")
		{
			sql+="  and it.[itemno]=? ";
			para.add(itemno);
		}
		if(barCode!="")
		{
			sql+="  and bc.[pkgbarcode]=? ";
			para.add(barCode);
		}
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		
		 Cursor cursor=db.rawQuery(sql,arr);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 ItemData item=new ItemData();
				 item.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 item.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				 item.setPlanamount(cursor.getDouble(cursor.getColumnIndex("plan_amount")));
				 item.setWork_amount(cursor.getDouble(cursor.getColumnIndex("work_amount")));
				 item.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
				 item.setPkgbarcode(cursor.getString(cursor.getColumnIndex("pkgbarcode")));
				 item.setPkgname(cursor.getString(cursor.getColumnIndex("pkgname")));//单位
				 item.setPkgrate(cursor.getDouble(cursor.getColumnIndex("pkgrate")));
				 item.setPrice(cursor.getString(cursor.getColumnIndex("proinfo11")));//价格
				 
				 list.add(item);
			 }
		 }
		db.close();
		return list;
    }
    
    
    public List<ItemData> GetItemDataByProductNo(String itemno,String productNo)
    {
    	List<ItemData> list=new ArrayList<ItemData>();
		String sql="select "
+" it.[itemno],it.productno,it.[plan_amount],it.[work_amount], "
+" p.[productname],p.proinfo11  "
+" from itemdata it inner join product p "
+" on p.[productno]=it.productno "
+"  where 1=1   ";
		
		SQLiteDatabase db=DbManager.getDb();
		List<String> para=new ArrayList<String>();
		if(itemno!="")
		{
			sql+="  and it.[itemno]=? ";
			para.add(itemno);
		}
		if(productNo!="")
		{
			sql+="  and it.productno=? ";
			para.add(productNo);
		}
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		
		 Cursor cursor=db.rawQuery(sql,arr);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 ItemData item=new ItemData();
				 item.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 item.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				 item.setPlanamount(cursor.getDouble(cursor.getColumnIndex("plan_amount")));
				 item.setWork_amount(cursor.getDouble(cursor.getColumnIndex("work_amount")));
				 item.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
				 item.setPrice(cursor.getString(cursor.getColumnIndex("proinfo11")));//价格
				 
				 list.add(item);
			 }
		 }
		db.close();
		return list;
    }
    
    
    public List<ItemData> GetItemDataByProductNoTwo(String itemno,String productNo)
    {
    	List<ItemData> list=new ArrayList<ItemData>();
		String sql="select "
+" it.[itemno],it.productno,it.[plan_amount],it.[work_amount] "
+" from itemdata it "
+"  where 1=1   ";
		
		SQLiteDatabase db=DbManager.getDb();
		List<String> para=new ArrayList<String>();
		if(!itemno.equals(""))
		{
			sql+="  and it.[itemno]=? ";
			para.add(itemno);
		}
		if(!productNo.equals(""))
		{
			sql+="  and it.productno=? ";
			para.add(productNo);
		}
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		
		 Cursor cursor=db.rawQuery(sql,arr);
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				 ItemData item=new ItemData();
				 item.setItemno(cursor.getString(cursor.getColumnIndex("itemno")));
				 item.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				 item.setPlanamount(cursor.getDouble(cursor.getColumnIndex("plan_amount")));
				 item.setWork_amount(cursor.getDouble(cursor.getColumnIndex("work_amount")));
				// item.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
		
				 
				 list.add(item);
			 }
		 }
		db.close();
		return list;
    }

    
    public int deleteItemData(String itemno)
    {
    	
    	String sql=" delete from itemdata  where itemno='"+itemno+"'";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		return 0;
    }
    
    public int deleteAll()
    {
    	
    	String sql=" delete from itemdata  ";
        SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql);
		db.close();
		return 0;
    }
}
