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
	private SimpleAdapter adp;// ����������
	private List<Map<String, Object>> mapList;// ��������Դ

	
	private TextView tbBack;

	private int totalCount;// �ܵļ�¼��



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
         *  ����1�������Ķ���
         *  ����2������Դ
         *  ����3�ǲ����ļ�
         *  ����4�Ǽ���
         *  ����5�ǰ󶨲����ļ�����ͼID
         * 
         * */
        mapList=new ArrayList<Map<String,Object>>();
        
        for(int i=0;i<10;i++)
        {
        	Map<String,Object> map=new HashMap<String,Object>();
       
        	map.put("storeCode",i);
        	map.put("storeName","��Ʒ�۸�:$"+i);
        	map.put("process","100%");
        	mapList.add(map);
        }
     
      /*  adp=new SimpleAdapter(this, mapList,R.layout.listmendiandetail, new String[]{"storeCode","storeName","process"}, new int[]{R.id.tvmendiancode,R.id.tvmendian,R.id.tvjindu});
         lv.setAdapter(adp);*/

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
      /*  Toast.makeText(this, "listview��item������ˣ��������λ����-->" + position,
                Toast.LENGTH_SHORT).show();*/
    }

    /**
     * �ӿڷ�������ӦListView��ť����¼�
     */
    @Override
    public void click(View v) {
        Toast.makeText(
        		ExecActivity.this,
                "listview���ڲ��İ�ť������ˣ���λ����-->" + (Integer) v.getTag() + ",������-->"
                        + mapList.get((Integer) v.getTag()).get("storeCode")+"--",
                Toast.LENGTH_SHORT).show();
          dialog("��ǰ�ŵ껹�� 2������δ�ּ���ɣ���ȷ���Ƿ�ǿ�����?");
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
				 Toast.makeText(ExecActivity.this, "ȷ��",
			                Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				
			}
		});
		dialog.setOnNegativeListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Toast.makeText(ExecActivity.this, "ȡ��",
			                Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	

}
