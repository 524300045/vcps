package com.wologic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wologic.R;
import com.wologic.control.ImageFunction;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.RuKuDao;
import com.wologic.dao.VersionInfoDao;
import com.wologic.dao.WFunctionDao;
import com.wologic.domain.VersionInfo;
import com.wologic.domain.WFuction;
import com.wologic.domain.WorkItem;
import com.wologic.util.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
//主界面显示设置,table1存放显示的图标信息
public class ScanFragment extends Fragment {

	private ImageView imgruku, imgmore, imgfahuo, imgchuku, imgpandian;

	private RuKuDao ruKuDao;
	private ItemDataDao itemDataDao;
	
	private VersionInfoDao vinfoDao;

	private WFunctionDao functionDao;

	private ListView lv;

	private TableLayout tl;
	
	private TextView tvtitle,tvversion;
	
	private LinearLayout wologiccalculator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.scanfragmentpage, null);
		tl = (TableLayout) view.findViewById(R.id.table1);
		tvtitle = (TextView) view.findViewById(R.id.tvtitle);
		tvversion = (TextView) view.findViewById(R.id.tvversion);
		wologiccalculator = (LinearLayout)view.findViewById(R.id.wologiccalculator);
		ruKuDao = new RuKuDao();
		itemDataDao = new ItemDataDao();
		functionDao = new WFunctionDao();
		vinfoDao=new VersionInfoDao();
		
		VersionInfo versionInfo=vinfoDao.GetVersionInfo(Common.clientId);
		if(versionInfo!=null)
		{
			tvtitle.setText(versionInfo.getVersionName());
			tvversion.setText(versionInfo.getVersionId());
		}

		lv = (ListView) view.findViewById(R.id.lv);
		initevent();
		init();
		bindlist();
		return view;
	}

	private void init() {
		//获取显示的业务信息
		List<WFuction> list = functionDao.GetAllFunctionInfo(Common.clientId);
		if (list != null && list.size() > 0) {
			if (list.size() > 4) {

				TableRow tableRow1 = new TableRow(getActivity());
				TableRow tableRow2 = new TableRow(getActivity());
				tl.addView(tableRow1);
				tl.addView(tableRow2);
				for (int i = 0; i < 4; i++) {
					ImageView img = new ImageView(getActivity());
					
					ImageFunction imgFunction = new ImageFunction(getActivity(),list.get(i).getFuctionid());
					TextView textView = new TextView(getActivity());
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(list.get(i).getFuctionname());

					tableRow1.addView(imgFunction);
					tableRow2.addView(textView);
				}

				TextView tvPad = new TextView(getActivity());
				tvPad.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, 15, 1.0f));
				tvPad.setGravity(Gravity.CENTER);
				tvPad.setTextColor(Color.parseColor("#000000"));
				tl.addView(tvPad);

				TableRow tableRow3 = new TableRow(getActivity());
				TableRow tableRow4 = new TableRow(getActivity());
				tl.addView(tableRow3);
				tl.addView(tableRow4);
				for (int i = 4; i < list.size(); i++) {
					ImageView img = new ImageView(getActivity());
					img.setImageResource(R.drawable.pandian);
					ImageFunction imgFunction = new ImageFunction(getActivity(),list.get(i).getFuctionid());

					TextView textView = new TextView(getActivity());
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(list.get(i).getFuctionname());

					tableRow3.addView(imgFunction);
					tableRow4.addView(textView);
				}

			} else {
				TableRow tableRow1 = new TableRow(getActivity());
				TableRow tableRow2 = new TableRow(getActivity());
				tl.addView(tableRow1);
				tl.addView(tableRow2);
				for (WFuction item : list) {
					ImageView img = new ImageView(getActivity());
					img.setImageResource(R.drawable.pandian);

					ImageFunction imgFunction = new ImageFunction(getActivity(),item.getFuctionid());

					TextView textView = new TextView(getActivity());
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(item.getFuctionname());

					tableRow1.addView(imgFunction);
					tableRow2.addView(textView);
				}

				TextView tvPad = new TextView(getActivity());
				tvPad.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, 15, 1.0f));
				tvPad.setGravity(Gravity.CENTER);
				tvPad.setTextColor(Color.parseColor("#000000"));
				tl.addView(tvPad);
			}
		}
	}
