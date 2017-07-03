package com.wologic.ui;


import com.wologic.R;
import com.wologic.dao.ParameterDao;
import com.wologic.domain.Parameter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ZuoYeSetActivity  extends Activity {

	
	private TextView tbBack;
	
	private Button btnsave;
	
	private CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
	
	private ParameterDao parameterDao;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zuoyeset);
		tbBack=(TextView)findViewById(R.id.tvback);
		btnsave=(Button)(TextView)findViewById(R.id.btnsave);
		checkBox1=(CheckBox)(TextView)findViewById(R.id.checkBox1);
		checkBox2=(CheckBox)(TextView)findViewById(R.id.checkBox2);
		checkBox3=(CheckBox)(TextView)findViewById(R.id.checkBox3);
		checkBox4=(CheckBox)(TextView)findViewById(R.id.checkBox4);
		parameterDao=new ParameterDao();
		
		Parameter parameterOne=parameterDao.getParameterById(2);
		if(parameterOne!=null&&parameterOne.getParaValue1().equals("1"))
		{
			checkBox1.setChecked(true);
		}
		
		
		Parameter parameterTwo=parameterDao.getParameterById(3);
		if(parameterTwo!=null&&parameterTwo.getParaValue1().equals("1"))
		{
			checkBox2.setChecked(true);
		}
		
		Parameter parameterThree=parameterDao.getParameterById(4);
		if(parameterThree!=null&&parameterThree.getParaValue1().equals("1"))
		{
			checkBox3.setChecked(true);
		}
		
		Parameter parameterFour=parameterDao.getParameterById(5);
		if(parameterFour!=null&&parameterFour.getParaValue1().equals("1"))
		{
			checkBox4.setChecked(true);
		}
		
		
		btnsave.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(checkBox1.isChecked())
				{
					Parameter p=new Parameter();
					p.setParameterId(2);
					p.setParaValue1("1");
					parameterDao.updateParameter(p);
				}
				else
				{
					
					Parameter p=new Parameter();
					p.setParameterId(2);
					p.setParaValue1("0");
					parameterDao.updateParameter(p);
				}
				
				if(checkBox2.isChecked())
				{
					
					Parameter p=new Parameter();
					p.setParameterId(3);
					p.setParaValue1("1");
					parameterDao.updateParameter(p);
				}
				else
				{
					
					Parameter p=new Parameter();
					p.setParameterId(3);
					p.setParaValue1("0");
					parameterDao.updateParameter(p);
				}
				
				
				if(checkBox3.isChecked())
				{
					Parameter p=new Parameter();
					p.setParameterId(4);
					p.setParaValue1("1");
					parameterDao.updateParameter(p);
					
				}
				else
				{
					Parameter p=new Parameter();
					p.setParameterId(4);
					p.setParaValue1("0");
					parameterDao.updateParameter(p);
				}
				
				
				if(checkBox4.isChecked())
				{
					Parameter p=new Parameter();
					p.setParameterId(5);
					p.setParaValue1("1");
					parameterDao.updateParameter(p);
					
				}
				else
				{
					Parameter p=new Parameter();
					p.setParameterId(5);
					p.setParaValue1("0");
					parameterDao.updateParameter(p);
				}
				
				Toast.makeText(ZuoYeSetActivity.this, "保存设置成功", 1000).show();
				
				
			}});
		
		tbBack.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
			}});
    }
}
