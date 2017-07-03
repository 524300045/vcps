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

	private String ruKuCode;// ��ⵥ��

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
		prompt.setText("���ڴ���...");
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
				showDialog("ȷ��ɾ��[�̵�-"+ruKuCode+"]��?");
			}});
		
		tvnexttotal.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OperationPanDianActivity.this,
						PanDianGoodsActivity.class);
				intent.putExtra("itemno",ruKuCode);// ������ⵥ��
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
					//��ȡ�豸���к�
//					TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE); 
//					Common.deviceid = TelephonyMgr.getDeviceId();
//					//��ȡ�豸����
//					Common.deviceid = android.os.Build.MANUFACTURER;
					// ���Ų�����,����
					String url = Constant.url
							+ "/services/0/tasks/WriteUploadData";
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("edit[itemno]", ruKuCode);// ��ⵥ��
					map.put("edit[type]", "03");// ��������
					map.put("edit[finishdate]", URLEncoder.encode(ctime));// ���ʱ��
					map.put("edit[userid]", Common.userID);// ��ⵥ��
					map.put("edit[deviceid]",Common.deviceid);// �豸ID
					
					List<ItemData> itemList = itemDataDao.GetItemDataOne(ruKuCode,
							"");
					
					map.put("edit[dtlcount]", itemList.size());// ��ϸ����
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
						//����״̬Ϊ�����
						ruKuDao.updateWorkItemStatus(ruKuCode);
						
						Parameter parameterTwo = parameterDao
								.getParameterById(3);
						if (parameterTwo != null
								&& parameterTwo.getParaValue1().equals("1")) {
							// �ϴ��ɹ����Զ�ɾ��
							ruKuDao.delWorkItem(ruKuCode);
							itemDataDao.deleteItemData(ruKuCode);
						}
					}
					// �������
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
					msg.obj = "�����쳣";
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
		Toaster.toaster("ɾ���ɹ�!");
	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// ��
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
		 
		 new AlertDialog.Builder(OperationPanDianActivity.this).setTitle("����ɾ��")//���öԻ������

		 .setMessage(title)//������ʾ������

		 .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {//���ȷ����ť
			 @Override

			 public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�

				 del();
			 }

		 }).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {//��ӷ��ذ�ť

			 @Override

			 public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�


			 }

		 }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���

	}
}
