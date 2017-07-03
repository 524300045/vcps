package com.wologic.control;

import com.wologic.R;
import com.wologic.ui.GoodsActivity;
import com.wologic.ui.MainChuKuActivity;
import com.wologic.ui.MainPanDianActivity;
import com.wologic.ui.MainRuKuActivity;
import com.wologic.ui.MoreActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageFunction extends LinearLayout {

	private Context context;
	private ImageView img;
	public ImageFunction(Context context,String functionId) {
		super(context);
		this.context = context;
		 // 导入布局  
       LayoutInflater.from(context).inflate(R.layout.imgfunction, this, true);  
       img=(ImageView) findViewById(R.id.img);
       img.setTag(functionId);
       if(functionId.equals("pd"))
       {
    	   img.setImageResource(R.drawable.pandian);
       }
       if(functionId.equals("th"))
       {
    	   img.setImageResource(R.drawable.tuihuo);
       }
       if(functionId.equals("ch"))
       {
    	   img.setImageResource(R.drawable.chuhuo);
       }
       if(functionId.equals("sh"))
       {
    	   img.setImageResource(R.drawable.shouhuo);
       }
       if(functionId.equals("fh"))
       {
    	   img.setImageResource(R.drawable.fahuo);
       }
       if(functionId.equals("rk"))
       {
    	   img.setImageResource(R.drawable.ruku);
       }
       if(functionId.equals("dh"))
       {
    	   img.setImageResource(R.drawable.dihuo);
       }
       if(functionId.equals("gd"))
       {
    	   img.setImageResource(R.drawable.geduo);
       }
      
       img.setOnClickListener(mListener);
	}
	
 	
   	OnClickListener  mListener = new OnClickListener() {  
           @Override  
           public void onClick(View v) {  
        	   
        	  String functionId=v.getTag().toString();
        	
        	  if(functionId.equals("pd"))
              {
        		  context.startActivity(new Intent(getContext(), MainPanDianActivity.class));
              }
              if(functionId.equals("th"))
              {
           	  
              }
              if(functionId.equals("ch"))
              {
            	  context.startActivity(new Intent(getContext(), MainChuKuActivity.class));
              }
              if(functionId.equals("sh"))
              {
           	   img.setImageResource(R.drawable.shouhuo);
              }
              if(functionId.equals("fh"))
              {
           	   img.setImageResource(R.drawable.fahuo);
              }
              if(functionId.equals("rk"))
              {
  				 context.startActivity(new Intent(getContext(), MainRuKuActivity.class));
              }
              if(functionId.equals("dh"))
              {
           	   img.setImageResource(R.drawable.dihuo);
              }
              if(functionId.equals("gd"))
              {
            	  context.startActivity(new Intent(getContext(), MoreActivity.class));
              }
             
           	
           }  
       };  

}
