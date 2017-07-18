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
import com.wologic.application.MyApplication;
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
	
		tvProcess = (TextView) findViewById(R.id.tvProcess);
	
		tvmsg = (TextView) findViewById(R.id.tvmsg);
		etbarcode = (EditText) findViewById(R.id.etbarcode);
		etStore = (EditText) findViewById(R.id.etStore);
		btnSure = (Button) findViewById(R.id.btnSure);
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				sumbit();
			}
		});

		tvProcess.setText("");
		
		
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
							Toaster.toaster("请扫描包裹号!");
							return true;
						}
						tvProcess.setText("");
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
							Toaster.toaster("请扫描门店号!");
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
					else if(jsonSearch.optString("code").toString().equals("302"))
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
					msg.obj = "网络异常,请检查单号是否存在";
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
			Toaster.toaster("请扫描包裹号!");
			return;
		}
		final String storeCode = etStore.getText().toString().trim();

		if (storeCode.equals("")) {
			etStore.selectAll();
			Toaster.toaster("请扫描门店号!");
			return;
		}

		// 获取门店信息
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
							msg.obj = "包裹与当前门店不一致";
							handler.sendMessage(msg);
						}
						else
						{
							   searchUrl = Constant.url
										+ "/packageDetail/picknew";

								PackageDetailRequest detailRequest=new PackageDetailRequest();
								detailRequest.setBoxCode(packageCode);
								
								detailRequest.setCreateUser(MyApplication.getAppContext().getUsername());
								String json2=JSON.toJSONString(detailRequest);
								String resultSearch2 = com.wologic.util.SimpleClient.httpPost(searchUrl, json2);
								JSONObject jsonSearch2 = new JSONObject(resultSearch2);
								if(jsonSearch2.optString("code").toString().equals("200"))
								{
									Message msg = new Message();
									msg.what = 4;
									msg.obj = "分拣完成";
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
					else	if(jsonSearch.optString("code").toString().equals("302"))
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
				List<PackageAllDetail> packageDetailList=(List<PackageAllDetail>)msg.obj;
				int totalNum=0;
				int finishNum=0;
				
				if(packageDetailList!=null)
				{
					totalNum=packageDetailList.size();
					for(PackageAllDetail item:packageDetailList)
					{
						if(item.getStatus().equals(10))
						{
							finishNum+=1;
						}
					}
				}
				tvProcess.setText(finishNum+"/"+totalNum);
				etStore.requestFocus();
				etStore.selectAll();
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
