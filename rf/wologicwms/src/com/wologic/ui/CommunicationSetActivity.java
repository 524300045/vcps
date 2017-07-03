package com.wologic.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wologic.R;
import com.wologic.dao.ParameterDao;
import com.wologic.domain.Parameter;
import com.wologic.util.Constant;
import com.wologic.util.Toaster;

public class CommunicationSetActivity extends Activity {

	private TextView tbBack, tvaccount, tvport, tvip,tvpdaname;

	private Button btntest, btnsure;

	private ParameterDao parameterDao;
	
	//private PDAInfoDao pdainfoDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_communicationset);
		tbBack = (TextView) findViewById(R.id.tvback);
		tvaccount = (TextView) findViewById(R.id.tvaccount);
		tvport = (TextView) findViewById(R.id.tvport);
		tvpdaname = (TextView) findViewById(R.id.tvpdaname);
		tvip = (TextView) findViewById(R.id.tvip);
		btntest = (Button) findViewById(R.id.btntest);
		btnsure = (Button) findViewById(R.id.btnsure);
		parameterDao = new ParameterDao();
		//pdainfoDao = new PDAInfoDao();
		tvaccount.setText("");
		tvport.setText("");
		tvip.setText("");
		Parameter parameter = parameterDao.getParameterById(1);
		//PdaInfo pda = pdainfoDao.getPdaInfo();
		if (parameter != null) {
			tvaccount.setText(parameter.getParaValue3());
			tvport.setText(parameter.getParaValue2());
			tvip.setText(parameter.getParaValue1());
		}
		/*if(pda!=null){
			tvpdaname.setText(pda.getPdaName());
		}*/
		initEvent();
	}

	private void initEvent() {
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnsure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//PdaInfo pda = new PdaInfo();
				Parameter p = new Parameter();
				p.setParameterId(1);
				p.setParaValue1(tvip.getText().toString());
				p.setParaValue2(tvport.getText().toString());
				p.setParaValue3(tvaccount.getText().toString());
				/*pda.setPdaName(tvpdaname.getText().toString());
				pda.setPDAType("MaiBo");*/
				parameterDao.updateParameter(p);
				//pdainfoDao.setPdaInfo(pda);
				//将设备的信息保存到本地
				/*if(FileOperation.setDeviceName(tvpdaname.getText().toString())){
					Toast.makeText(CommunicationSetActivity.this, "保存设置成功", 1000)
					.show();
					Common.deviceid = tvpdaname.getText().toString();
				}
				else{
					Common.deviceid = tvpdaname.getText().toString();
					Toast.makeText(CommunicationSetActivity.this, "设备名称保存失败!", 1000)
					.show();
				}*/
				Constant.url = "http://"+tvip.getText().toString()+":778/wologic";
			}
		});

		btntest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String ip = tvip.getText().toString();
				final String port = tvport.getText().toString();
				final String account = tvaccount.getText().toString();

				Thread mThread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String url = Constant.url + "/services/0/test";
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("edit[ip]", ip);
							map.put("edit[port]", port);
							map.put("edit[db]", account);

							HttpClient client = com.wologic.util.SimpleClient
									.getHttpClient();
							String result = com.wologic.util.SimpleClient
									.doGet(url, map);
							JSONObject json = new JSONObject(result);
							Message msg = new Message();
							msg.what = 2;
							msg.obj = json.optString("message");
							handler.sendMessage(msg);

						} catch (Exception e) {

							Message msg = new Message();
							msg.what = 2;
							msg.obj = "连接失败!";
							handler.sendMessage(msg);
						}
					}
				});
				mThread.start();
			};
		});

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
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
