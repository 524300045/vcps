package com.wologic.ui;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
import com.wologic.domainnew.PackageAllDetail;
import com.wologic.domainnew.PreprocessInfo;
import com.wologic.request.BoxInfoRequest;
import com.wologic.request.OutBoundRequest;
import com.wologic.request.PackageDetailRequest;
import com.wologic.request.PreprocessInfoRequest;
import com.wologic.util.Constant;
import com.wologic.util.Toaster;

public class PartnerOrderActivity extends Activity {

	private TextView tbBack;

	private EditText etBoxCode;

	private Button btnSure;

	private TextView tvmsg, tvProcess, tvStoreName, tvGoodsName, tvModel,
			tvWeight, tvTotalProcess;

	private String packagecode;

	private Long packageId;

	private String storeCode;
	
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partnerorder);

		Intent intent = getIntent();
		if (intent != null) {
			packagecode = intent.getStringExtra("packagecode");
			packageId = intent.getLongExtra("packageId", 0);
			storeCode = intent.getStringExtra("storeCode");
		}

		tbBack = (TextView) findViewById(R.id.tvback);
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		mediaPlayer = MediaPlayer.create(
				PartnerOrderActivity.this, R.raw.error);
		
		
		tvTotalProcess = (TextView) findViewById(R.id.tvTotalProcess);
		tvProcess = (TextView) findViewById(R.id.tvProcess);
		tvStoreName = (TextView) findViewById(R.id.tvStoreName);
		tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);
		tvModel = (TextView) findViewById(R.id.tvModel);
		tvWeight = (TextView) findViewById(R.id.tvWeight);

		tvmsg = (TextView) findViewById(R.id.tvmsg);
		etBoxCode = (EditText) findViewById(R.id.etBoxCode);

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
		etBoxCode.requestFocus();
		getPackageDetail(packagecode);
		loadProcess(storeCode);
	}
	
	private void loadProcess(final String scode)
	{
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

				  String	searchUrl = Constant.url
							+ "/outBoundDetail/getFinishInfo";
					
					OutBoundRequest outBoundRequest=new OutBoundRequest();
					outBoundRequest.setStoredCode(scode);
					String json2=JSON.toJSONString(outBoundRequest);
					String resultSearch2 = com.wologic.util.SimpleClient.httpPost(searchUrl, json2);
					
					JSONObject jsonSearch2 = new JSONObject(resultSearch2);
					if(jsonSearch2.optString("code").toString().equals("200"))
					{
						Message msg5 = new Message();
						msg5.what =5;
						msg5.obj = jsonSearch2.optString("result");
						handler.sendMessage(msg5);
					}

				} catch (Exception e) {
					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "网络异常,请检查网络连接";
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
						tvmsg.setText("");
						
						String code = etBoxCode.getText().toString().trim();
						if (code.equals("")) {
							etBoxCode.selectAll();
							Toaster.toaster("请扫描箱号!");
							mediaPlayer.setVolume(1.0f, 1.0f);
							mediaPlayer.start();
							tvmsg.setVisibility(View.VISIBLE);
							tvmsg.setText("请扫描箱号!");
							return true;
						}
						getPreProcessDetail(code);
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
					msg.obj = "网络异常,请检查网络连接";
					handler.sendMessage(msg);
				}
			}
		});
		mThread.start();
	}

	private void getPreProcessDetail(final String boxCode) {
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
								searchUrl = Constant.url
										+ "/packageDetail/partnerOrder";
								
								PackageDetailRequest packageDetailRequest = new PackageDetailRequest();
								packageDetailRequest.setPackageCode(packagecode);
								packageDetailRequest.setId(packageId);
								packageDetailRequest.setUpdateUser(MyApplication.getAppContext().getUsername());
								packageDetailRequest.setBoxCode(boxCode);
								

								String json2 = JSON
										.toJSONString(packageDetailRequest);

								String resultSearch2 = com.wologic.util.SimpleClient
										.httpPost(searchUrl, json2);
								JSONObject jsonSearch2 = new JSONObject(
										resultSearch2);
								if (jsonSearch2.optString("code").toString()
										.equals("200")) {

									Message msg = new Message();
									msg.what = 3;
									msg.obj = "扫描成功";
									handler.sendMessage(msg);
								} else {
									Message msg = new Message();
									msg.what = 2;
									msg.obj = jsonSearch.optString("message");
									handler.sendMessage(msg);
								}

							}

						}

					} else {

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
				PackageAllDetail detail = (PackageAllDetail) msg.obj;
				tvTotalProcess.setText(detail.getTotalProcess());
				tvProcess.setText(detail.getStoreProcess());
				tvStoreName.setText(detail.getStoredName());
				tvGoodsName.setText(detail.getGoodsName());
				String guige = "";
				if (detail.getModelNum() != null) {
					guige = detail.getModelNum().toString()
							+ detail.getGoodsUnit() + "/"
							+ detail.getPhysicsUnit();
				}
				tvModel.setText(guige);
				tvWeight.setText(detail.getWeight().toString());

				break;
			case 2:
				
				mediaPlayer.setVolume(1.0f, 1.0f);
				mediaPlayer.start();
				tvmsg.setVisibility(View.VISIBLE);
				tvmsg.setText(msg.obj.toString());
				Toaster.toaster(msg.obj.toString());
				etBoxCode.selectAll();
				etBoxCode.requestFocus();
				break;
			case 3:
				// 提交成功
				Toaster.toaster(msg.obj.toString());
				finish();
				break;
			case 5:
				tvProcess.setText(msg.obj.toString());
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
	
	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	};

}
