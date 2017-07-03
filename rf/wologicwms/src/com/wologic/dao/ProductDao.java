package com.wologic.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.BarCode;
import com.wologic.domain.Product;
import com.wologic.util.DbManager;

public class ProductDao {

	public int insertProduct(Product product)
	{
		String sql = "insert into product (productno,productname,proinfo11,proinfo10,proinfo9,proinfo8,proinfo7) values (?,?,?,?,?,?,?)";
		SQLiteDatabase db = DbManager.getDb();
		Object[] bindArgs = { product.getProductno(), product.getProductname(),
				product.getProinfo11(),product.getProinfo10(),product.getProinfo9(),product.getProinfo8(),product.getProinfo7()};
		db.execSQL(sql, bindArgs);
		return 0;
	}
	
	public Product  getProduct(String productno)
	{
		Product p=null;
		
		SQLiteDatabase db = DbManager.getDb();
		String sql = "select productno,productname,proinfo11 from product  where productno=? ";
		 String[] bindArgs={productno};
		Cursor cursor = db.rawQuery(sql, bindArgs);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				p=new Product();
				p.setProductno(cursor.getString(cursor.getColumnIndex("productno")));
				p.setProductname(cursor.getString(cursor.getColumnIndex("productname")));
				p.setProinfo11(cursor.getString(cursor.getColumnIndex("proinfo11")));
                break;
			}
		}
		db.close();
		return p;
	}
	
	
	public int deleteAllProduct()
	{
		String sql = "delete from product ";
		SQLiteDatabase db = DbManager.getDb();
		db.execSQL(sql);
		return 0;
	}
	

}
