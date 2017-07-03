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
	// 定义一个布局
	private LayoutInflater layoutInflater;
	
	private ParameterDao parameterDao;
	
	private WBaseInfoDao basicInfoDao;
	
	// 定义数组来存放Fragment界面
	private Class fragmentArray[] = { ScanFragment.class, DangAnFragment.class,
			SettingFragment.class, UserFragment.class,  };
	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_scan_btn,
			R.drawable.tab_dangan_btn, R.drawable.tab_setting_btn,
			R.drawable.tab_user_btn,  };
	// Tab选项卡的文字
	private String mTextviewArray[] = { "扫描", "档案", "设置", "登陆",  };
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
      
      
    }
    
    

    private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);
        
		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i);
					
		}
	}
	/**
	 * 给Tab按钮设置图标和文字
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
