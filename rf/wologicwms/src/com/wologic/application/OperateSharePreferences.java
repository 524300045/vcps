package com.wologic.application;



import android.content.Context;
import android.content.SharedPreferences;

/**
 */
public class OperateSharePreferences {

	private final String TAG = OperateSharePreferences.class.getSimpleName();
	/**
	 */
	private final String database= "db";


	private static OperateSharePreferences my;
	private SharedPreferences mdbInfoSharedPreferences;

	private OperateSharePreferences(Context context) {
		mdbInfoSharedPreferences = context.getSharedPreferences(database, Context.MODE_PRIVATE);
	}

	public static OperateSharePreferences getInstance() {
		if (my == null) {
			my = new OperateSharePreferences(MyApplication.getAppContext());
		}
		return my;
	}


	/**
	 * name
	 * 
	 * @param name
	 */
	public void saveDbFlag(String name) {
		mdbInfoSharedPreferences.edit().putString(database, name).commit();
	}

	/**
	 * @return
	 */
	public String getDbFlag() {
		return mdbInfoSharedPreferences.getString(database, "");
	}

}
