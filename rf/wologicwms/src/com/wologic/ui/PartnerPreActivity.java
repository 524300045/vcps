package com.wologic.ui;

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

public class PartnerPreActivity extends Activity {

	private TextView tbBack;

	private EditText etbarcode;

	private EditText etStore;


	private TextView tvmsg,tvProcess,tvStoreName,tvGoodsName,tvModel,tvWeight;

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
		
		tvProcess = (TextView) findViewById(R.id.tvProcess);
		tvStoreName = (TextView) findViewById(R.id.tvStoreName);
		tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);
		tvModel = (TextView) findViewById(R.id.tvModel);
		tvWeight = (TextView) findViewById(R.id.tvWeight);
		
		tvmsg = (TextView) findViewById(R.id.tvmsg);
		etbarcode = (EditText) findViewById(R.id.etbarcode);
		etStore = (EditText) findViewById(R.id.etStore);
		
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
							Toaster.toaster("«Î…®√Ë∞¸π¸∫≈!");
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
							+ "/packageDetail/getPackageDetailByCode";

					PackageDetailRequest packageDetailRequest=new PackageDetailRequest();
					packageDetailRequest.setPackageCode(packageCode);
					String json=JSON.toJSONString(packageDetailRequest);
					String resultSearch = com.wologic.util.SimpleClient.httpPost(searchUrl, json);
					
					JSONObject jsonSearch = new JSONObject(resultSearch);
					if(jsonSearch.optString("code").toString().equals("200"))
					{
						PackageAllDetail packageDetail=JSON.parseObject(jsonSearch.optString("result"),PackageAllDetail.class);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = packageDetail;
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
					msg.obj = "Õ¯¬Á“Ï≥£,«ÎºÏ≤Èµ•∫≈ «∑Ò¥Ê‘⁄";
					handler.sendMessage(msg);
				}
			}
		});
		mThread.start();
	}

	private void sumbit() {

		String packageCode = etbarcode.getText().toString().trim();
		if (packageCode.equals("")) {
			etbarcode.selectAll();
			Toaster.toaster("«Î…®√Ë∞¸π¸∫≈!");
			return;
		}
		String storeCode = etStore.getText().toString().trim();

		if (storeCode.equals("")) {
			etStore.selectAll();
			Toaster.toaster("«Î…®√Ë√≈µÍ∫≈!");
			return;
		}

		// ªÒ»°√≈µÍ–≈œ¢

	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				PackageAllDetail detail=(PackageAllDetail)msg.obj;
				tvProcess.setText(detail.getStoreProcess());
				tvStoreName.setText(detail.getStoredName());
				tvGoodsName.setText(detail.getGoodsName());
				String guige="";
				if(detail.getModelNum()!=null)
				{
					guige=detail.getModelNum().toString()+detail.getGoodsUnit()+"/"+detail.getPhysicsUnit();
				}
				tvModel.setText(guige);
				tvWeight.setText(detail.getWeight().toString());
				etStore.requestFocus();
				break;
			case 2:
				etbarcode.selectAll();
				Toaster.toaster(msg.obj.toString());
				break;
			case 3:
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
