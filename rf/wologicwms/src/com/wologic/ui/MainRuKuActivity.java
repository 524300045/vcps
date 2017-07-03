package com.wologic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.ParameterDao;
import com.wologic.dao.RuKuDao;
import com.wologic.domain.DetailItem;
import com.wologic.domain.ItemData;
import com.wologic.domain.Parameter;
import com.wologic.domain.SearchWorkItem;
import com.wologic.domain.WorkItem;
import com.wologic.util.Common;
import com.wologic.util.Constant;
import com.wologic.util.Toaster;

public class MainRuKuActivity extends Activity {

	private RuKuDao ruKuDao;
	private TextView tbBack;
	private EditText etrukucode;
	private ListView lv;
	private Button btnSure;
	private ItemDataDao itemDataDao;
	private ImageView imgscan;
	private SimpleAdapter adp;// 定义适配器
	private List<Map<String, Object>> mapList;// 定义数据源
	private ParameterDao parameterDao;
	//private PDAInfoDao pdainfoDao;
	private final static int SCANNIN_GREQUEST_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rukumain);
		tbBack = (TextView) findViewById(R.id.tvback);
		etrukucode = (EditText) findViewById(R.id.etrukucode);
		btnSure = (Button) findViewById(R.id.btnSure);
		lv = (ListView) findViewById(R.id.lv);
		etrukucode.requestFocus();
		ruKuDao = new RuKuDao(MainRuKuActivity.this);
		itemDataDao = new ItemDataDao();
		parameterDao=new ParameterDao();
		//pdainfoDao = new PDAInfoDao();
		initEvent();
		getPdaInfo();
		imgscan = (ImageView) findViewById(R.id.imgscan1);
		imgscan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainRuKuActivity.this,
						MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		
		Parameter parameterThree=parameterDao.getParameterById(4);
		if(parameterThree!=null&&parameterThree.getParaValue1().equals("1"))
		{
			imgscan.setVisibility(View.VISIBLE);//设置显示
		}
		else
		{
			imgscan.setVisibility(View.GONE);//设置不显示
		}

	}
	/**
	 * 获取PDA信息
	 */
	private void getPdaInfo(){
//		PdaInfo pda = new PdaInfo();
//		pda = pdainfoDao.getPdaInfo();
//		Common.deviceid = pda.getPdaName();
		//改为从本地文件拿
		/*String DeviceName = FileOperation.getDeviceName();
		if(DeviceName.indexOf("error")!=-1){//含有error信息
			Toast.makeText(MainRuKuActivity.this, "获取设备信息失败!", 1000)
			.show();
		}
		else if(DeviceName.indexOf("Wologic")!=-1){//默认值
			Toast.makeText(MainRuKuActivity.this, "尚未设置设备名称,请前往设置!", 1000)
			.show();
		}
		Common.deviceid = DeviceName;*/
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				etrukucode.setText(bundle.getString("result"));

			}
			break;
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		bindlist();
	}

	// 绑定入库单列表
	private void bindlist() {
		List<WorkItem> list = new ArrayList<WorkItem>();
		list = ruKuDao.getWorkItem("", "w1");
		mapList = new ArrayList<Map<String, Object>>();
		for (WorkItem item : list) {
			Long count = itemDataDao.getTotalCount(item.getItemno());
			double num = itemDataDao.GetTotalNum(item.getItemno());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemno", "[入库]" + item.getItemno());
			if (item.getItemstate().equals("s10")) {
				map.put("state", "●");
			} 
			else if(item.getItemstate().equals("s09")){//正在操作
				map.put("state", "○");
			}
			else{//尚未操作s5
				map.put("state", "☆");
			}
			map.put("time", Common.getStringDate(item.getWorktime()));
			map.put("count", "[条数]" + count);
			map.put("num", "[总数]" + num);
			map.put("code", item.getItemno());
			mapList.add(map);
		}

		adp = new SimpleAdapter(this, mapList, R.layout.listitemruku,
				new String[] { "itemno", "state", "time", "count", "num",
						"code" }, new int[] { R.id.tvruku, R.id.tvstate,
						R.id.tvtime, R.id.tvcount, R.id.tvnum, R.id.tvcode });
		lv.setAdapter(adp);

	}

	private void initEvent() {
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		etrukucode.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:

						final String code = etrukucode.getText().toString()
								.trim();
						if (code.equals("")) {
							return true;
						}

						List<WorkItem> workList = ruKuDao.getWorkItem(code);
						if (workList != null && workList.size() > 0) {
							// 单号存在,判断状态
							if (!workList.get(0).getItemtype().equals("w1")) {
								// 页面跳转
								etrukucode.selectAll();
								Toaster.toaster("当前单据不是入库单");
							} else if (workList.get(0).getItemstate()
									.equals("s10")) {
								// Toaster.toaster(code + "已完成");
								Intent intent = new Intent(
										MainRuKuActivity.this,
										RuKuGoodsActivity.class);
								intent.putExtra("itemno", code);// 传递入库单号
								startActivity(intent);

							} else {

								//本地已经存在单号
								List<ItemData> itemDataList=itemDataDao.GetItemDataEnd(code);
								if(itemDataList!=null&&itemDataList.size()>0)
								{
									// 绑定
									Intent intent = new Intent(MainRuKuActivity.this,
											RuKuGoodsActivity.class);
									intent.putExtra("itemno",code);// 传递入库单号
									startActivity(intent);
								}
								else
								{
									getDetail(code);
								}
							}
						} else {
							// 单号不存在，从服务器下载，然后跳转
							downdata(code);
						}
					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;
				}
				return false;
			}
		});

		// 确定按钮
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String code = etrukucode.getText().toString().trim();
				if (code.equals("")) {
					etrukucode.selectAll();
					Toaster.toaster("请输入入库单号");
					return;
				}

				List<WorkItem> workList = ruKuDao.getWorkItem(code);
				if (workList != null && workList.size() > 0) {
					// 单号存在,判断状态
					if (!workList.get(0).getItemtype().equals("w1")) {
						// 页面跳转
						etrukucode.selectAll();
						Toaster.toaster("当前单据不是入库单");
					} else if (workList.get(0).getItemstate().equals("s10")) {
						Intent intent = new Intent(MainRuKuActivity.this,
								RuKuGoodsActivity.class);
						intent.putExtra("itemno", code);// 传递入库单号
						startActivity(intent);
					} else {
						//本地已经存在单号
						List<ItemData> itemDataList=itemDataDao.GetItemDataEnd(code);
						if(itemDataList!=null&&itemDataList.size()>0)
						{
							// 绑定
							Intent intent = new Intent(MainRuKuActivity.this,
									RuKuGoodsActivity.class);
							intent.putExtra("itemno",code);// 传递入库单号
							startActivity(intent);
						}
						else
						{
							getDetail(code);
						}
						
					}
				} else {
					// 单号不存在，从服务器下载，然后跳转
					downdata(code);
				}
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 获取当前选择的入库单号
				TextView tvcode = (TextView) arg1.findViewById(R.id.tvcode);// 来源单号
				//首先判断本地数据库是否已经包含此单号，不包含则向服务器取回单号的详细信息			
				final String code = tvcode.getText().toString().trim();
				if(!itemDataDao.ValidateExistOfItemnoInfoFromLocal(tvcode.getText().toString().trim())){
					//不存在，前往下载数据
					getDetail(code);
				}
				Intent intent = new Intent(MainRuKuActivity.this,
						OperationRuKuActivity.class);
				intent.putExtra("itemno", tvcode.getText());// 传递入库单号
				startActivity(intent);
			}
		});

	}
	
	private void getDetail(final String code)
	{
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				
		String url = Constant.url
				+ "/services/0/products/ProductInInfo";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("edit[id]", code);

		try {
			HttpClient client = com.wologic.util.SimpleClient
					.getHttpClient();
			
			String result = com.wologic.util.SimpleClient
					.doGet(url, map);

			JSONObject json = new JSONObject(result);
			if(json.optString("result").equals("false"))
			{
				Message msg = new Message();
				msg.what = 2;
				msg.obj = "获取不到入库单信息";
				handler.sendMessage(msg);
			}
			else
			{
				JSONObject json2 = new JSONObject(json
						.optString("result"));
				List<DetailItem> detailList = JSON.parseArray(
						json2.optString("data"), DetailItem.class);
				if (detailList != null && detailList.size() > 0)
				{

					// 插入主表
					for (DetailItem item : detailList)
					{
						
						List<ItemData>  dlist=itemDataDao.GetItemDataByProductNoTwo(code, item.getProductno());
						if(dlist==null||dlist.size()<=0)
						{
							// 插入数据库
							ItemData itemData2 = new ItemData();
							itemData2.setItemno(code);
							itemData2.setProductno(item.getProductno());
							itemData2.setPlanamount(item
									.getProductqty());
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
					// 页面跳转
					Message msg = new Message();
					msg.what = 1;
					msg.obj = code;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "获取不到入库单信息";
					handler.sendMessage(msg);
				}
			}
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			Message msg = new Message();
			msg.what = 2;
			msg.obj = "网络异常,请检查单号是否存在";
			handler.sendMessage(msg);
		}
			}
		});
		mThread.start();
	}

	private void downdata(final String code) {
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/services/0/tasks/SearchWorkTask";

					Map<String, Object> mapSearch = new HashMap<String, Object>();
					mapSearch.put("edit[userid]", Common.userID);
					mapSearch.put("edit[deviceid]", Common.deviceid);
					mapSearch.put("edit[type]", "01");

					String resultSearch = com.wologic.util.SimpleClient.doGet(
							searchUrl, mapSearch);
					JSONObject jsonSearch = new JSONObject(resultSearch);
					if(jsonSearch.optString("result").equals("false"))
					{
						
						Message msg = new Message();
						msg.what = 2;
						msg.obj =jsonSearch.optString("message");
						handler.sendMessage(msg);
					}
					else
					{
						JSONObject jsonSearch2 = new JSONObject(jsonSearch
								.optString("result"));
						List<SearchWorkItem> searchWorkList = JSON.parseArray(
								jsonSearch2.optString("data"), SearchWorkItem.class);
						if (searchWorkList != null && searchWorkList.size() > 0) {
							for (SearchWorkItem item : searchWorkList) {
								// 插入入库单
								List<WorkItem> wiList=ruKuDao.getWorkItem(item.getItemno());
								if(wiList==null||wiList.size()<=0)
								{
									WorkItem workItem = new WorkItem();
									workItem.setItemno(item.getItemno());
									workItem.setItemtype("w1");
									workItem.setItemstate("s5");
									ruKuDao.insertWorkItem(workItem);
								}
							}

							getDetail(code);

						} else {
							// 查询不到待工作的单号
							Message msg = new Message();
							msg.what = 2;
							msg.obj = "无待工作单号";
							handler.sendMessage(msg);
						}
					}
					

				} catch (Exception e) {
					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "网络异常,请检查单号是否存在";
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
				Intent intent = new Intent(MainRuKuActivity.this,
						RuKuGoodsActivity.class);
				intent.putExtra("itemno", msg.obj.toString());// 传递入库单号
				startActivity(intent);
				break;
			case 2:
				etrukucode.selectAll();
				Toaster.toaster(msg.obj.toString());
				break;
			case 3:
				break;
			default:
				break;
			}
		}
	};

}
