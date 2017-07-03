package com.wologic.ui;


import java.util.List;

import com.wologic.R;
import com.wologic.control.BasicInfoControl;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.WBaseInfoDao;
import com.wologic.domain.WBaseInfo;
import com.wologic.util.Common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DangAnFragment extends Fragment {

	private ImageView imggoods;
	private TextView tvgoods;
	
	private WBaseInfoDao infoDao;
	
	private GoodsDao goodsDao;
	
	private LinearLayout llcontent;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.danganfragmentpage, null);
		//容器
		llcontent=(LinearLayout)view.findViewById(R.id.llcontent);
		infoDao=new WBaseInfoDao();
		goodsDao=new GoodsDao();
		//获取所有模块信息
		List<WBaseInfo> infoList=infoDao.GetAllBasicInfo(Common.clientId);
		if(infoList!=null&&infoList.size()>0)
		{
			int i=1;
			for(WBaseInfo item:infoList)
			{
				if(item.getIsShow().equals("1"))
				{
					double num=0;
					String time="";
					if(item.getInfoId().equals("huopin"))
					{
						num=goodsDao.GetTotalCount();
					}
				    if(item.getUpdatetime()!=null)
				    {
				    	time=item.getUpdatetime().toString();
				    }
					BasicInfoControl control=new BasicInfoControl(getActivity(),item.getInfoname(),item.getInfoId(),i,num,time);
					llcontent.addView(control);
					i+=1;
				}
				
			}
		}
		
		return view;
	}
}
