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

public class GoodsDangAnActivity extends Activity {

	private GoodsDao goodsDao;
	private String itemNo;
	private Button btnSure;
	private ListView lv;
	private SimpleAdapter adp;// 定义适配器
	private List<Map<String, Object>> mapList;// 定义数据源

	private EditText etBarCode;

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
		setContentView(R.layout.activity_goodsdangan);
		goodsDao = new GoodsDao();
		btnSure = (Button) findViewById(R.id.btnSure);
		lv = (ListView) findViewById(R.id.lvgoods);
		etBarCode = (EditText) findViewById(R.id.etBarCode);
		tbBack = (TextView) findViewById(R.id.tvback);
		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// scanBarCode();
				bindlist();
			}
		});

		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		etBarCode.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:

						final String code = etBarCode.getText().toString()
								.trim();
						if (code.equals("")) {
							return true;
						}
						bindlist();
						etBarCode.selectAll();
					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;
				}
				return false;
			}
		});
	}

	/*
	 * 扫描条码
	 */
	private void scanBarCode() {
		String barCode = etBarCode.getText().toString();
		List<BarCode> itemList = goodsDao.GetAlllist(barCode);
		mapList = new ArrayList<Map<String, Object>>();
		if (itemList != null && itemList.size() > 0) {
			for (BarCode item : itemList) {
				// 获取商品信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("barcode", item.getPkgbarcode());// 条码
				map.put("name", item.getProductname());// 名称
				map.put("guige", item.getGuige());// 规格
				map.put("stock", item.getStock());// 库存
				map.put("pinpai", item.getPinpai());// 品牌
				map.put("unit", item.getUnit());// 单位
				map.put("chandi", item.getChandi());// 产地
				map.put("price", item.getPrice());// 价格
				mapList.add(map);
			}

		} else {
			// 没有查询到商品条码
			Toaster.toaster("查询不到商品信息");
		}

		adp = new SimpleAdapter(this, mapList, R.layout.listgoodsdangandetail,
				new String[] { "barcode", "name", "guige", "stock", "pinpai",
						"unit", "chandi", "price" }, new int[] {
						R.id.tvbarcode, R.id.tvname, R.id.tvguige,
						R.id.tvstock, R.id.tbpinpai, R.id.tvunit,
						R.id.tvchandi, R.id.tvprice });
		lv.setAdapter(adp);
	}

	private void bindlist() {
		currentPage = 1;
		// 获取第一页数据
		barCodeList = goodsDao.GetAlllistPage(etBarCode.getText().toString()
				.trim(), 1, pageSize);
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
			View view = LayoutInflater.from(GoodsDangAnActivity.this).inflate(
					R.layout.listgoodsdangandetail, null);
			TextView tvbarcode = (TextView) view.findViewById(R.id.tvbarcode);
			TextView tvname = (TextView) view.findViewById(R.id.tvname);
			TextView tvguige = (TextView) view.findViewById(R.id.tvguige);
			TextView tvstock = (TextView) view.findViewById(R.id.tvstock);
			TextView tbpinpai = (TextView) view.findViewById(R.id.tbpinpai);
			TextView tvunit = (TextView) view.findViewById(R.id.tvunit);
			TextView tvchandi = (TextView) view.findViewById(R.id.tvchandi);
			TextView tvprice = (TextView) view.findViewById(R.id.tvprice);

			tvbarcode.setText(barCodeList.get(position).getPkgbarcode()
					.toString());
			tvname.setText(barCodeList.get(position).getProductname()
					.toString());

			if (barCodeList.get(position).getGuige() == null) {
				tvguige.setText("");
			} else {
				tvguige.setText(barCodeList.get(position).getGuige().toString());
			}

			if (barCodeList.get(position).getStock() == null) {
				tvstock.setText("");
			} else {
				tvstock.setText(barCodeList.get(position).getStock().toString());
			}
			if (barCodeList.get(position).getPinpai() == null) {
				tbpinpai.setText("");
			} else {
				tbpinpai.setText(barCodeList.get(position).getPinpai()
						.toString());
			}

			if (barCodeList.get(position).getUnit() == null) {
				tvunit.setText("");
			} else {
				tvunit.setText(barCodeList.get(position).getUnit().toString());
			}

			if (barCodeList.get(position).getChandi() == null) {
				tvchandi.setText("");
			} else {
				tvchandi.setText(barCodeList.get(position).getChandi()
						.toString());
			}

			if (barCodeList.get(position).getPrice() == null) {
				tvprice.setText("");
			} else {
				tvprice.setText(barCodeList.get(position).getPrice().toString());
			}

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
		List<BarCode> tempbarCodeList = goodsDao.GetAlllistPage(etBarCode
				.getText().toString().trim(), pageStart, pageSize);

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
