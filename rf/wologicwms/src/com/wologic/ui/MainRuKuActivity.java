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
	private SimpleAdapter adp;// ����������
	private List<Map<String, Object>> mapList;// ��������Դ
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
			imgscan.setVisibility(View.VISIBLE);//������ʾ
		}
		else
		{
			imgscan.setVisibility(View.GONE);//���ò���ʾ
		}

	}
	/**
	 * ��ȡPDA��Ϣ
	 */
	private void getPdaInfo(){
//		PdaInfo pda = new PdaInfo();
//		pda = pdainfoDao.getPdaInfo();
//		Common.deviceid = pda.getPdaName();
		//��Ϊ�ӱ����ļ���
		/*String DeviceName = FileOperation.getDeviceName();
		if(DeviceName.indexOf("error")!=-1){//����error��Ϣ
			Toast.makeText(MainRuKuActivity.this, "��ȡ�豸��Ϣʧ��!", 1000)
			.show();
		}
		else if(DeviceName.indexOf("Wologic")!=-1){//Ĭ��ֵ
			Toast.makeText(MainRuKuActivity.this, "��δ�����豸����,��ǰ������!", 1000)
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
				// ��ʾɨ�赽������
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

	// ����ⵥ�б�
	private void bindlist() {
		List<WorkItem> list = new ArrayList<WorkItem>();
		list = ruKuDao.getWorkItem("", "w1");
		mapList = new ArrayList<Map<String, Object>>();
		for (WorkItem item : list) {
			Long count = itemDataDao.getTotalCount(item.getItemno());
			double num = itemDataDao.GetTotalNum(item.getItemno());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemno", "[���]" + item.getItemno());
			if (item.getItemstate().equals("s10")) {
				map.put("state", "��");
			} 
			else if(item.getItemstate().equals("s09")){//���ڲ���
				map.put("state", "��");
			}
			else{//��δ����s5
				map.put("state", "��");
			}
			map.put("time", Common.getStringDate(item.getWorktime()));
			map.put("count", "[����]" + count);
			map.put("num", "[����]" + num);
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
							// ���Ŵ���,�ж�״̬
							if (!workList.get(0).getItemtype().equals("w1")) {
								// ҳ����ת
								etrukucode.selectAll();
								Toaster.toaster("��ǰ���ݲ�����ⵥ");
							} else if (workList.get(0).getItemstate()
									.equals("s10")) {
								// Toaster.toaster(code + "�����");
								Intent intent = new Intent(
										MainRuKuActivity.this,
										RuKuGoodsActivity.class);
								intent.putExtra("itemno", code);// ������ⵥ��
								startActivity(intent);

							} else {

								//�����Ѿ����ڵ���
								List<ItemData> itemDataList=itemDataDao.GetItemDataEnd(code);
								if(itemDataList!=null&&itemDataList.size()>0)
								{
									// ��
									Intent intent = new Intent(MainRuKuActivity.this,
											RuKuGoodsActivity.class);
									intent.putExtra("itemno",code);// ������ⵥ��
									startActivity(intent);
								}
								else
								{
									getDetail(code);
								}
							}
						} else {
							// ���Ų����ڣ��ӷ��������أ�Ȼ����ת
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

		// ȷ����ť
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String code = etrukucode.getText().toString().trim();
				if (code.equals("")) {
					etrukucode.selectAll();
					Toaster.toaster("��������ⵥ��");
					return;
				}

				List<WorkItem> workList = ruKuDao.getWorkItem(code);
				if (workList != null && workList.size() > 0) {
					// ���Ŵ���,�ж�״̬
					if (!workList.get(0).getItemtype().equals("w1")) {
						// ҳ����ת
						etrukucode.selectAll();
						Toaster.toaster("��ǰ���ݲ�����ⵥ");
					} else if (workList.get(0).getItemstate().equals("s10")) {
						Intent intent = new Intent(MainRuKuActivity.this,
								RuKuGoodsActivity.class);
						intent.putExtra("itemno", code);// ������ⵥ��
						startActivity(intent);
					} else {
						//�����Ѿ����ڵ���
						List<ItemData> itemDataList=itemDataDao.GetItemDataEnd(code);
						if(itemDataList!=null&&itemDataList.size()>0)
						{
							// ��
							Intent intent = new Intent(MainRuKuActivity.this,
									RuKuGoodsActivity.class);
							intent.putExtra("itemno",code);// ������ⵥ��
							startActivity(intent);
						}
						else
						{
							getDetail(code);
						}
						
					}
				} else {
					// ���Ų����ڣ��ӷ��������أ�Ȼ����ת
					downdata(code);
				}
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ��ȡ��ǰѡ�����ⵥ��
				TextView tvcode = (TextView) arg1.findViewById(R.id.tvcode);// ��Դ����
				//�����жϱ������ݿ��Ƿ��Ѿ������˵��ţ����������������ȡ�ص��ŵ���ϸ��Ϣ			
				final String code = tvcode.getText().toString().trim();
				if(!itemDataDao.ValidateExistOfItemnoInfoFromLocal(tvcode.getText().toString().trim())){
					//�����ڣ�ǰ����������
					getDetail(code);
				}
				Intent intent = new Intent(MainRuKuActivity.this,
						OperationRuKuActivity.class);
				intent.putExtra("itemno", tvcode.getText());// ������ⵥ��
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
				msg.obj = "��ȡ������ⵥ��Ϣ";
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

					// ��������
					for (DetailItem item : detailList)
					{
						
						List<ItemData>  dlist=itemDataDao.GetItemDataByProductNoTwo(code, item.getProductno());
						if(dlist==null||dlist.size()<=0)
						{
							// �������ݿ�
							ItemData itemData2 = new ItemData();
							itemData2.setItemno(code);
							itemData2.setProductno(item.getProductno());
							itemData2.setPlanamount(item
									.getProductqty());
							itemData2.setCompleteamount(0d);
							itemData2.setInfo1(item.getSectionid());// ��λ
							itemData2.setWork_amount(0d);
							itemData2.setUserid(Common.userID);
							itemData2.setDeviceid(Common.deviceid);
							itemData2.setState("00");
							itemData2.setInfo2("0");
							itemDataDao.insertItemData(itemData2);
						}
					
				 }
					// ҳ����ת
					Message msg = new Message();
					msg.what = 1;
					msg.obj = code;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "��ȡ������ⵥ��Ϣ";
					handler.sendMessage(msg);
				}
			}
			
		} catch (Exception e) {
			System.out.print(e.getMessage());
			Message msg = new Message();
			msg.what = 2;
			msg.obj = "�����쳣,���鵥���Ƿ����";
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
								// ������ⵥ
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
							// ��ѯ�����������ĵ���
							Message msg = new Message();
							msg.what = 2;
							msg.obj = "�޴���������";
							handler.sendMessage(msg);
						}
					}
					

				} catch (Exception e) {
					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "�����쳣,���鵥���Ƿ����";
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
				// ��
				Intent intent = new Intent(MainRuKuActivity.this,
						RuKuGoodsActivity.class);
				intent.putExtra("itemno", msg.obj.toString());// ������ⵥ��
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
