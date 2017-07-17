package com.wologic.control;

import com.wologic.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomSureDialog extends Dialog  {

	
	private Button positiveButton, negativeButton;
	private TextView title;

	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public CustomSureDialog(Context context) {
		super(context,R.style.dialog);
		setCustomDialog();
	}

	private void setCustomDialog() {
	
		View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_normal__sure_layout, null);
		title = (TextView) mView.findViewById(R.id.title);

		positiveButton = (Button) mView.findViewById(R.id.positiveButton);
		negativeButton = (Button) mView.findViewById(R.id.negativeButton);
		super.setContentView(mView);
	}
	

	 @Override
	public void setContentView(int layoutResID) {
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
	}

	@Override
	public void setContentView(View view) {
	}

	/** 
     * È·¶¨¼ü¼àÌýÆ÷ 
     * @param listener 
     */  
    public void setOnPositiveListener(View.OnClickListener listener){  
    	positiveButton.setOnClickListener(listener);  
    }  
    /** 
     * È¡Ïû¼ü¼àÌýÆ÷ 
     * @param listener 
     */  
    public void setOnNegativeListener(View.OnClickListener listener){  
    	negativeButton.setOnClickListener(listener);  
    }

}
