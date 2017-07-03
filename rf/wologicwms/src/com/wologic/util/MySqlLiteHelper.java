package com.wologic.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlLiteHelper extends SQLiteOpenHelper {

	public MySqlLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		Log.i("MySqlLiteHelper", "MySqlLiteHelper");
	}

	public MySqlLiteHelper(Context context) {
		super(context, Constant.db, null, Constant.version);
		Log.i("MySqlLiteHelper", "MySqlLiteHelper");
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createtable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	private void createtable(SQLiteDatabase db) {
		/*String workitemsql = "CREATE TABLE [workitem] ([itemno] TEXT NOT NULL COLLATE NOCASE, [itemtype] TEXT NOT NULL COLLATE NOCASE, [itemstate] TEXT NOT NULL COLLATE NOCASE,  [worktime] DATETIME, [sinfo7] TEXT,  [sinfo6] TEXT, [sinfo5] TEXT, [sinfo4] TEXT, [sinfo3] TEXT,  [sinfo2] TEXT, [sinfo1] TEXT);";
	
		String itemdatasql = "CREATE TABLE [itemdata] ("+
 "[itemno] TEXT NOT NULL COLLATE NOCASE, "+
  "[productno] TEXT NOT NULL COLLATE NOCASE, "+
  "[plan_amount] DOUBLE DEFAULT 0, "+
 "[complete_amount] DOUBLE DEFAULT 0, "+
 "[work_amount] DOUBLE DEFAULT 0, "+
 "[userid] TEXT, "+
 "[deviceid] TEXT, "+
 "[state] TEXT, "+
 "[worktime] DATETIME, "+
 " [Info1] TEXT, "+
 "[Info2] TEXT, "+
 "[info3] TEXT, "+
 " [info4] TEXT, "+
 " [info5] TEXT, "+
 "[info6] TEXT, "+
 "[info7] TEXT, "+
 " [info8] TEXT, "+
 "[info9] TEXT, "+
 " [info10] TEXT);"+
 "CREATE INDEX [no] ON [itemdata] ([itemno] COLLATE NOCASE ASC, [productno] COLLATE NOCASE ASC);";
		String productsql="CREATE TABLE [product] ( "+
  "[productno] TEXT NOT NULL COLLATE NOCASE,  "+
  "[productname] TEXT, "+ 
  "[proinfo11] TEXT,  "+
  "[proinfo10] TEXT,  "+
  "[proinfo9] TEXT,  "+
  "[proinfo8] TEXT,  "+
  "[proinfo7] TEXT,  "+
  "[proinfo6] TEXT,  "+
 "[proinfo5] TEXT,  "+
  "[proinfo4] TEXT,  "+
  "[proinfo3] TEXT,  "+
  "[proinfo2] TEXT,  "+
 " [proinfo1] TEXT,  "+
  "CONSTRAINT [sqlite_autoindex_Product_1] PRIMARY KEY ([productno] COLLATE NOCASE ASC)); ";
		
	 String barcodesql="CREATE TABLE [barcode] ("+
			 " [productno] TEXT NOT NULL COLLATE NOCASE, "+
			 " [pkgbarcode] TEXT NOT NULL COLLATE NOCASE, "+
			 "  [pkgname] TEXT NOT NULL DEFAULT 件, "+
			 " [pkgrate] DOUBLE NOT NULL DEFAULT 1);"+
            "CREATE INDEX [id] ON [barcode] ([productno] COLLATE NOCASE ASC, [pkgbarcode] COLLATE NOCASE ASC);";
	 
	 String userSql="CREATE TABLE [wuser] ("+
 " [code] VARCHAR(12) NOT NULL COLLATE NOCASE, "+
 " [name] VARCHAR(20) NOT NULL,  "+
 " [state] VARCHAR(1) NOT NULL,  "+
 " [password] VARCHAR(20)); "+
" CREATE UNIQUE INDEX [uindex] ON [wuser] ([code] COLLATE NOCASE ASC);";
	 
	 //创建参数表
	 String parameterSql="CREATE TABLE [parameter] ("+
			 "  [parameterId] INTEGER NOT NULL, "+
			 "  [parametersubId] INTEGER NOT NULL DEFAULT 0, "+
			 " [paraDiscribe] TEXT NOT NULL, "+
			 " [paraValue1] TEXT, "+
			 " [paraValue2] TEXT, "+
			 " [paraValue3] TEXT, "+
			 " [paraValue4] TEXT, "+
			 "  [paraValue5] TEXT, "+
			 " [paraValue6] TEXT);";

		
		db.execSQL(workitemsql);// 创建单据主表
     	db.execSQL(itemdatasql);
     	db.execSQL(productsql);
     	db.execSQL(barcodesql);
     	db.execSQL(userSql);
     	db.execSQL(parameterSql);
     	
        
     	db.execSQL("insert into wuser (code,name,state,password) values ('001','沃杰软件','1','001')");
     	
     	//插入Sql
     	String parameterInsertSql="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                                "values (1,0,'服务器地址配置','192.168.10.2','8800','zhangdb','','','')";
     	db.execSQL(parameterInsertSql);
     	
    	String parameterInsertSqlOne="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                "values (2,0,'作业成功自动上传','1','','','','','')";
    	String parameterInsertSqlTwo="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                "values (3,0,'上传成功自动删单','1','','','','','')";
    	String parameterInsertSqlThree="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                "values (4,0,'启用摄像头扫描','0','','','','','')";
    	String parameterInsertSqlFour="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                "values (5,0,'启用消息推送通知','1','','','','','')";
    	
     	String parameterInsertSqlFive="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                "values (6,0,'自动更新周期','','','','','','')";
    	
    	String parameterInsertSql6="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                "values (7,0,'条码位数','','','','','','')";
    	
    	String parameterInsertSql7="insert into  [parameter] (parameterId,parametersubId,paraDiscribe,paraValue1,paraValue2,paraValue3,paraValue4,paraValue5,paraValue6) "+
                "values (8,0,'条码截取位置','','','','','','')";
    	
    	db.execSQL(parameterInsertSqlOne);
    	db.execSQL(parameterInsertSqlTwo);
    	db.execSQL(parameterInsertSqlThree);
     	db.execSQL(parameterInsertSqlFour);
     	
     	db.execSQL(parameterInsertSqlFive);
     	
     	db.execSQL(parameterInsertSql6);
     	db.execSQL(parameterInsertSql7);
     	//db.execSQL("insert into barcode (productno,pkgbarcode,pkgname,pkgrate)  values ('80','12345678','个',5)");
     	
     	String createInfoSql="CREATE TABLE [wbaseinfo] ([ClientId] TEXT, [VersionId] TEXT, [infoId] TEXT,  [infoname] TEXT, [IsShow] TEXT NOT NULL DEFAULT 1, [updatetime] DATETIME, [base1] TEXT, [base2] TEXT, [base3] TEXT);";
     	db.execSQL(createInfoSql);
     	String sqlinfo="insert into wbaseinfo (ClientId,infoId,infoname,IsShow) values ('1','huopin','货品信息','1')";
     	db.execSQL(sqlinfo);
    	String sqlinfo2="insert into wbaseinfo (ClientId,infoId,infoname,IsShow) values ('1','gys','供应商信息','1')";
     	db.execSQL(sqlinfo2);
     	
     	//创建函数功能SQL
     	String createFunctionSql="CREATE TABLE [wfuction] ([ClientId] TEXT, [VersionId] TEXT, [fuctionid] TEXT NOT NULL, [fuctionname] TEXT NOT NULL, "+
 "[fuctionseq] INTEGER, [funinfo1] TEXT, [funinfo2] TEXT, [funinfo3] TEXT, [funinfo4] TEXT, [funinfo5] TEXT, [funinfo6] TEXT);";
    	db.execSQL(createFunctionSql);
    	
    	String functionOne="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','pd','盘点',1)";
    	String functionTwo="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','th','退货',1)";
    	String functionThree="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','ch','出货',1)";
    	String function4="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','sh','收货',1)";
    	
    	String function5="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','fh','发货',1)";
    	String function6="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','rk','入库',1)";
    	String function7="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','dh','订货',1)";
    	String function8="insert into  wfuction (ClientId,fuctionid,fuctionname,fuctionseq) values ('1','gd','更多>',1)";
    	
    	
    	
    	db.execSQL(functionOne);
    	db.execSQL(functionTwo);
    	db.execSQL(functionThree);
    	db.execSQL(function4);
    	db.execSQL(function5);
    	db.execSQL(function6);
    	db.execSQL(function7);
    	db.execSQL(function8);*/
    	
    	
	}
}
