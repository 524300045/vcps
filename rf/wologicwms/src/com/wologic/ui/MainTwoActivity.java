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
import com.wologic.domainnew.Menu;
import com.wologic.domainnew.SubMenu;
import com.wologic.util.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==97)
		{
			Intent intent=new Intent(MainTwoActivity.this,PickerActivity.class);
			startActivity(intent);
			return true;
		}
		if(keyCode==64)
		{
			Intent intent=new Intent(MainTwoActivity.this,CancelPickerActivity.class);
			startActivity(intent);
			return true;
		}
		if(keyCode==2)
		{
			Intent intent=new Intent(MainTwoActivity.this,ExecActivity.class);
			startActivity(intent);
			return true;
		}
		if(keyCode==106)
		{
			Intent intent=new Intent(MainTwoActivity.this,PartnerPickerActivity.class);
			startActivity(intent);
			return true;
		}
		if(keyCode==96)
		{
			finish(); 
			Intent intent=new Intent(MainTwoActivity.this,LoginActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private ImageView imgruku, imgmore, imgfahuo, imgchuku, imgpandian;

	private ListView lv;

	private TableLayout tl;
	
	private TextView tvtitle,tvversion,tvuser,tbPartener;
	
	private LinearLayout wologiccalculator;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
			 {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanfragmentpage);
		tl = (TableLayout)findViewById(R.id.table1);
		tvtitle = (TextView)findViewById(R.id.tvtitle);
		tvversion=(TextView)findViewById(R.id.tvversion);
		tvuser=(TextView)findViewById(R.id.tvuser);
		tbPartener=(TextView)findViewById(R.id.tbPartener);
		
		tvtitle.setText("分拣系统");
		tvuser.setText(Common.RealName+"("+Common.UserName+")");
		tbPartener.setText(Common.partnerName+"("+Common.partnerCode+")");
		init();
	}

	private void init() {
		//获取显示的业务信息
		List<WFuction> list = new ArrayList<WFuction>();
		
		List<Menu> menuList=Common.menuDtos;
		List<SubMenu> subMenuList=null;
		if(menuList!=null)
		{
			for(Menu menuItem:menuList)
			{
				if(menuItem.getMenuCode().equals("RE00061"))
				{
					subMenuList=menuItem.getSubMenus();
					break;
				}
			}
		}
		
		if(subMenuList!=null)
		{
			
			for(SubMenu item:subMenuList)
			{
				if(item.getSubMenuCode().equals("RE00062"))
				{
					WFuction wone=new WFuction();
					wone.setFuctionid("fj");
					wone.setFuctionname("分拣(F2)");
					list.add(wone);
				}
				if(item.getSubMenuCode().equals("RE00064"))
				{
					WFuction wtwo=new WFuction();
					wtwo.setFuctionid("qxfj");
					wtwo.setFuctionname("解除装箱(F3)");
					list.add(wtwo);
				}
				
				if(item.getSubMenuCode().equals("RE00063"))
				{
					WFuction wPartnerPre=new WFuction();
					wPartnerPre.setFuctionid("pp");
					wPartnerPre.setFuctionname("包装装箱(F5)");
					list.add(wPartnerPre);
				}
				
			}
			
		}
		
		
	
		WFuction wexit=new WFuction();
		wexit.setFuctionid("exit");
		wexit.setFuctionname("重新登录(F1)");
		list.add(wexit);
		
		
		if (list != null && list.size() > 0) {
			
			if(list.size()==1)
			{
				TableRow tableRow1 = new TableRow(MainTwoActivity.this);
				TableRow tableRow2 = new TableRow(MainTwoActivity.this);
				tl.addView(tableRow1);
				tl.addView(tableRow2);
				for (int i = 0; i < 1; i++) {
					ImageView img = new ImageView(MainTwoActivity.this);
					
					ImageFunction imgFunction = new ImageFunction(MainTwoActivity.this,list.get(i).getFuctionid());
					TextView textView = new TextView(MainTwoActivity.this);
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(list.get(i).getFuctionname());

					tableRow1.addView(imgFunction);
					tableRow2.addView(textView);
				}
			}
			
			if(list.size()==2)
			{
				TableRow tableRow1 = new TableRow(MainTwoActivity.this);
				TableRow tableRow2 = new TableRow(MainTwoActivity.this);
				tl.addView(tableRow1);
				tl.addView(tableRow2);
				for (int i = 0; i < 2; i++) {
					ImageView img = new ImageView(MainTwoActivity.this);
					
					ImageFunction imgFunction = new ImageFunction(MainTwoActivity.this,list.get(i).getFuctionid());
					TextView textView = new TextView(MainTwoActivity.this);
					textView.setGravity(Gravity.CENTER);
					textView.setTextColor(Color.parseColor("#000000"));
					textView.setText(list.get(i).getFuctionname());

					tableRow1.addView(imgFunction);
					tableRow2.addView(textView);
				}
			}
			
			if(list.size()>=3)
			{
				TableRow tableRow1 = new TableRow(MainTwoActivity.this);
				TableRow tableRow2 = new TableRow(MainTwoActivity.this);
				tl.addView(tableRow1);
				tl.addView(tableRow2);
				for (int i = 0; i < 2; i++) {
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
				for (int i = 2; i < list.size(); i++) {
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
				
			}
			
			
		}
	}
//主界面的操作记录信息显示

}
