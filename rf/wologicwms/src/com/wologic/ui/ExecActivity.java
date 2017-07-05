package com.wologic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wologic.R;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ItemDataDao;
import com.wologic.domain.BarCode;
import com.wologic.domain.ItemData;
import com.wologic.domain.WorkItem;
import com.wologic.util.Common;
import com.wologic.util.Toaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ExecActivity extends Activity {


	
	private ListView lv;
	private SimpleAdapter adp;// 定义适配器
	private List<Map<String, Object>> mapList;// 定义数据源


	private TextView tbBack;

	private int totalCount;// 总的记录数
	private listViewAdapter adapter;

	// 查看更多
	private TextView moreTextView;
	// 正在加载进度条
	private LinearLayout loadProgressBar;

	// 分页加载的数据的数量
	private int pageSize = 10;

	private int currentPage = 0;// 分页索引

	private List<BarCode> barCodeList;

	private int count;
	private boolean isaddfoot = false;

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
		bindlist();
	}

	/*
	 * 扫描条码
	 */


	private void bindlist() {
		currentPage = 1;
		// 获取第一页数据
		barCodeList =new ArrayList<BarCode>();
		
		BarCode barOne=new BarCode();
		BarCode barTwo=new BarCode();
		BarCode barThree=new BarCode();
		
		barCodeList.add(barOne);
		barCodeList.add(barTwo);
		barCodeList.add(barThree);
		// 在ListView中添加"加载更多"

		if (barCodeList == null || barCodeList.size() == 0
				|| barCodeList.size() < pageSize) {
			if (isaddfoot) {
				moreTextView.setVisibility(View.GONE);
				// 删除
				View view = LayoutInflater.from(this).inflate(
						R.layout.list_page_load, null);
				lv.removeFooterView(view);
				isaddfoot = false;
			}
		} else {
			if (!isaddfoot) {
				isaddfoot = true;
				addPageMore();
			}

		}

		adapter = new listViewAdapter();
		lv.setAdapter(adapter);

	}

	private class listViewAdapter extends BaseAdapter {
		int count = barCodeList.size();

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = LayoutInflater.from(ExecActivity.this).inflate(
					R.layout.listmendiandetail, null);
			TextView tvmendiancode = (TextView) view.findViewById(R.id.tvmendiancode);
			TextView tvmendian = (TextView) view.findViewById(R.id.tvmendian);
			TextView tvjindu = (TextView) view.findViewById(R.id.tvjindu);
			
			tvmendiancode.setText("lc002");
			tvmendian.setText("朝阳大悦城");
			tvjindu.setText("10/100");
			return view;
		}

	}

	private void addPageMore() {
		View view = LayoutInflater.from(this).inflate(R.layout.list_page_load,
				null);
		moreTextView = (TextView) view.findViewById(R.id.more_id);
		loadProgressBar = (LinearLayout) view.findViewById(R.id.load_id);
		moreTextView.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 隐藏"加载更多"
				moreTextView.setVisibility(View.GONE);
				// 显示进度条
				loadProgressBar.setVisibility(View.VISIBLE);

				currentPage += 1;
				// 加载模拟数据：下一页数据， 在正常情况下，上面的休眠是不需要，直接使用下面这句代码加载相关数据
				boolean nextLoad = chageListView(currentPage, pageSize);
				if (nextLoad) {
					Message mes = handler.obtainMessage(1);
					handler.sendMessage(mes);
				} else {
					Message mes = handler.obtainMessage(2);
					handler.sendMessage(mes);
				}
			}
		});
		lv.addFooterView(view);
	}

	/**
	 * 加载下一页的数据
	 * 
	 * @param pageStart
	 * @param pageSize
	 */
	private boolean chageListView(int pageStart, int pageSize) {
		
		List<BarCode> tempbarCodeList=new ArrayList<BarCode>();
		BarCode barOne=new BarCode();
		BarCode barTwo=new BarCode();
		BarCode barThree=new BarCode();
		
		tempbarCodeList.add(barOne);
		tempbarCodeList.add(barTwo);
		tempbarCodeList.add(barThree);
		
		
		count=0;
		if (tempbarCodeList == null)
		{
			return false;
		}
		if( tempbarCodeList.size() == 0)
		{
			return false;
		}
		count = tempbarCodeList.size();
		barCodeList.addAll(tempbarCodeList);
		if (tempbarCodeList.size() < pageSize)
		{
			return false;
		}
		else
		{
			return true;
		}
	
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 改变适配器的数目
				adapter.count += pageSize;
				// 通知适配器，发现改变操作
				adapter.notifyDataSetChanged();

				// 再次显示"加载更多"
				moreTextView.setVisibility(View.VISIBLE);
				// 再次隐藏“进度条”
				loadProgressBar.setVisibility(View.GONE);
				break;

			case 2:
				// 改变适配器的数目
				adapter.count += count;
				// 通知适配器，发现改变操作
				adapter.notifyDataSetChanged();

				// 再次显示"加载更多"
				moreTextView.setVisibility(View.GONE);
				// 再次隐藏“进度条”
				loadProgressBar.setVisibility(View.GONE);
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

}
