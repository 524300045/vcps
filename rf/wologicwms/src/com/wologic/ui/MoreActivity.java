package com.wologic.ui;


import com.wologic.R;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ProductDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreActivity extends Activity
{

	private TextView tbBack;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		tbBack = (TextView) findViewById(R.id.tvback);
		initEvent();
    }
	
	private void initEvent() {
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}
	
	
}
