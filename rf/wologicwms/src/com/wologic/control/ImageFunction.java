package com.wologic.control;

import com.wologic.R;
import com.wologic.ui.CancelPickerActivity;
import com.wologic.ui.ExecActivity;
import com.wologic.ui.GoodsActivity;
import com.wologic.ui.MainChuKuActivity;
import com.wologic.ui.MainPanDianActivity;
import com.wologic.ui.MainRuKuActivity;
import com.wologic.ui.MoreActivity;
import com.wologic.ui.PartnerPickerActivity;
import com.wologic.ui.PartnerPreActivity;
import com.wologic.ui.PickerActivity;

import android.app.Activity;
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
       if(functionId.equals("fj"))
       {
    	   img.setImageResource(R.drawable.pandian);
       }
       if(functionId.equals("qxfj"))
       {
    	   img.setImageResource(R.drawable.tuihuo);
       }
       if(functionId.equals("ex"))
       {
    	   img.setImageResource(R.drawable.chuhuo);
       }
       if(functionId.equals("ck"))
       {
    	   img.setImageResource(R.drawable.shouhuo);
       }
       if(functionId.equals("exit"))
       {
    	   img.setImageResource(R.drawable.fahuo);
       }
       if(functionId.equals("pp"))
       {
    	   img.setImageResource(R.drawable.ruku);
       }
       if(functionId.equals("po"))
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
        	
        	  if(functionId.equals("fj"))
              {
        		  context.startActivity(new Intent(getContext(), PickerActivity.class));
              }
              if(functionId.equals("qxfj"))
              {
            	  context.startActivity(new Intent(getContext(), CancelPickerActivity.class));
              }
              if(functionId.equals("ex"))
              {
            	  context.startActivity(new Intent(getContext(), ExecActivity.class));
              }
              if(functionId.equals("exit"))
              {
            	  Activity activity = (Activity) context;
            	  activity.finish();
            	  final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  context.startActivity(intent);
                  
           	   //img.setImageResource(R.drawable.shouhuo);
              }
              if(functionId.equals("pp"))
              {
            	  context.startActivity(new Intent(getContext(), PartnerPickerActivity.class));
              }
              if(functionId.equals("po"))
              {
  				 context.startActivity(new Intent(getContext(), PartnerPickerActivity.class));
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
