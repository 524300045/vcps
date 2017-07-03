package com.wologic.ui;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wologic.R;
import com.wologic.util.Toaster;

public class SettingFragment extends Fragment {

	private ImageView imgcomset,imgzuoyeset;
	private TextView tvcomset,tvzuoye,tvanquan,tvsaomiao,tvshuju;
	private LinearLayout tvcomsettotal,tvzuoyetotal,tvanquantotal,tvsaomiaototal,tvshujutotal;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.settingfragmentpage, null);
		imgcomset=(ImageView)view.findViewById(R.id.imgcomset);
		//imgzuoyeset=(ImageView)view.findViewById(R.id.tvcomset);
		
		tvcomset=(TextView)view.findViewById(R.id.tvcomset);
		tvzuoye=(TextView)view.findViewById(R.id.tvzuoye);
		tvanquan=(TextView)view.findViewById(R.id.tvanquan);
		tvsaomiao=(TextView)view.findViewById(R.id.tvsaomiao);
//		tvshuju=(TextView)view.findViewById(R.id.tvshuju);
		tvcomsettotal =(LinearLayout)view.findViewById(R.id.tvcomsettotal);
		tvzuoyetotal =(LinearLayout)view.findViewById(R.id.tvzuoyetotal);
		tvanquantotal =(LinearLayout)view.findViewById(R.id.tvanquantotal);
		tvsaomiaototal =(LinearLayout)view.findViewById(R.id.tvsaomiaototal);
		tvshujutotal =(LinearLayout)view.findViewById(R.id.tvshujutotal);
		
		tvcomsettotal.setOnClickListener((new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), CommunicationSetActivity.class);
				startActivity(intent);
			}
			
		}));
	
		
		tvzuoyetotal.setOnClickListener((new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), ZuoYeSetActivity.class);
				startActivity(intent);
			}
			
		}));
		
		tvanquantotal.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(tvanquan.getCurrentTextColor()!=-1315861){
					Intent intent = new Intent(getActivity(), AnQuanSetActivity.class);
					startActivity(intent);
				}
				else
				{
					Toaster.toaster("该功能未开启!");
				}
			}});
		
		tvsaomiaototal.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), ScanSetActivity.class);
				startActivity(intent);
				
			}});
		tvshujutotal.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(), DataSetActivity.class);
				startActivity(intent);
			}});
		return view;
	}
 
}
