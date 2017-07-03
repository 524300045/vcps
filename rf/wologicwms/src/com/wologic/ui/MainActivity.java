package com.wologic.ui;


import java.util.Date;

import com.wologic.R;
import com.wologic.R.layout;
import com.wologic.R.menu;
import com.wologic.dao.ParameterDao;
import com.wologic.dao.WBaseInfoDao;
import com.wologic.domain.Parameter;
import com.wologic.domain.WBaseInfo;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainActivity  extends FragmentActivity{

	
	private FragmentTabHost mTabHost;
	// ����һ������
	private LayoutInflater layoutInflater;
	
	private ParameterDao parameterDao;
	
	private WBaseInfoDao basicInfoDao;
	
	// �������������Fragment����
	private Class fragmentArray[] = { ScanFragment.class, DangAnFragment.class,
			SettingFragment.class, UserFragment.class,  };
	// ������������Ű�ťͼƬ
	private int mImageViewArray[] = { R.drawable.tab_scan_btn,
			R.drawable.tab_dangan_btn, R.drawable.tab_setting_btn,
			R.drawable.tab_user_btn,  };
	// Tabѡ�������
	private String mTextviewArray[] = { "ɨ��", "����", "����", "��½",  };
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
      
      
    }
    
    

    private void initView() {
		// ʵ�������ֶ���
		layoutInflater = LayoutInflater.from(this);
        
		// ʵ����TabHost���󣬵õ�TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// �õ�fragment�ĸ���
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// Ϊÿһ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// ��Tab��ť��ӽ�Tabѡ���
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// ����Tab��ť�ı���
			mTabHost.getTabWidget().getChildAt(i);
					
		}
	}
	/**
	 * ��Tab��ť����ͼ�������
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);

		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);

		return view;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
