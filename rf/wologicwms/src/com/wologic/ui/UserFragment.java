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
				 Toaster.toaster("�ù���δ����!");
			}});
		lvcontactus.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				 showDialog(
						"�Ϻ��ֽ�������޹�˾\n" +
				 		"��ϵ��ʽ:12345678\n" +
				 		"��ַ:�Ϻ�������������·1885�����ٴ���\n");
			}});
		return view;
	}
	/**
	 * ��������
	 * @param title
	 */
	public void showDialog(String title){
		new AlertDialog.Builder(this.getActivity())
		.setTitle("��������")
		//���öԻ���ı���
		.setMessage(title)
		//������ʾ����Ϣ
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {// ���ȷ����ť
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ȷ����ť����Ӧ�¼�,ִ��ɾ��
								
							}
						}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
	}
}
