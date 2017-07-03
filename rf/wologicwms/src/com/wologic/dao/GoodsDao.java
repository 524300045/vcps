package com.wologic.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.BarCode;
import com.wologic.domain.ItemData;
import com.wologic.util.DbManager;

public class GoodsDao {

	public int InsertGoods(BarCode barCode) {
		String sql = "insert into barcode (productno,pkgbarcode,pkgname,pkgrate) values (?,?,?,?)";
		SQLiteDatabase db = DbManager.getDb();
		Object[] bindArgs = { barCode.getProductno(), barCode.getPkgbarcode(),
				barCode.getPkgname(), barCode.getPkgrate() };
		db.execSQL(sql, bindArgs);
		return 0;
	}

	public List<BarCode> GetAlllist(String barcode) {
		List<BarCode> list = new ArrayList<BarCode>();
		SQLiteDatabase db = DbManager.getDb();
		String sql = "select bc.[productno],bc.[pkgname],bc.[pkgbarcode],bc.[pkgrate],p.[productname],p.[proinfo11], "
				+" proinfo10,proinfo9,proinfo8,proinfo7 "
				+ "from barcode bc inner join product p on bc.[productno]=p.[productno] where 1=1 ";
		
		
		List<String> para=new ArrayList<String>();
		
		if(barcode!="")
		{
			sql+="  and ( bc.[pkgbarcode]=? or p.[productname] like ? )";
			para.add(barcode);
			para.add("%"+barcode+"%");
		}
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
		
		Cursor cursor = db.rawQuery(sql, arr);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				BarCode barCode = new BarCode();
				barCode.setProductno(cursor.getString(cursor
						.getColumnIndex("productno")));
				barCode.setPkgbarcode(cursor.getString(cursor
						.getColumnIndex("pkgbarcode")));
				barCode.setPkgname(cursor.getString(cursor
						.getColumnIndex("pkgname")));
				barCode.setPkgrate(cursor.getDouble(cursor
						.getColumnIndex("pkgrate")));
				barCode.setProductname(cursor.getString(cursor
						.getColumnIndex("productname")));
				barCode.setPrice(cursor.getDouble(cursor
						.getColumnIndex("proinfo11")));
				barCode.setChandi(cursor.getString(cursor
						.getColumnIndex("proinfo10")));
				barCode.setGuige(cursor.getString(cursor
						.getColumnIndex("proinfo9")));
				barCode.setStock(cursor.getString(cursor
						.getColumnIndex("proinfo8")));
				barCode.setPinpai(cursor.getString(cursor
						.getColumnIndex("proinfo7")));
				
				list.add(barCode);
			}
		}
		db.close();
		return list;
	}
	
	
	public List<BarCode> GetAlllistPage(String barcode,int pageIndex,int pageSize ) {
		List<BarCode> list = new ArrayList<BarCode>();
		SQLiteDatabase db = DbManager.getDb();
		String sql = "select bc.[productno],bc.[pkgname],bc.[pkgbarcode],bc.[pkgrate],p.[productname],p.[proinfo11], "
				+" proinfo10,proinfo9,proinfo8,proinfo7 "
				+ "from barcode bc inner join product p on bc.[productno]=p.[productno] where 1=1 ";
		
		
		List<String> para=new ArrayList<String>();
		
		if(!barcode.equals(""))
		{
			sql+="  and ( bc.[pkgbarcode]=? or p.[productname] like ? )";
			para.add(barcode);
			para.add("%"+barcode+"%");
		}
		
		String[] strings = new String[para.size()];
		String[] arr=(String[])para.toArray(strings);
	
		
		sql+=" limit "+pageSize+" offset "+pageSize*(pageIndex-1);
		
		
		Cursor cursor = db.rawQuery(sql, arr);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				BarCode barCode = new BarCode();
				barCode.setProductno(cursor.getString(cursor
						.getColumnIndex("productno")));
				barCode.setPkgbarcode(cursor.getString(cursor
						.getColumnIndex("pkgbarcode")));
				barCode.setPkgname(cursor.getString(cursor
						.getColumnIndex("pkgname")));
				barCode.setPkgrate(cursor.getDouble(cursor
						.getColumnIndex("pkgrate")));
				barCode.setProductname(cursor.getString(cursor
						.getColumnIndex("productname")));
				barCode.setPrice(cursor.getDouble(cursor
						.getColumnIndex("proinfo11")));
				barCode.setChandi(cursor.getString(cursor
						.getColumnIndex("proinfo10")));
				barCode.setGuige(cursor.getString(cursor
						.getColumnIndex("proinfo9")));
				barCode.setStock(cursor.getString(cursor
						.getColumnIndex("proinfo8")));
				barCode.setPinpai(cursor.getString(cursor
						.getColumnIndex("proinfo7")));
				
				list.add(barCode);
			}
		}
		db.close();
		return list;
	}

	
	public List<BarCode> GetAlllistByProductAndBarCodeAndPkg(String productno,String barcode,String pkgname) {
		List<BarCode> list = new ArrayList<BarCode>();
		SQLiteDatabase db = DbManager.getDb();
		String sql = "select * from barcode  where 1=1 and productno=? and pkgbarcode=? and pkgname=? ";
		String[] strings ={productno,barcode,pkgname};
		Cursor cursor = db.rawQuery(sql, strings);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				BarCode barCode = new BarCode();
				barCode.setProductno(cursor.getString(cursor
						.getColumnIndex("productno")));
				barCode.setPkgbarcode(cursor.getString(cursor
						.getColumnIndex("pkgbarcode")));
				barCode.setPkgname(cursor.getString(cursor
						.getColumnIndex("pkgname")));
				barCode.setPkgrate(cursor.getDouble(cursor
						.getColumnIndex("pkgrate")));
				list.add(barCode);
			}
		}
		db.close();
		return list;
	}

	
	public int deleteAllGoods() {
		String sql = " delete from  barcode ";
		SQLiteDatabase db = DbManager.getDb();
		db.execSQL(sql);
		return 0;
	}
	
	/**
	 * 获取总的数量
	 * @param itemno
	 * @return
	 */
	public Double GetTotalCount()
	{
		String sql = "select count(0) from barcode bc inner join product p on bc.[productno]=p.[productno] where 1=1 ";
		
		SQLiteDatabase db=DbManager.getDb();
		  Cursor cursor=db.rawQuery(sql, null);
		  if(cursor!=null)
		  {
			  cursor.moveToFirst();
			  double count = cursor.getDouble(0);
			  return count;
		  }
	   return (double) 0;
	}
}
