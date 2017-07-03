package com.wologic.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wologic.domain.Parameter;
import com.wologic.util.DbManager;

public class ParameterDao {
	
	
	public Parameter getParameterById(int parameterId)
	{
		Parameter p=null;
		
		SQLiteDatabase db = DbManager.getDb();
		String sql = "select * from parameter where parameterId="+parameterId;
		//String[] strings ={parameterId};
		Cursor cursor = db.rawQuery(sql,null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				p=new Parameter();
				p.setParameterId(cursor.getInt(cursor
						.getColumnIndex("parameterId")));
				p.setParametersubId(cursor.getInt(cursor
						.getColumnIndex("parametersubId")));
				p.setParaDiscribe(cursor.getString(cursor
						.getColumnIndex("paraDiscribe")));
				
				p.setParaValue1(cursor.getString(cursor
						.getColumnIndex("paraValue1")));
				p.setParaValue2(cursor.getString(cursor
						.getColumnIndex("paraValue2")));
				p.setParaValue3(cursor.getString(cursor
						.getColumnIndex("paraValue3")));
				p.setParaValue4(cursor.getString(cursor
						.getColumnIndex("paraValue4")));
				p.setParaValue5(cursor.getString(cursor
						.getColumnIndex("paraValue5")));
				p.setParaValue6(cursor.getString(cursor
						.getColumnIndex("paraValue6")));
				break;
			}
		}
		db.close();
		return p;
	}

	
	public int updateParameter(Parameter p)
	{
		String sql="update parameter set paraValue1=?,paraValue2=?,paraValue3=?,paraValue4=?,paraValue5=?,paraValue6=? where parameterId=? ";
		 Object[] bindArgs={p.getParaValue1(),p.getParaValue2(),p.getParaValue3(),p.getParaValue4(),p.getParaValue5(),p.getParaValue6(),p.getParameterId()};
		SQLiteDatabase db=DbManager.getDb();
		db.execSQL(sql,bindArgs);
		db.close();
    	return 0;
    	
	}
}
