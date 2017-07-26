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
	
	private TextView tvtitle,tvversion,tvuser;
	
	private LinearLayout wologiccalculator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.scanfragmentpage, null);
		tl = (TableLayout) view.findViewById(R.id.table1);
		tvtitle = (TextView) view.findViewById(R.id.tvtitle);
		tvversion=(TextView) view.findViewById(R.id.tvversion);
		tvuser=(TextView) view.findViewById(R.id.tvuser);
		tvtitle.setText("分拣系统"+Common.partnerCode);
		tvuser.setText(Common.RealName+"("+Common.UserName+")");
		init();
		
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
	

}