//主界面的操作记录信息显示
	private void bindlist() {
		//拿前四条操作记录
		List<WorkItem> list = ruKuDao.getTopNWorkItem();
		if (list != null & list.size() > 0) {
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			for (WorkItem item : list) {
				Long count = itemDataDao.getTotalCount(item.getItemno());
				double num = itemDataDao.GetTotalNum(item.getItemno());
				if(!item.getItemstate().equals("s10")&&!item.getItemstate().equals("s09")){
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				if (item.getItemtype().equals("w1")) {
					map.put("itemno", "[入库]" + item.getItemno());
					map.put("img", R.drawable.pandian);
				} else if (item.getItemtype().equals("w2")) {
					map.put("itemno", "[出库]" + item.getItemno());
					map.put("img", R.drawable.pandian);
				} else if (item.getItemtype().equals("w3")) {
					map.put("itemno", "[盘点]" + item.getItemno());
					map.put("img", R.drawable.pandian);
				} else {
					map.put("itemno", "" + item.getItemno());
					map.put("img", R.drawable.pandian);
				}

				if (item.getItemstate().equals("s10")) {//已完成
					map.put("state", "●");
				} 
				else if(item.getItemstate().equals("s09")){//正在操作
					map.put("state", "○");
				}
				else{//尚未操作
//					map.put("state", "☆");
				}
				map.put("time", Common.getStringDate(item.getWorktime()));
				map.put("count", "[条数]" + count);
				map.put("num", "[总数]" + num);
				map.put("code", item.getItemno());
				map.put("type", item.getItemtype());
				mapList.add(map);
			}

			SimpleAdapter adp = new SimpleAdapter(getActivity(), mapList,
					R.layout.listworkitem, new String[] { "itemno", "state",
							"time", "count", "num", "img", "code", "type" },
					new int[] { R.id.tvruku, R.id.tvstate, R.id.tvtime,
							R.id.tvcount, R.id.tvnum, R.id.img, R.id.tvcode,
							R.id.tvtype });
			lv.setAdapter(adp);
		}
	}
	public class SpecialAdapter extends SimpleAdapter{  
		LayoutInflater inflator;
	    @SuppressWarnings("unchecked")
		public SpecialAdapter(Context context, List<? extends Map<String, ?>> data,  
	            int resource, String[] from, int[] to) {  
	        super(context, data, resource, from, to);  
	    	inflator = LayoutInflater.from(context);
	        // TODO Auto-generated constructor stub  
	    }  
	    /* (non-Javadoc) 
	     * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup) 
	     */  
	    @SuppressWarnings("unchecked")
		@Override  
	    public View getView(int position, View convertView, ViewGroup parent) {  
	        // TODO Auto-generated method stub  
	    	if (convertView == null) {
                convertView = inflator.inflate(android.R.layout.simple_list_item_2, parent, false);
            }
	        TextView view = (TextView) convertView.findViewById(android.R.id.text1);
	        TextView view1 = (TextView) convertView.findViewById(android.R.id.text2);
	        view.setTextSize(14);
	        view1.setTextSize(14);
	        return convertView;  
	    }  
	} 
//主界面，操作记录点击事件
	private void initevent() {

		wologiccalculator.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClassName("com.android.calculator2", "com.android.calculator2.Calculator");
				startActivity(intent);
			}
			
		});
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 获取当前选择的入库单号
				TextView tvcode = (TextView) arg1.findViewById(R.id.tvcode);// 来源单号
				TextView tvtype = (TextView) arg1.findViewById(R.id.tvtype);// 来源单号
				//首先判断本地数据库是否已经包含此单号，不包含则向服务器取回单号的详细信息			
				if (tvtype.getText().equals("w1")) {
					Intent intent = new Intent(getActivity(),
							RuKuGoodsActivity.class);
					intent.putExtra("itemno", tvcode.getText());// 传递入库单号
					startActivity(intent);
				}
				if (tvtype.getText().equals("w2")) {
					Intent intent = new Intent(getActivity(),
							ChuKuGoodsActivity.class);
					intent.putExtra("itemno", tvcode.getText());// 传递入库单号
					startActivity(intent);
				}
				if (tvtype.getText().equals("w3")) {
					Intent intent = new Intent(getActivity(),
							PanDianGoodsActivity.class);
					intent.putExtra("itemno", tvcode.getText());// 传递入库单号
					startActivity(intent);
				}

			}
		});
	}

}
