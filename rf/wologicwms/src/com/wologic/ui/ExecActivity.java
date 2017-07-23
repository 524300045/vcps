package com.wologic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.control.CustomDialog;
import com.wologic.control.CustomSureDialog;
import com.wologic.domainnew.OutBound;
import com.wologic.domainnew.PackageAllDetail;
import com.wologic.request.OutBoundRequest;
import com.wologic.request.PackageDetailRequest;
import com.wologic.ui.ContentAdapter.Callback;
import com.wologic.util.Common;
import com.wologic.util.Constant;
import com.wologic.util.Toaster;

public class ExecActivity extends Activity implements OnItemClickListener,
		Callback {

	private ListView lv;
	private SimpleAdapter adp;// ����������
	private List<Map<String, Object>> mapList;// ��������Դ

	private TextView tbBack;

	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exec);
		lv = (ListView) findViewById(R.id.lvgoods);
		tbBack = (TextView) findViewById(R.id.tvback);
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mediaPlayer = MediaPlayer.create(
				ExecActivity.this, R.raw.error);
		
		getList();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	};

	private void getList() {
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/outBound/getOutBoundList";

					OutBoundRequest outBoundRequest = new OutBoundRequest();
					outBoundRequest.setYn(1);
					String json = JSON.toJSONString(outBoundRequest);
					String resultSearch = com.wologic.util.SimpleClient
							.httpPost(searchUrl, json);

					JSONObject jsonSearch = new JSONObject(resultSearch);
					if (jsonSearch.optString("code").toString().equals("200")) {
						List<OutBound> outBoundList = JSON.parseArray(
								jsonSearch.optString("result"), OutBound.class);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = outBoundList;
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
					msg.obj = "�����쳣,������������";
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
				List<OutBound> list = (List<OutBound>) msg.obj;
				bindList(list);
				break;
			case 2:
				
				mediaPlayer.setVolume(1.0f, 1.0f);
				mediaPlayer.start();
				Toaster.toaster(msg.obj.toString());
				break;
			default:
				break;
			}
		}
	};

	private void bindList(List<OutBound> list) {

		/*
		 * ����1�������Ķ��� ����2������Դ ����3�ǲ����ļ� ����4�Ǽ��� ����5�ǰ󶨲����ļ�����ͼID
		 */
		mapList = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("taskcode", list.get(i).getOutboundTaskCode());
			map.put("storeCode", list.get(i).getStoredCode());
			map.put("storeName", list.get(i).getStoredName());
			map.put("finishnum", list.get(i).getFinishNum());
			map.put("totalnum", list.get(i).getTotalNum());
			mapList.add(map);
		}

		lv.setAdapter(new ContentAdapter(this, mapList, this));
		lv.setOnItemClickListener(this);

	}

	/*
	 * ɨ������
	 */

	/**
	 * ��ӦListView��item�ĵ���¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		/*
		 * Toast.makeText(this, "listview��item������ˣ��������λ����-->" + position,
		 * Toast.LENGTH_SHORT).show();
		 */
	}

	/**
	 * �ӿڷ�������ӦListView��ť����¼�
	 */
	@Override
	public void click(View v) {
		/*Toast.makeText(
				ExecActivity.this,
				"listview���ڲ��İ�ť������ˣ���λ����-->" + (Integer) v.getTag() + ",������-->"
						+ mapList.get((Integer) v.getTag()).get("storeCode")
						+ "--", Toast.LENGTH_SHORT).show();*/
		Integer finishNum = (Integer) mapList.get((Integer) v.getTag()).get(
				"finishnum");
		Integer totalNum = (Integer) mapList.get((Integer) v.getTag()).get(
				"totalnum");
		String storeName = mapList.get((Integer) v.getTag()).get("storeName")
				.toString();
		Integer diff = totalNum - finishNum;
		String taskcode = mapList.get((Integer) v.getTag()).get("taskcode")
				.toString();
		
		String msg = storeName + "  ����" + diff + "������Ʒδ�����,��ȷ���Ƿ�ǿ�����?";
		dialog(msg,taskcode);
	}

	private void dialog(final String title,final String taskcode) {
		final CustomSureDialog dialog = new CustomSureDialog(ExecActivity.this);
		TextView textview = (TextView) dialog.findViewById(R.id.title);
		textview.setVisibility(View.VISIBLE);
		textview.setText(title);
		dialog.setTitle(textview);

		dialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ExecActivity.this, "ȷ��", Toast.LENGTH_SHORT)
						.show();
				//
				
				dialog.dismiss();
				updateFinish(taskcode);

			}
		});
		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ExecActivity.this, "ȡ��", Toast.LENGTH_SHORT)
						.show();
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	private void updateFinish(final String taskCode)
	{
		
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/outBound/updateFinish";

					OutBoundRequest outBoundRequest = new OutBoundRequest();
					outBoundRequest.setOutboundTaskCode(taskCode);
					String json = JSON.toJSONString(outBoundRequest);
					String resultSearch = com.wologic.util.SimpleClient
							.httpPost(searchUrl, json);

					JSONObject jsonSearch = new JSONObject(resultSearch);
					if (jsonSearch.optString("code").toString().equals("200")) {
						List<OutBound> outBoundList = JSON.parseArray(
								jsonSearch.optString("result"), OutBound.class);
						Message msg = new Message();
						msg.what = 2;
						msg.obj = "ǿ����ɳɹ�";
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
					msg.obj = "�����쳣"+e.getMessage();
					handler.sendMessage(msg);
				}
			}
		});
		mThread.start();
	}

}
