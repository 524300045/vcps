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
	private SimpleAdapter adp;// ����������
	private List<Map<String, Object>> mapList;// ��������Դ


	private TextView tbBack;

	private int totalCount;// �ܵļ�¼��
	private listViewAdapter adapter;

	// �鿴����
	private TextView moreTextView;
	// ���ڼ��ؽ�����
	private LinearLayout loadProgressBar;

	// ��ҳ���ص����ݵ�����
	private int pageSize = 10;

	private int currentPage = 0;// ��ҳ����

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
	 * ɨ������
	 */


	private void bindlist() {
		currentPage = 1;
		// ��ȡ��һҳ����
		barCodeList =new ArrayList<BarCode>();
		
		BarCode barOne=new BarCode();
		BarCode barTwo=new BarCode();
		BarCode barThree=new BarCode();
		
		barCodeList.add(barOne);
		barCodeList.add(barTwo);
		barCodeList.add(barThree);
		// ��ListView�����"���ظ���"

		if (barCodeList == null || barCodeList.size() == 0
				|| barCodeList.size() < pageSize) {
			if (isaddfoot) {
				moreTextView.setVisibility(View.GONE);
				// ɾ��
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
			tvmendian.setText("�������ó�");
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
				// ����"���ظ���"
				moreTextView.setVisibility(View.GONE);
				// ��ʾ������
				loadProgressBar.setVisibility(View.VISIBLE);

				currentPage += 1;
				// ����ģ�����ݣ���һҳ���ݣ� ����������£�����������ǲ���Ҫ��ֱ��ʹ����������������������
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
	 * ������һҳ������
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
				// �ı�����������Ŀ
				adapter.count += pageSize;
				// ֪ͨ�����������ָı����
				adapter.notifyDataSetChanged();

				// �ٴ���ʾ"���ظ���"
				moreTextView.setVisibility(View.VISIBLE);
				// �ٴ����ء���������
				loadProgressBar.setVisibility(View.GONE);
				break;

			case 2:
				// �ı�����������Ŀ
				adapter.count += count;
				// ֪ͨ�����������ָı����
				adapter.notifyDataSetChanged();

				// �ٴ���ʾ"���ظ���"
				moreTextView.setVisibility(View.GONE);
				// �ٴ����ء���������
				loadProgressBar.setVisibility(View.GONE);
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

}
