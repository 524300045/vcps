package com.wologic.ui;

import com.wologic.R;
import com.wologic.util.Toaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserFragment extends Fragment {

	private Button btnexit;
	private LinearLayout lvdevice;
	private LinearLayout lvcontactus;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.userfragmentpage, null);
		btnexit=(Button)view.findViewById(R.id.btnexit);
		lvdevice=(LinearLayout)view.findViewById(R.id.tvdevice);
		lvcontactus=(LinearLayout)view.findViewById(R.id.contactus);
		btnexit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				 System.exit(0); 
			}});
		lvdevice.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				 Toaster.toaster("该功能未开启!");
			}});
		lvcontactus.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				 showDialog(
						"上海沃杰软件有限公司\n" +
				 		"联系方式:12345678\n" +
				 		"地址:上海市闵行区都会路1885号丽琴大厦\n");
			}});
		return view;
	}
	/**
	 * 关于我们
	 * @param title
	 */
	public void showDialog(String title){
		new AlertDialog.Builder(this.getActivity())
		.setTitle("关于我们")
		//设置对话框的标题
		.setMessage(title)
		//设置显示的信息
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// 确定按钮的响应事件,执行删除
								
							}
						}).show();// 在按键响应事件中显示此对话框
	}
}
