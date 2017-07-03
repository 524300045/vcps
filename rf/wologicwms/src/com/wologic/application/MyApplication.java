package com.wologic.application;



import android.app.Application;

public class MyApplication extends Application {
	
	private static MyApplication application;

	public static synchronized MyApplication getInstance() {
		return application;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
	}

	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	  public static MyApplication getAppContext() {
	        return application;
	    }
}
