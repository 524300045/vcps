package com.wologic.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.application.OperateSharePreferences;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ParameterDao;
import com.wologic.dao.ProductDao;
import com.wologic.dao.WBaseInfoDao;
import com.wologic.domain.BarCode;
import com.wologic.domain.GoodsDto;
import com.wologic.domain.Parameter;
import com.wologic.domain.Product;
import com.wologic.domain.WBaseInfo;
import com.wologic.util.Common;
import com.wologic.util.Constant;
import com.wologic.util.DbManager;
import com.wologic.util.SimpleClient;
import com.wologic.util.Toaster;

public class SplashActivity extends Activity {

	private GoodsDao goodsDao;

	private ProductDao productDao;

	private WBaseInfoDao basicInfoDao;

	private Dialog loadingDialog;

	private ParameterDao parameterDao;
	
	//private PDAInfoDao pdainfoDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View view = getLayoutInflater().inflate(R.layout.activity_splash, null);
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1000);
		basicInfoDao = new WBaseInfoDao();
		productDao = new ProductDao();
		goodsDao = new GoodsDao();
		parameterDao = new ParameterDao();
		//pdainfoDao = new PDAInfoDao();
		
		loadingDialog();
		getPdaInfo();
		createDb();//创建数据库‘
		setTimeZone();
		// 读取客户端信息
		Properties pro = new Properties();
		try {
    			pro.load(SplashActivity.class
					.getResourceAsStream("/assets/property.properties"));
			String clientID = pro.getProperty("clientid");
			Common.clientId = clientID;
		} catch (IOException e) {
			Toast.makeText(SplashActivity.this, "读取配置文件异常", 2000).show();
		}

		DbManager.getInstance(this);
		

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {

			
				
				Parameter p = parameterDao.getParameterById(6);
				if (p != null) {
					if (p.getParaValue1() != null
							&& !p.getParaValue1().toString().equals("")) {
						int day = Integer.valueOf(p.getParaValue1());// 自动更新周期
						WBaseInfo basicInfo = basicInfoDao
								.GetBasicInfo("huopin");
						if (basicInfo != null
								&& basicInfo.getUpdatetime() != null) {
							Date date_start = basicInfo.getUpdatetime();
							Date date_end = new Date();
							int dayDiff = daysBetween(date_start, date_end);
							if (dayDiff > day) {
								// 重新下载
								downdangan();
							} else {
								skip();
							}
						} else {
							skip();
						}
					} else {
						skip();
					}
				} else {
					skip();
				}

			}
		});
		view.setAnimation(animation);
	
	
		setContentView(view);

	}
	private void setTimeZone(){
		AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setTimeZone("America/Santiago");
	}
	private void getPdaInfo(){
//		PdaInfo pda = new PdaInfo();
//		pda = pdainfoDao.getPdaInfo();
//		Common.deviceid = pda.getPdaName();
	}
	public static final int daysBetween(Date early, Date late) {

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	private void loadingDialog() {
		loadingDialog = new Dialog(this, R.style.dialog_loading);
		loadingDialog.setContentView(R.layout.dialog_loading_2);
		TextView prompt = (TextView) loadingDialog.findViewById(R.id.prompt);
		prompt.setText("正在下载...");
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setCancelable(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void createDb()
	{
		File f = new File("/data/data/com.wologic/databases/adpa.db");
		if (f.exists()) {

			String flag = OperateSharePreferences.getInstance().getDbFlag();
			if (flag.equals("")) {
				f.delete();
				File file = new File("/data/data/com.wologic/databases");
				if(!file.exists())
				{
					file.mkdir();
				}
				boolean result=CopyAssetsFile("adpa.db","/data/data/com.wologic/databases");
				OperateSharePreferences.getInstance().saveDbFlag("1");
			}

		} else {
			File file = new File("/data/data/com.wologic/databases");
			if(!file.exists())
			{
				file.mkdir();
			}

			boolean result=CopyAssetsFile("adpa.db","/data/data/com.wologic/databases");
			OperateSharePreferences.getInstance().saveDbFlag("1");
		}
	}
	

private Boolean CopyAssetsFile(String filename, String des) {

    Boolean isSuccess = true;

    //复制安卓apk的assets目录下任意路径的单个文件到des文件夹，注意是否对des有写权限

    AssetManager assetManager = this.getAssets();

   InputStream in = null;

   OutputStream out = null;

   try {

       in = assetManager.open(filename);

       String newFileName = des + "/" + filename;

       out = new FileOutputStream(newFileName);

       byte[] buffer = new byte[1024];

       int read;

       while ((read = in.read(buffer)) != -1) {

           out.write(buffer, 0, read);

       }

       in.close();

       in = null;

       out.flush();

       out.close();

       out = null;

    } catch (Exception e) {

      e.printStackTrace();

      isSuccess = false;

    }

    return isSuccess;

}



	private void downdangan() {
		if (loadingDialog != null && !loadingDialog.isShowing())
			loadingDialog.show();

		final List<BarCode> goodsAllList = goodsDao.GetAlllist("");

		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 单号不存在,下载
					String url = Constant.url
							+ "/services/0/products/ProductInfo";
					Map<String, Object> map = new HashMap<String, Object>();
					HttpClient client = SimpleClient.getHttpClient();
					String result = com.wologic.util.SimpleClient.doGet(url,
							map);
					JSONObject json = new JSONObject(result);
					JSONObject json2 = new JSONObject(json.optString("result"));
					System.out.print(result);
					List<BarCode> barCodeList = new ArrayList<BarCode>();
					List<GoodsDto> goodsList = JSON.parseArray(
							json2.optString("data"), GoodsDto.class);
					if (goodsList != null && goodsList.size() > 0) {
						for (GoodsDto item : goodsList) {
							// 先判断商品信息是否存在
							Product p = productDao.getProduct(item
									.getProductno());
							if (p == null) {
								// 添加商品信息
								Product p1 = new Product();
								p1.setProductno(item.getProductno());
								p1.setProductname(item.getProname());
								p1.setProinfo11(item.getDanjia());// 价格
								p1.setProinfo10(item.getChandi());// 产地
								p1.setProinfo9(item.getGuige());// 规格
								p1.setProinfo8(item.getKucun());
								p1.setProinfo7(item.getPingpai());
								productDao.insertProduct(p1);
							}
							if (!item.getBarcode().equals("")) {

								List<BarCode> barList = goodsDao
										.GetAlllistByProductAndBarCodeAndPkg(
												item.getProductno(),
												item.getBarcode(),
												item.getDanwei());
								// 判断条码信息是否存在
								if (barList == null || barList.size() <= 0) {
									// 不存在，添加
									BarCode barCode = new BarCode();
									barCode.setProductno(item.getProductno());
									barCode.setPkgbarcode(item.getBarcode());
									barCode.setPkgname(item.getDanwei());
									barCode.setPkgrate(1D);
									goodsDao.InsertGoods(barCode);
								}
							}

							if (!item.getSupbarcode1().equals("")) {
								List<BarCode> barList = goodsDao
										.GetAlllistByProductAndBarCodeAndPkg(
												item.getProductno(),
												item.getSupbarcode1(),
												item.getSupdanwei1());
								// 判断条码信息是否存在
								if (barList == null || barList.size() <= 0) {
									// 不存在，添加
									BarCode barCode = new BarCode();
									barCode.setProductno(item.getProductno());
									barCode.setPkgbarcode(item.getSupbarcode1());
									barCode.setPkgname(item.getSupdanwei1());
									if (item.getSupxishu1().equals("")) {
										barCode.setPkgrate(0d);
									} else {
										barCode.setPkgrate(Double
												.parseDouble(item
														.getSupxishu1()));
									}

									goodsDao.InsertGoods(barCode);
								}
							}

							if (!item.getSupbarcode2().equals("")) {
								List<BarCode> barList = goodsDao
										.GetAlllistByProductAndBarCodeAndPkg(
												item.getProductno(),
												item.getSupbarcode2(),
												item.getSupdanwei2());
								// 判断条码信息是否存在
								if (barList == null || barList.size() <= 0) {
									// 不存在，添加
									BarCode barCode = new BarCode();
									barCode.setProductno(item.getProductno());
									barCode.setPkgbarcode(item.getSupbarcode2());
									barCode.setPkgname(item.getSupdanwei2());
									if (item.getSupxishu1().equals("")) {
										barCode.setPkgrate(0d);
									} else {
										barCode.setPkgrate(Double
												.parseDouble(item
														.getSupxishu2()));
									}

									goodsDao.InsertGoods(barCode);
								}
							}

						}
					}

					if (loadingDialog != null && loadingDialog.isShowing())
						loadingDialog.dismiss();
					basicInfoDao.UpdateBasicInfoTime("huopin");// 更新时间
					// 下载完成
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "下载完成";
					handler.sendMessage(msg);

				} catch (Exception e) {
					if (loadingDialog != null && loadingDialog.isShowing())
						loadingDialog.dismiss();

					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "网络异常";
					handler.sendMessage(msg);
				}

			}
		});
		mThread.start();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// 绑定
				break;
			case 2:
				Toaster.toaster(msg.obj.toString());
				Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
				break;
			case 3:
				break;
			default:
				break;
			}
		}
	};

	private void skip() {

		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
