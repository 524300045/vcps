package com.wologic.ui;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.application.MyApplication;
import com.wologic.domainnew.BoxInfo;
import com.wologic.domainnew.PackTaskDetail;
import com.wologic.domainnew.PackageAllDetail;
import com.wologic.domainnew.PreprocessInfo;
import com.wologic.request.BoxInfoRequest;
import com.wologic.request.PackTaskDetailRequest;
import com.wologic.request.PackageDetailRequest;
import com.wologic.request.PreprocessInfoRequest;
import com.wologic.util.Constant;
import com.wologic.util.Toaster;

public class PartnerPreActivity extends Activity {

	private TextView tbBack;

	private EditText etbarcode;

	private EditText etBoxCode;

	private TextView tvmsg, tvProcess, tvStoreName, tvGoodsName, tvModel,
			tvWeight;

	private String storeCode, storeName,ousStockCode,packTaskCode;

	private Long taskDetailId;
	
	private String lastPackageCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partnerpre);
		tbBack = (TextView) findViewById(R.id.tvback);
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		Intent intent = getIntent();
		if (intent != null) {
			storeCode = intent.getStringExtra("storeCode");//门店编号
			taskDetailId = Long.valueOf(intent.getStringExtra("id"));//包装任务明细ID
			storeName = intent.getStringExtra("storeName");
			ousStockCode=intent.getStringExtra("ousStockCode");
			packTaskCode=intent.getStringExtra("packTaskCode");
			lastPackageCode=intent.getStringExtra("lastPackageCode");
		}

		tvProcess = (TextView) findViewById(R.id.tvProcess);
		tvStoreName = (TextView) findViewById(R.id.tvStoreName);
		tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);
		tvModel = (TextView) findViewById(R.id.tvModel);
		tvWeight = (TextView) findViewById(R.id.tvWeight);

		tvmsg = (TextView) findViewById(R.id.tvmsg);
		etbarcode = (EditText) findViewById(R.id.etbarcode);
		etBoxCode = (EditText) findViewById(R.id.etBoxCode);

		tvStoreName.setText(storeName);
		
		tvProcess.setText("");
		
		tvGoodsName.setText("");
		tvModel.setText("");
		tvWeight.setText("");

		initEvent();
		etBoxCode.requestFocus();
		getProcessInfo(lastPackageCode);
	}
	
	
	private void getProcessInfo(final String lastPackageCode)
	{
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

				
							
							//查询预包装信息
					    String searchUrl = Constant.url
									+ "/preprocessInfo/getPreprocessInfoByCode";
							
							PreprocessInfoRequest preprocessInfoRequest=new PreprocessInfoRequest();
							preprocessInfoRequest.setPreprocessCode(lastPackageCode);
							String json3=JSON.toJSONString(preprocessInfoRequest);
							String resultSearch3 = com.wologic.util.SimpleClient.httpPost(searchUrl, json3);
							JSONObject jsonSearch3 = new JSONObject(resultSearch3);
							if(jsonSearch3.optString("code").toString().equals("200"))
							{
								if(null==jsonSearch3.opt("result")||"null".equals(jsonSearch3.opt("result").toString()))
								{
									Message msg = new Message();
									msg.what =2;
									msg.obj ="查询不到包裹信息" +
											"";
									handler.sendMessage(msg);
								}
								else
								{
									
									PreprocessInfo preprocessInfo=JSON.parseObject(jsonSearch3.optString("result"),PreprocessInfo.class);
									Message msg = new Message();
									msg.what =5;
									msg.obj =preprocessInfo;

									handler.sendMessage(msg);
								}
							}
						
					
					
				} catch (Exception e) {
					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 3;
					msg.obj =e.getMessage();
					handler.sendMessage(msg);
					
				}
			}
		});
		mThread.start();
		
	}

	private void initEvent() {

		etBoxCode.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:
						String packageCode = etbarcode.getText().toString()
								.trim();
						if (packageCode.equals("")) {
							etbarcode.selectAll();
							Toaster.toaster("请扫描箱号!");
							return true;
						}
						etbarcode.requestFocus();
						break;
					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;
				}
				return false;
			}
		});

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
						sumbit();
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

	private void getPackageDetail(final String packageCode) {
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/packageDetail/getPackageDetailByCode";

					PackageDetailRequest packageDetailRequest = new PackageDetailRequest();
					packageDetailRequest.setPackageCode(packageCode);
					String json = JSON.toJSONString(packageDetailRequest);
					String resultSearch = com.wologic.util.SimpleClient
							.httpPost(searchUrl, json);

					JSONObject jsonSearch = new JSONObject(resultSearch);
					if (jsonSearch.optString("code").toString().equals("200")) {
						PackageAllDetail packageDetail = JSON.parseObject(
								jsonSearch.optString("result"),
								PackageAllDetail.class);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = packageDetail;
						handler.sendMessage(msg);
					} else {
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

		final String boxCode = etBoxCode.getText().toString().trim();

		if (storeCode.equals("")) {
			etBoxCode.selectAll();
			Toaster.toaster("请扫描门箱号!");
			return;
		}

		final String packageCode = etbarcode.getText().toString().trim();
		if (packageCode.equals("")) {
			etbarcode.selectAll();
			Toaster.toaster("请扫描包裹号!");
			return;
		}

		// 判断箱号是否是属于当前门店的

		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url + "/boxInfo/getBoxInfoCode";

					BoxInfoRequest boxInfoRequest = new BoxInfoRequest();
					boxInfoRequest.setBoxCode(boxCode);
					String json = JSON.toJSONString(boxInfoRequest);
					String resultSearch = com.wologic.util.SimpleClient
							.httpPost(searchUrl, json);

					JSONObject jsonSearch = new JSONObject(resultSearch);
					if (jsonSearch.optString("code").toString().equals("200")) {
						if (null == jsonSearch.opt("result")
								|| "null" == jsonSearch.opt("result")
										.toString()) {
							Message msg = new Message();
							msg.what = 2;
							msg.obj = "查询不到箱号信息";
							handler.sendMessage(msg);
						} else {
							// 判断箱号是否已经使用

							BoxInfo boxInfo = JSON.parseObject(
									jsonSearch.optString("result"),
									BoxInfo.class);
							if (!storeCode.equals(boxInfo.getStoredCode())) {
								Message msg = new Message();
								msg.what = 2;
								msg.obj = "箱号不属于当前门店";
								handler.sendMessage(msg);
							} else {

								// 查询包裹信息
								searchUrl = Constant.url
										+ "/preprocessInfo/getPreprocessInfoByCode";
								PreprocessInfoRequest preprocessInfoRequest = new PreprocessInfoRequest();
								preprocessInfoRequest
										.setPreprocessCode(packageCode);
								String json2 = JSON
										.toJSONString(preprocessInfoRequest);
								String resultSearch2 = com.wologic.util.SimpleClient
										.httpPost(searchUrl, json2);

								
								JSONObject jsonSearch2 = new JSONObject(
										resultSearch2);
								if (jsonSearch2.optString("code").toString()
										.equals("200")) {
									if (null == jsonSearch2.opt("result")
											|| "null" == jsonSearch2.opt(
													"result").toString()) {
										Message msg = new Message();
										msg.what = 3;
										msg.obj = "查询不到包裹信息";
										handler.sendMessage(msg);
									} else {
										PreprocessInfo preprocessInfo = JSON
												.parseObject(jsonSearch2
														.optString("result"),
														PreprocessInfo.class);
										if (preprocessInfo.getStatus() == 1) {
											Message msg = new Message();
											msg.what = 3;
											msg.obj = "当前包裹已经被占用";
											handler.sendMessage(msg);
										} else {

											// 提交

											searchUrl = Constant.url
													+ "/packageDetail/partnerPre";
											PackageDetailRequest packageDetailRequest = new PackageDetailRequest();

											packageDetailRequest.setOutboundTaskCode(ousStockCode);
											packageDetailRequest.setPackTaskCode(packTaskCode);
											packageDetailRequest.setPackTaskDetailId(taskDetailId);
											packageDetailRequest.setSkuCode(preprocessInfo.getSkuCode());
											packageDetailRequest.setWeight(preprocessInfo.getPackWeight());
											packageDetailRequest.setPackageCode(preprocessInfo.getPreprocessCode());
											packageDetailRequest.setBoxCode(boxCode);
											packageDetailRequest.setProcessUser(MyApplication.getAppContext().getUsername());
											packageDetailRequest.setCreateUser(MyApplication.getAppContext().getUsername());
											packageDetailRequest.setUpdateUser(MyApplication.getAppContext().getUsername());
											
											
											String json3 = JSON
													.toJSONString(packageDetailRequest);
											String resultSearch3 = com.wologic.util.SimpleClient
													.httpPost(searchUrl, json3);

											JSONObject jsonSearch3 = new JSONObject(
													resultSearch3);
											if (jsonSearch3.optString("code").toString()
													.equals("200")) 
											{
												
												Message msg = new Message();
												msg.what = 4;
												msg.obj = jsonSearch3.optString("message");
												handler.sendMessage(msg);
											}
											else if(jsonSearch3.optString("code").toString()
													.equals("301"))
											{
												Message msg = new Message();
												msg.what = 3;
												msg.obj = jsonSearch3.optString("message");
												handler.sendMessage(msg);
											}
											else
											{
												Message msg = new Message();
												msg.what = 3;
												msg.obj = jsonSearch3.optString("message");
												handler.sendMessage(msg);
											}
											
										}

									}
								} else {
									Message msg = new Message();
									msg.what = 2;
									msg.obj = jsonSearch2.optString("message");
									handler.sendMessage(msg);
								}

							}

						}

					} else {

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

		// 判断包裹号是否被使用

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:

				etBoxCode.requestFocus();
				break;
			case 2:
				etBoxCode.selectAll();
				etBoxCode.requestFocus();
				Toaster.toaster(msg.obj.toString());
				break;
			case 3:
				etbarcode.selectAll();
				etbarcode.requestFocus();
				break;
			case 4:
				etbarcode.setText("");
				etbarcode.selectAll();
				etbarcode.requestFocus();
				Toaster.toaster(msg.obj.toString());
				break;
			case 5:
				PreprocessInfo preprocessInfo=(PreprocessInfo)msg.obj;
				tvProcess.setText("");
				tvGoodsName.setText(preprocessInfo.getGoodsName());
				tvModel.setText(preprocessInfo.getModelNum().toString());
				tvWeight.setText(preprocessInfo.getPackWeight().toString());
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
