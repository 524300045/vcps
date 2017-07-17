package com.wologic.ui;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.domainnew.PackageAllDetail;
import com.wologic.request.PackageDetailRequest;
import com.wologic.util.Constant;
import com.wologic.util.Toaster;

public class PickerActivity extends Activity {

	private TextView tbBack;

	private EditText etbarcode;

	private EditText etStore;

	private Button btnSure;

	private String storeCode;
	
	private TextView tvmsg,tvProcess,tvStoreName,tvGoodsName,tvModel,tvWeight,tvTotalProcess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picker);
		tbBack = (TextView) findViewById(R.id.tvback);
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		tvTotalProcess = (TextView) findViewById(R.id.tvTotalProcess);
		tvProcess = (TextView) findViewById(R.id.tvProcess);
		tvStoreName = (TextView) findViewById(R.id.tvStoreName);
		tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);
		tvModel = (TextView) findViewById(R.id.tvModel);
		tvWeight = (TextView) findViewById(R.id.tvWeight);
		
		tvmsg = (TextView) findViewById(R.id.tvmsg);
		etbarcode = (EditText) findViewById(R.id.etbarcode);
		etStore = (EditText) findViewById(R.id.etStore);
		btnSure = (Button) findViewById(R.id.btnSure);
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});
		tvTotalProcess.setText("");
		tvProcess.setText("");
		tvStoreName.setText("");
		tvGoodsName.setText("");
		tvModel.setText("");
		tvWeight.setText("");
		
		initEvent();
		etbarcode.requestFocus();
		
	}

	private void initEvent() {
		etbarcode.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:
						String packageCode = etbarcode.getText().toString()
								.trim();
						if (packageCode.equals("")) {
							etbarcode.selectAll();
							Toaster.toaster("��ɨ�������!");
							return true;
						}
						getPackageDetail(packageCode);
						break;
					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;
				}
				return false;
			}
		});
		
		etStore.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:
						String storeCode = etStore.getText().toString()
								.trim();
						if (storeCode.equals("")) {
							etbarcode.selectAll();
							Toaster.toaster("��ɨ���ŵ��!");
							return true;
						}
						sumbit() ;
						break;
					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;
				}
				return false;
			}
		});

	}
	
	private void getPackageDetail(final String packageCode)
	{
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/packageDetail/getPackageDetailByBoxCode";

					PackageDetailRequest packageDetailRequest=new PackageDetailRequest();
					packageDetailRequest.setBoxCode(packageCode);
					String json=JSON.toJSONString(packageDetailRequest);
					String resultSearch = com.wologic.util.SimpleClient.httpPost(searchUrl, json);
					
					JSONObject jsonSearch = new JSONObject(resultSearch);
					if(jsonSearch.optString("code").toString().equals("200"))
					{
						List<PackageAllDetail> packageDetailList=JSON.parseArray(jsonSearch.optString("result"),PackageAllDetail.class);
						storeCode=packageDetailList.get(0).getStoredCode();
						Message msg = new Message();
						msg.what = 1;
						msg.obj = packageDetailList;
						handler.sendMessage(msg);
					}
					if(jsonSearch.optString("code").toString().equals("302"))
					{
						Message msg = new Message();
						msg.what = 2;
						msg.obj = jsonSearch.optString("message");
						handler.sendMessage(msg);
					}
					else
					{
						Message msg = new Message();
						msg.what = 2;
						msg.obj = jsonSearch.optString("message");
						handler.sendMessage(msg);
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

	private void sumbit() {

		final String packageCode = etbarcode.getText().toString().trim();
		if (packageCode.equals("")) {
			etbarcode.selectAll();
			Toaster.toaster("��ɨ�������!");
			return;
		}
		final String storeCode = etStore.getText().toString().trim();

		if (storeCode.equals("")) {
			etStore.selectAll();
			Toaster.toaster("��ɨ���ŵ��!");
			return;
		}

		// ��ȡ�ŵ���Ϣ
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/packageDetail/getPackageDetailByBoxCode";

					PackageDetailRequest packageDetailRequest=new PackageDetailRequest();
					packageDetailRequest.setBoxCode(packageCode);
					String json=JSON.toJSONString(packageDetailRequest);
					String resultSearch = com.wologic.util.SimpleClient.httpPost(searchUrl, json);
					
					JSONObject jsonSearch = new JSONObject(resultSearch);
					if(jsonSearch.optString("code").toString().equals("200"))
					{
						List<PackageAllDetail> packageDetailList=JSON.parseArray(jsonSearch.optString("result"),PackageAllDetail.class);
						if(!packageDetailList.get(0).getStoredCode().equals(storeCode))
						{
							Message msg = new Message();
							msg.what = 3;
							msg.obj = "�����뵱ǰ�ŵ겻һ��";
							handler.sendMessage(msg);
						}
						else
						{
							   searchUrl = Constant.url
										+ "/packageDetail/picknew";

								PackageDetailRequest detailRequest=new PackageDetailRequest();
								detailRequest.setBoxCode(packageCode);
								
								String json2=JSON.toJSONString(detailRequest);
								String resultSearch2 = com.wologic.util.SimpleClient.httpPost(searchUrl, json2);
								JSONObject jsonSearch2 = new JSONObject(resultSearch2);
								if(jsonSearch2.optString("code").toString().equals("200"))
								{
									Message msg = new Message();
									msg.what = 4;
									msg.obj = "�ּ����";
									handler.sendMessage(msg);
								}
								else if(jsonSearch2.optString("code").toString().equals("302"))
								{
									Message msg = new Message();
									msg.what = 2;
									msg.obj = jsonSearch.optString("message");
									handler.sendMessage(msg);
								}
								else
								{
									Message msg = new Message();
									msg.what = 3;
									msg.obj = jsonSearch.optString("message");
									handler.sendMessage(msg);
								}
								
						}
						
						
					}
					if(jsonSearch.optString("code").toString().equals("302"))
					{
						Message msg = new Message();
						msg.what = 2;
						msg.obj = jsonSearch.optString("message");
						handler.sendMessage(msg);
					}
					else
					{
						Message msg = new Message();
						msg.what = 2;
						msg.obj = jsonSearch.optString("message");
						handler.sendMessage(msg);
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
				List<PackageAllDetail> packageDetailList=(List<PackageAllDetail>)msg.obj;
				/*tvTotalProcess.setText(detail.getTotalProcess());
				tvProcess.setText(detail.getStoreProcess());
				tvStoreName.setText(detail.getStoredName());
				tvGoodsName.setText(detail.getGoodsName());
				String guige="";
				if(detail.getModelNum()!=null)
				{
					guige=detail.getModelNum().toString()+detail.getGoodsUnit()+"/"+detail.getPhysicsUnit();
				}
				tvModel.setText(guige);
				tvWeight.setText(detail.getWeight().toString());*/
				etStore.requestFocus();
				break;
			case 2:
				etbarcode.selectAll();
				etbarcode.requestFocus();
				Toaster.toaster(msg.obj.toString());
				break;
			case 3:
				etStore.selectAll();
				etStore.requestFocus();
				Toaster.toaster(msg.obj.toString());
				break;
			case 4:
				etStore.setText("");
				etbarcode.setText("");
				etbarcode.requestFocus();
				Toaster.toaster(msg.obj.toString());
				break;
			default:
				break;
			}
		}
	};

	

	@Override
	protected void onStart() {
		super.onStart();

	}

}
