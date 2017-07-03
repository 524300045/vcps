package com.wologic.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.User;
import com.wologic.util.DbManager;

public class UserDao {
	
	public User GetUserByNameAndPwd(String code,String pwd)
	{
		User user=null;
        String sql="select * from wuser where code=? and password=? ";
		
		SQLiteDatabase db=DbManager.getDb();
		
		 Cursor cursor=db.rawQuery(sql,new String[]{code,pwd});
		 if(cursor!=null)
		 {
			 while(cursor.moveToNext())
			 {
				  user=new User();
				  user.setCode(cursor.getString(cursor.getColumnIndex("code")));
				  user.setCode(cursor.getString(cursor.getColumnIndex("name")));
				  user.setCode(cursor.getString(cursor.getColumnIndex("state")));
				  user.setCode(cursor.getString(cursor.getColumnIndex("password")));
				  break;
			 }
			
		 }
		db.close();
		return user;
	}

}
