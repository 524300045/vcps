package com.wologic.ui;

import com.wologic.R;
import com.wologic.dao.ParameterDao;
import com.wologic.domain.Parameter;
import com.wologic.util.Common;
import com.wologic.util.Toaster;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScanSetActivity extends Activity {

	private TextView tbBack;
	
	private Button btnSureSub,btnSure;
	
	private TextView tvfour,tvthree,tvtwo,tvone;
	private ParameterDao parameterDao;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanset);
		tbBack = (TextView) findViewById(R.id.tvback);
		tvone = (TextView) findViewById(R.id.tvone);
		tvtwo = (TextView) findViewById(R.id.tvtwo);
		tvthree = (TextView) findViewById(R.id.tvthree);
		tvfour = (TextView) findViewById(R.id.tvfour);
		btnSureSub=(Button)findViewById(R.id.btnSureSub);
		btnSure=(Button)findViewById(R.id.btnSure);
		
		parameterDao = new ParameterDao();
		
		Parameter parameter = parameterDao.getParameterById(7);
		if (parameter != null) {
			tvone.setText(parameter.getParaValue1());
			tvtwo.setText(parameter.getParaValue2());
		}
		
		Parameter parameter2 = parameterDao.getParameterById(8);
		if (parameter2 != null) {
			tvthree.setText(parameter2.getParaValue1());
			tvfour.setText(parameter2.getParaValue2());
		}
		
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnSure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(tvone.getText().toString().trim().equals("")||tvtwo.getText().toString().trim().equals(""))
				{
					Toaster.toaster("条码位数输入不能为空!");
					return;
				}
				if(!Common.isInteger(tvone.getText().toString().trim()))
				{
					Toaster.toaster("条码位数请输入整数!");
					 return;
				}
				if(!Common.isInteger(tvtwo.getText().toString().trim()))
				{
					Toaster.toaster("条码位数请输入整数!");
					 return;
				}

				Integer numone=Integer.valueOf(tvone.getText().toString().trim());
				if(numone<=0)
				{
					Toaster.toaster("位数必须大于0!");
					 return;
				}
				
				Integer numtwo=Integer.valueOf(tvtwo.getText().toString().trim());
				if(numtwo<=0)
				{
				    	Toaster.toaster("位数必须大于0!");
					 return;
				}
				
				if(numone>numtwo)
				{
					Toaster.toaster("开始位数不能大于结束位数!");
					 return;
				}
				
				Parameter p = new Parameter();
				p.setParameterId(7);
				p.setParaValue1(tvone.getText().toString().trim());
				p.setParaValue2(tvtwo.getText().toString().trim());
				parameterDao.updateParameter(p);
				Toast.makeText(ScanSetActivity.this, "保存设置成功", 1000)
						.show();
				
			}});
		
		btnSureSub.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(tvthree.getText().toString().trim().equals("")||tvfour.getText().toString().trim().equals(""))
				{
					Toaster.toaster("条码截取位置输入不能为空!");
					return;
				}
				
				if(!Common.isInteger(tvthree.getText().toString().trim()))
				{
					Toaster.toaster("条码截取位数请输入整数!");
					 return;
				}
				if(!Common.isInteger(tvfour.getText().toString().trim()))
				{
					Toaster.toaster("条码截取位数请输入整数!");
					 return;
				}

				Integer numone=Integer.valueOf(tvthree.getText().toString().trim());
				if(numone<=0)
				{
					Toaster.toaster("位数必须大于0!");
					 return;
				}
				
				Integer numtwo=Integer.valueOf(tvfour.getText().toString().trim());
				if(numtwo<=0)
				{
				    	Toaster.toaster("位数必须大于0!");
					 return;
				}
				
				if(numone>numtwo)
				{
					Toaster.toaster("开始位数不能大于结束位数!");
					 return;
				}
				
				Parameter p = new Parameter();
				p.setParameterId(8);
				p.setParaValue1(tvthree.getText().toString().trim());
				p.setParaValue2(tvfour.getText().toString().trim());
				parameterDao.updateParameter(p);
				Toast.makeText(ScanSetActivity.this, "保存设置成功", 1000)
						.show();
				
			}});
    }
}
