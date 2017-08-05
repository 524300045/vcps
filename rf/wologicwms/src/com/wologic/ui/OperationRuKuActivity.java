package com.wologic.ui;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.ParameterDao;
import com.wologic.dao.ProductDao;
import com.wologic.dao.RuKuDao;
import com.wologic.domain.BarCode;
import com.wologic.domain.DetailItem;
import com.wologic.domain.GoodsDto;
import com.wologic.domain.ItemData;
import com.wologic.domain.Parameter;
import com.wologic.domain.Product;
import com.wologic.domain.WorkItem;


import com.wologic.util.Common;
import com.wologic.util.Constant;
import com.wologic.util.CsvUtil;
import com.wologic.util.SimpleClient;
import com.wologic.util.Toaster;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OperationRuKuActivity extends Activity {

	private RuKuDao ruKuDao;

	private ItemDataDao itemDataDao;

	private String ruKuCode;// 入库单号

//	private TextView tvupload, tvdel, tvnext, tvopen, tvexport;

	private LinearLayout tvuploadtotal, tvdeltotal, tvnexttotal, tvopentotal, tvexporttotal;
	
	private TextView tbBack;

	private Dialog loadingDialog;

	private ParameterDao parameterDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rukuoperation);
		tvuploadtotal = (LinearLayout) findViewById(R.id.tvuploadtotal);
		tvdeltotal = (LinearLayout) findViewById(R.id.tvdeltotal);
		tvopentotal = (LinearLayout) findViewById(R.id.tvopentotal);
		tvexporttotal = (LinearLayout) findViewById(R.id.tvexporttotal);
		tvnexttotal = (LinearLayout) findViewById(R.id.tvnexttotal);
		tbBack = (TextView) findViewById(R.id.tvback);
		loadingDialog();
		itemDataDao = new ItemDataDao();
		parameterDao = new ParameterDao();
		ruKuDao = new RuKuDao(OperationRuKuActivity.this);
		Intent intent = getIntent();
		if (intent != null) {
			ruKuCode = intent.getStringExtra("itemno");
		}

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
		tvdeltotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDialog("确认删除[入库-" + ruKuCode + "]吗?");
			}
		});
		tvnexttotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OperationRuKuActivity.this,
						RuKuGoodsActivity.class);
				intent.putExtra("itemno", ruKuCode);// 传递入库单号
				startActivity(intent);
				finish();
			}
		});

		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		tvopentotal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				open();
			}
		});

		// 导出
		tvexporttotal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				export();
			}
		});

	}

	private void export() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime) + ".csv";
		Intent intent = new Intent(OperationRuKuActivity.this,
				SaveFileDialogActivity.class);
		intent.putExtra("DefaultFilePath", Environment
				.getExternalStorageDirectory().getPath());
		intent.putExtra("DefaultFileName", dateString);
		intent.putExtra("Ext", ".csv");
		startActivityForResult(intent, 4);
	}

	private void open() {
		Intent intent = new Intent(OperationRuKuActivity.this,
				OpenFileDialogActivity.class);
		intent.putExtra("DefaultFilePath", Environment
				.getExternalStorageDirectory().getPath());
		//intent.putExtra("DefaultFileName", "default.lag");
		intent.putExtra("Ext", ".csv");
		startActivityForResult(intent, 5);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == SaveFileDialogActivity.RESULT_CODE) {
			// 获取文件名
			String FilePathName = data.getStringExtra("FilePathName");
			String suffixname = getSuffix(FilePathName);
			if (!suffixname.equals("csv")) {
				FilePathName += ".csv";
			}

			List<ItemData> itemList = itemDataDao.GetItemDataNoEnd(ruKuCode);
			if (itemList != null && itemList.size() > 0) {
				// 创建CSV文件
				CsvUtil.open(FilePathName);
				CsvUtil.writeCsv(itemList, "w1");
				CsvUtil.flush();
			}

			Toaster.toaster("导出成功");
		}
		if (resultCode == OpenFileDialogActivity.RESULT_CODE) {
			// 获取文件名
			String FilePathName = data.getStringExtra("FilePathName");

			String suffixname = getSuffix(FilePathName);
			if (!suffixname.equals("csv")) {
				Toaster.toaster("打开文件不是CSV格式");
				return;
			}
			
			List<DetailItem> detailList = CsvUtil
					.getImportWorkItemCSV(FilePathName);
			for (DetailItem item : detailList) {
				List<WorkItem> wiList = ruKuDao.getWorkItem(item.getRukuno());
				if (wiList == null || wiList.size() <= 0) {
					if (item.getItemtype().equals("w1")) {
						WorkItem workItem = new WorkItem();
						workItem.setItemno(item.getRukuno());
						workItem.setItemtype("w1");
						workItem.setItemstate("s5");
						ruKuDao.insertWorkItem(workItem);
					}
				}

				List<ItemData> d2list = itemDataDao.GetItemDataByProductNoTwo(
						item.getRukuno(), item.getProductno());
				if (d2list == null || d2list.size() <= 0) {
					ItemData itemData2 = new ItemData();
					itemData2.setItemno(item.getRukuno());
					itemData2.setProductno(item.getProductno());
					itemData2.setPlanamount(item.getProductqty());
					itemData2.setCompleteamount(0d);
					itemData2.setInfo1(item.getSectionid());// 货位
					itemData2.setWork_amount(0d);
					itemData2.setUserid(Common.userID);
					itemData2.setDeviceid(Common.deviceid);
					itemData2.setState("00");
					itemData2.setInfo2("0");
					itemDataDao.insertItemData(itemData2);
				}

			}

			Toaster.toaster("导入成功");
		}

	}

	private String getSuffix(String name) {
		File file = new File(name);
		String fileName = file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return suffix;
	}

	private void upload() {
		if (loadingDialog != null && !loadingDialog.isShowing())
			loadingDialog.show();

		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Date now = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String ctime = formatter.format(now);
					//获取设备序列号
//					TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
//					Common.deviceid = TelephonyMgr.getDeviceId();
					//获取设备名称
//					Common.deviceid = android.os.Build.MANUFACTURER;
					// 单号不存在,下载
					String url = Constant.url
							+ "/services/0/tasks/WriteUploadData";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("edit[itemno]", ruKuCode);// 入库单号
					map.put("edit[type]", "01");// 单据类型
					map.put("edit[finishdate]", URLEncoder.encode(ctime));// 完成时间
					map.put("edit[userid]", Common.userID);// 入库单号
					map.put("edit[deviceid]", Common.deviceid);// 设备ID

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
					// 下载完成
					if (json.opt("result").equals(true)) {
						// 更新状态为已完成
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

	private void del() {
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

		new AlertDialog.Builder(OperationRuKuActivity.this)
				.setTitle("数据删除")
				// 设置对话框标题

				.setMessage(title)
				// 设置显示的内容

				.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// 确定按钮的响应事件

								del();
							}

						})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {// 添加返回按钮

							@Override
							public void onClick(DialogInterface dialog,
									int which) {// 响应事件

							}

						}).show();// 在按键响应事件中显示此对话框

	}
}
