package com.wologic.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wologic.R;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.RuKuDao;
import com.wologic.domain.WorkItem;
import com.wologic.util.Common;
import com.wologic.util.Toaster;

public class MainPanDianActivity extends Activity {

	private RuKuDao ruKuDao;
	private TextView tbBack;
	private EditText etrukucode;
	private ListView lv;
	private Button btnSure,btngen;
	private ItemDataDao itemDataDao;
	private SimpleAdapter adp;// 定义适配器
	private List<Map<String, Object>> mapList;// 定义数据源

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandianmain);
		tbBack = (TextView) findViewById(R.id.tvback);
		etrukucode = (EditText) findViewById(R.id.etrukucode);
		btnSure = (Button) findViewById(R.id.btnSure);
		btngen=(Button) findViewById(R.id.btngen);//生成单号
		
		lv = (ListView) findViewById(R.id.lv);
		etrukucode.requestFocus();
		ruKuDao = new RuKuDao(MainPanDianActivity.this);
		itemDataDao = new ItemDataDao();
		getPdaInfo();
		initEvent();
	}

	@Override
	protected void onStart() {
		super.onStart();
		bindlist();
	}
	/**
	 * 获取PDA信息
	 */
	private void getPdaInfo(){
//		PdaInfo pda = new PdaInfo();
//		pda = pdainfoDao.getPdaInfo();
//		Common.deviceid = pda.getPdaName();
		//改为从本地文件拿
		//String DeviceName = FileOperation.getDeviceName();
		/*if(DeviceName.indexOf("error")!=-1){//含有error信息
			Toast.makeText(this, "获取设备信息失败!", 1000)
			.show();
		}
		else if(DeviceName.indexOf("Wologic")!=-1){//默认值
			Toast.makeText(this, "尚未设置设备名称,请前往设置!", 1000)
			.show();
		}
		Common.deviceid = DeviceName;*/
//		Toaster.toaster(DeviceName);
	}
	// 绑定入库单列表
	private void bindlist() {
		List<WorkItem> list = new ArrayList<WorkItem>();
		list = ruKuDao.getWorkItem("", "w3");
		mapList = new ArrayList<Map<String, Object>>();
		for (WorkItem item : list) {
			Long count = itemDataDao.getTotalCount(item.getItemno());
			double num = itemDataDao.GetTotalNum(item.getItemno());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemno", "[盘点]" + item.getItemno());
			if (item.getItemstate().equals("s10")) {
				map.put("state", "●");
			} else {
				map.put("state", "○");
			}
			map.put("time", Common.getStringDate(item.getWorktime()));
			map.put("count", "[条数]" + count);
			map.put("num", "[总数]" + num);
			map.put("code", item.getItemno());
			mapList.add(map);
		}

		adp = new SimpleAdapter(this, mapList, R.layout.listitemruku,
				new String[] { "itemno", "state", "time", "count", "num","code" },
				new int[] { R.id.tvruku, R.id.tvstate, R.id.tvtime,
						R.id.tvcount, R.id.tvnum,R.id.tvcode });
		lv.setAdapter(adp);

	}
	/**
	 * 验证手写盘点单号的合法性
	 * @param itemNo
	 * @return
	 */
	private boolean validatepd(String itemNo){
		if((itemNo.indexOf("pd")>=0)&&itemNo.length()==16){
			return true;
		}
		else{
			return false;
		}
	}
	private void initEvent() {
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		
		// 确定按钮
		btnSure.setOnClickListener(new OnClickListener() {

			//首先要验证单号的合法性
			@Override
			public void onClick(View arg0) {
				final String code = etrukucode.getText().toString()
						.trim();
				if (code.equals("")) {
					etrukucode.selectAll();
					Toaster.toaster("请输入盘点单号!");
					return ;
				}
				if(!validatepd(code)){
					Toaster.toaster("单号不合要求，请重新输入单号!");
					return;
				}
				List<WorkItem> workList = ruKuDao.getWorkItem(code);
				if (workList != null && workList.size() > 0)
				{
					// 单号存在,判断状态
					if (!workList.get(0).getItemtype().equals("w3")) {
						// 页面跳转
						etrukucode.selectAll();
						Toaster.toaster("当前单据不是盘点单");
					
					} else if (workList.get(0).getItemstate()
							.equals("s10")) {
						//Toaster.toaster( code + "已完成");
						// 页面跳转
						Intent intent = new Intent(MainPanDianActivity.this,
								PanDianGoodsActivity.class);
						intent.putExtra("itemno", code);// 传递入库单号
						startActivity(intent);
						
					} else {
						// 页面跳转
						Intent intent = new Intent(MainPanDianActivity.this,
								PanDianGoodsActivity.class);
						intent.putExtra("itemno", code);// 传递入库单号
						startActivity(intent);
					}
				} else {
					//插入主表
					WorkItem workItem=new WorkItem();
					workItem.setItemno(code);
					workItem.setItemtype("w3");
					workItem.setItemstate("s5");
					
					ruKuDao.insertWorkItem(workItem);
					
					// 页面跳转
					Intent intent = new Intent(MainPanDianActivity.this,
							PanDianGoodsActivity.class);
					intent.putExtra("itemno", code);// 传递入库单号
					startActivity(intent);

				}
			}
		});

		btngen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String pandiancode="pd"+dateToString(new Date());
				etrukucode.setText(pandiancode);
			}
			
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				   //获取当前选择的入库单号
				 TextView   tvcode= (TextView)arg1.findViewById(R.id.tvcode);//来源单号    
					Intent intent = new Intent(
							MainPanDianActivity.this,
							OperationPanDianActivity.class);
					intent.putExtra("itemno", tvcode.getText());// 传递入库单号
					startActivity(intent);
			}
		});

	}
	
	public static String dateToString(Date time){ 
	    SimpleDateFormat formatter; 
	    formatter = new SimpleDateFormat ("yyyyMMddHHmmss"); 
	    String ctime = formatter.format(time); 
	    return ctime; 
	} 

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// 绑定
				Intent intent = new Intent(MainPanDianActivity.this,
						PanDianGoodsActivity.class);
				intent.putExtra("itemno", msg.obj.toString());// 传递入库单号
				startActivity(intent);
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

}
