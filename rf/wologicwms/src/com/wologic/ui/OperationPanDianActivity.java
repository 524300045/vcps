package com.wologic.ui;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.ParameterDao;
import com.wologic.dao.ProductDao;
import com.wologic.dao.RuKuDao;
import com.wologic.domain.BarCode;
import com.wologic.domain.GoodsDto;
import com.wologic.domain.ItemData;
import com.wologic.domain.Parameter;
import com.wologic.domain.Product;

import com.wologic.util.Common;
import com.wologic.util.Constant;
import com.wologic.util.SimpleClient;
import com.wologic.util.Toaster;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OperationPanDianActivity extends Activity {


	private RuKuDao ruKuDao;
	
	private ItemDataDao itemDataDao;

	private String ruKuCode;// 入库单号

//	private TextView tvupload, tvdel,tvnext;
	
	private LinearLayout tvuploadtotal, tvdeltotal,tvnexttotal;

	private TextView tbBack;


	private Dialog loadingDialog;
	
	private ParameterDao parameterDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandianoperation);
		tvuploadtotal = (LinearLayout) findViewById(R.id.tvuploadtotal);
		tvdeltotal = (LinearLayout) findViewById(R.id.tvdeltotal);
		tbBack = (TextView) findViewById(R.id.tvback);
		tvnexttotal=(LinearLayout) findViewById(R.id.tvnexttotal);
		loadingDialog();
		itemDataDao = new ItemDataDao();
		ruKuDao=new RuKuDao(OperationPanDianActivity.this);
		Intent intent = getIntent();
		if (intent != null) {
			ruKuCode = intent.getStringExtra("itemno");
		}
		parameterDao = new ParameterDao();
		init();
	}

	private void loadingDialog() {
		loadingDialog = new Dialog(this, R.style.dialog_loading);
		loadingDialog.setContentView(R.layout.dialog_loading_2);
		TextView prompt = (TextView) loadingDialog.findViewById(R.id.prompt);
		prompt.setText("正在处理...");
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setCancelable(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}

	private void init() {
		tvuploadtotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				upload();
			}
		});
		tvdeltotal.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				showDialog("确认删除[盘点-"+ruKuCode+"]吗?");
			}});
		
		tvnexttotal.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OperationPanDianActivity.this,
						PanDianGoodsActivity.class);
				intent.putExtra("itemno",ruKuCode);// 传递入库单号
				startActivity(intent);
				finish();
			}});

		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	private void upload() {
		if (loadingDialog != null && !loadingDialog.isShowing())
			loadingDialog.show();

		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					Date now = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String ctime = formatter.format(now);
					//获取设备序列号
//					TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
//					Common.deviceid = TelephonyMgr.getDeviceId();
//					//获取设备名称
//					Common.deviceid = android.os.Build.MANUFACTURER;
					// 单号不存在,下载
					String url = Constant.url
							+ "/services/0/tasks/WriteUploadData";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("edit[itemno]", ruKuCode);// 入库单号
					map.put("edit[type]", "03");// 单据类型
					map.put("edit[finishdate]", URLEncoder.encode(ctime));// 完成时间
					map.put("edit[userid]", Common.userID);// 入库单号
					map.put("edit[deviceid]",Common.deviceid);// 设备ID
					
					List<ItemData> itemList = itemDataDao.GetItemDataOne(ruKuCode,
							"");
					
					map.put("edit[dtlcount]", itemList.size());// 明细数量
					for (int i = 0; i < itemList.size(); i++) {
						map.put("edit[productno" + i + "]", itemList.get(i)
								.getProductno());
						map.put("edit[sys_amount" + i + "]", itemList.get(i)
								.getPlanamount());
						map.put("edit[Finish_amount" + i + "]", 0);
						map.put("edit[work_amount" + i + "]", itemList.get(i)
								.getWork_amount());
						map.put("edit[pkgname" + i + "]", itemList.get(i)
								.getPkgname());
						map.put("edit[sizeid" + i + "]", "");
						map.put("edit[sectionid" + i + "]", itemList.get(i)
								.getInfo1());
						map.put("edit[memo1" + i + "]", "");
						map.put("edit[memo2" + i + "]", "");
					}

					HttpClient client = SimpleClient.getHttpClient();
					String result = com.wologic.util.SimpleClient.doGet(url,
							map);
					JSONObject json = new JSONObject(result);
					System.out.print(result);
					if (loadingDialog != null && loadingDialog.isShowing())
						loadingDialog.dismiss();
					if(json.opt("result").equals(true))
					{
						//更新状态为已完成
						ruKuDao.updateWorkItemStatus(ruKuCode);
						
						Parameter parameterTwo = parameterDao
								.getParameterById(3);
						if (parameterTwo != null
								&& parameterTwo.getParaValue1().equals("1")) {
							// 上传成功后自动删除
							ruKuDao.delWorkItem(ruKuCode);
							itemDataDao.deleteItemData(ruKuCode);
						}
					}
					// 下载完成
					Message msg = new Message();
					msg.what = 2;
					msg.obj = json.opt("message");
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

	
	private void del()
	{
		ruKuDao.delWorkItem(ruKuCode);
		itemDataDao.deleteItemData(ruKuCode);
		Toaster.toaster("删除成功!");
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
				break;
			case 3:
				break;
			default:
				break;
			}
		}
	};
	
	public void showDialog(String title) {
		 
		 new AlertDialog.Builder(OperationPanDianActivity.this).setTitle("数据删除")//设置对话框标题

		 .setMessage(title)//设置显示的内容

		 .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
			 @Override

			 public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

				 del();
			 }

		 }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮

			 @Override

			 public void onClick(DialogInterface dialog, int which) {//响应事件


			 }

		 }).show();//在按键响应事件中显示此对话框

	}
}
