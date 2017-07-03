package com.wologic.control;

import com.wologic.R;
import com.wologic.ui.GoodsActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BasicInfoControl extends LinearLayout {

	private TextView tvgoods,tvnum,tvtime;
	private LinearLayout tvgoodstotal;
	 private Context context;
	public BasicInfoControl(final Context context,String title,String id,int controlid,double num,String time) {
		super(context);
		this.context = context;
		 // 导入布局  
        LayoutInflater.from(context).inflate(R.layout.basicinfo, this, true);  
        tvgoods= (TextView) findViewById(R.id.tvgoods);
        tvgoodstotal = (LinearLayout)findViewById(R.id.tvgoodstotal);
        tvnum= (TextView) findViewById(R.id.tvnum);
        tvtime= (TextView) findViewById(R.id.tvtime);
        tvgoods.setText(title);
        tvgoods.setId(controlid);
        tvgoods.setOnClickListener(mListener);
        
        tvgoodstotal.setId(controlid);
        tvgoodstotal.setOnClickListener(mListener);
        tvnum.setText("单据记录:"+String.valueOf(num));
        tvtime.setText("最近更新:"+time);
	}
	
	OnClickListener  mListener = new OnClickListener() {  
        @Override  
        public void onClick(View v) {  
        	  switch(v.getId()){  
        	  case 1:  
        		  context.startActivity(new Intent(getContext(), GoodsActivity.class));
                  break;
        	  case 2:  
                  break;
        	  }
        	
        }  
    };  

}
