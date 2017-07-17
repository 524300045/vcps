package com.wologic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wologic.R;
import com.wologic.control.CustomDialog;
import com.wologic.control.CustomSureDialog;
import com.wologic.ui.ContentAdapter.Callback;
import com.wologic.util.Common;
import com.wologic.util.Toaster;

public class ExecActivity extends Activity implements OnItemClickListener,Callback  {


	
	private ListView lv;
	private SimpleAdapter adp;// 定义适配器
	private List<Map<String, Object>> mapList;// 定义数据源

	
	private TextView tbBack;

	private int totalCount;// 总的记录数



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
		bindList();
	}

	
	private void bindList()
	{

        /*
         *  参数1是上下文对象
         *  参数2是数据源
         *  参数3是布局文件
         *  参数4是键名
         *  参数5是绑定布局文件中视图ID
         * 
         * */
        mapList=new ArrayList<Map<String,Object>>();
        
        for(int i=0;i<10;i++)
        {
        	Map<String,Object> map=new HashMap<String,Object>();
       
        	map.put("storeCode",i);
        	map.put("storeName","商品价格:$"+i);
        	map.put("process","100%");
        	mapList.add(map);
        }
     
      /*  adp=new SimpleAdapter(this, mapList,R.layout.listmendiandetail, new String[]{"storeCode","storeName","process"}, new int[]{R.id.tvmendiancode,R.id.tvmendian,R.id.tvjindu});
         lv.setAdapter(adp);*/

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
      /*  Toast.makeText(this, "listview的item被点击了！，点击的位置是-->" + position,
                Toast.LENGTH_SHORT).show();*/
    }

    /**
     * 接口方法，响应ListView按钮点击事件
     */
    @Override
    public void click(View v) {
        Toast.makeText(
        		ExecActivity.this,
                "listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
                        + mapList.get((Integer) v.getTag()).get("storeCode")+"--",
                Toast.LENGTH_SHORT).show();
          dialog("当前门店还差 2个包裹未分拣完成，请确认是否强制完成?");
    }

	
	private void dialog(final String title) {
		final CustomSureDialog dialog = new CustomSureDialog(ExecActivity.this);
		TextView textview = (TextView) dialog.findViewById(R.id.title);
		textview.setVisibility(View.VISIBLE);
		textview.setText(title);
		dialog.setTitle(textview);
		

		dialog.setOnPositiveListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// dosomething youself
				 Toast.makeText(ExecActivity.this, "确定",
			                Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				
			}
		});
		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Toast.makeText(ExecActivity.this, "取消",
			                Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	

}
