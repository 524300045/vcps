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
	private SimpleAdapter adp;// 定义适配器
	private List<Map<String, Object>> mapList;// 定义数据源

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
					msg.obj = "网络异常,请检查网络连接";
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
		 * 参数1是上下文对象 参数2是数据源 参数3是布局文件 参数4是键名 参数5是绑定布局文件中视图ID
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
	 * 扫描条码
	 */

	/**
	 * 响应ListView中item的点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		/*
		 * Toast.makeText(this, "listview的item被点击了！，点击的位置是-->" + position,
		 * Toast.LENGTH_SHORT).show();
		 */
	}

	/**
	 * 接口方法，响应ListView按钮点击事件
	 */
	@Override
	public void click(View v) {
		/*Toast.makeText(
				ExecActivity.this,
				"listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
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
		
		String msg = storeName + "  还差" + diff + "两个商品未拣完成,请确认是否强制完成?";
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
				Toast.makeText(ExecActivity.this, "确定", Toast.LENGTH_SHORT)
						.show();
				//
				
				dialog.dismiss();
				updateFinish(taskcode);

			}
		});
		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ExecActivity.this, "取消", Toast.LENGTH_SHORT)
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
						msg.obj = "强制完成成功";
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
					msg.obj = "网络异常"+e.getMessage();
					handler.sendMessage(msg);
				}
			}
		});
		mThread.start();
	}

}
