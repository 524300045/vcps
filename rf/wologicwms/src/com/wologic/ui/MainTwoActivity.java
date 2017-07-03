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
public class MainTwoActivity extends Activity {

	private ImageView imgruku, imgmore, imgfahuo, imgchuku, imgpandian;

	private ListView lv;

	private TableLayout tl;
	
	private TextView tvtitle,tvversion;
	
	private LinearLayout wologiccalculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
			 {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanfragmentpage);
		tl = (TableLayout)findViewById(R.id.table1);
		tvtitle = (TextView)findViewById(R.id.tvtitle);
		tvversion = (TextView)findViewById(R.id.tvversion);
		wologiccalculator = (LinearLayout)findViewById(R.id.wologiccalculator);
		init();
	}

	private void init() {
		//获取显示的业务信息
		List<WFuction> list = new ArrayList<WFuction>();
		
		WFuction wone=new WFuction();
		wone.setFuctionid("fj");
		wone.setFuctionname("包裹分拣(F2)");
		
		WFuction wtwo=new WFuction();
		wtwo.setFuctionid("qxfj");
		wtwo.setFuctionname("取消分拣(F3)");
		
		WFuction wThree=new WFuction();
		wThree.setFuctionid("ex");
		wThree.setFuctionname("异常处理(F4)");
		
		
		WFuction wfour=new WFuction();
		wfour.setFuctionid("ck");
		wfour.setFuctionname("出库发运(F5)");
		
		
		WFuction wexit=new WFuction();
		wexit.setFuctionid("exit");
		wexit.setFuctionname("重新登录(F12)");
		
		
		list.add(wone);
		list.add(wtwo);
		list.add(wThree);
		list.add(wfour);
		list.add(wexit);
		
		
		if (list != null && list.size() > 0) {
			if (list.size() > 4) {

				TableRow tableRow1 = new TableRow(MainTwoActivity.this);
				TableRow tableRow2 = new TableRow(MainTwoActivity.this);
				tl.addView(tableRow1);
				tl.addView(tableRow2);
				for (int i = 0; i < 4; i++) {
					ImageView img = new ImageView(MainTwoActivity.this);
					
					ImageFunction imgFunction = new ImageFunction(MainTwoActivity.this,list.get(i).getFuctionid());
					TextView textView = new TextView(MainTwoActivity.this);
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(list.get(i).getFuctionname());

					tableRow1.addView(imgFunction);
					tableRow2.addView(textView);
				}

				TextView tvPad = new TextView(MainTwoActivity.this);
				tvPad.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, 15, 1.0f));
				tvPad.setGravity(Gravity.CENTER);
				tvPad.setTextColor(Color.parseColor("#000000"));
				tl.addView(tvPad);

				TableRow tableRow3 = new TableRow(MainTwoActivity.this);
				TableRow tableRow4 = new TableRow(MainTwoActivity.this);
				tl.addView(tableRow3);
				tl.addView(tableRow4);
				for (int i = 4; i < list.size(); i++) {
					ImageView img = new ImageView(MainTwoActivity.this);
					img.setImageResource(R.drawable.pandian);
					ImageFunction imgFunction = new ImageFunction(MainTwoActivity.this,list.get(i).getFuctionid());

					TextView textView = new TextView(MainTwoActivity.this);
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(list.get(i).getFuctionname());

					tableRow3.addView(imgFunction);
					tableRow4.addView(textView);
				}

			} else {
				TableRow tableRow1 = new TableRow(MainTwoActivity.this);
				TableRow tableRow2 = new TableRow(MainTwoActivity.this);
				tl.addView(tableRow1);
				tl.addView(tableRow2);
				for (WFuction item : list) {
					ImageView img = new ImageView(MainTwoActivity.this);
					img.setImageResource(R.drawable.pandian);

					ImageFunction imgFunction = new ImageFunction(MainTwoActivity.this,item.getFuctionid());

					TextView textView = new TextView(MainTwoActivity.this);
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(item.getFuctionname());

					tableRow1.addView(imgFunction);
					tableRow2.addView(textView);
				}

				TextView tvPad = new TextView(MainTwoActivity.this);
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
