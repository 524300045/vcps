package com.wologic.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.application.MyApplication;

import com.wologic.domainnew.User;
import com.wologic.request.LoginRequest;
import com.wologic.util.Common;
import com.wologic.util.Toaster;

public class LoginActivity extends Activity implements OnItemClickListener {

	
	private MyApplication app;
	private Button btn_login, btn_logout;
	String code = "";
	String pwd = "";
	private EditText et_userName, et_userPassword;

	List<String> list1 = new ArrayList<String>();

	Dialog dialog;
	ListView mlistView;
	BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		app = (MyApplication) getApplication();
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		et_userName = (EditText) findViewById(R.id.et_userName);
		et_userPassword = (EditText) findViewById(R.id.et_userPassword);
		//et_userName.setText("10");
		//et_userPassword.setText("001");
		
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				login();
			}
		});

		btn_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	private void login() {
		code = et_userName.getText().toString();
		pwd = et_userPassword.getText().toString();

		if (code.equals("")) {
			com.wologic.util.Toaster.toaster("用户名不能为空!");
			return;
		}
		if (pwd.equals("")) {
			Toaster.toaster("密码不能为空!");
			return;
		}
		loginSync(code,pwd);

	}

	
	private void loginSync(final String code,final String pwd)
	{
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl ="http://www.bjkalf.net:8090/services/user/checkAndGetUserResource";

					LoginRequest request=new LoginRequest();
					request.setName(code);
					request.setPassword(pwd);

					String json = JSON.toJSONString(request);
					String resultSearch = com.wologic.util.SimpleClient
							.httpPost(searchUrl, json);

					JSONObject jsonSearch = new JSONObject(resultSearch);
					if (jsonSearch.optString("code").toString().equals("200"))
					{
						User user = JSON
								.parseObject(jsonSearch
										.optString("result"),
										User.class);
						if(user==null)
						{
							Message msg = new Message();
							msg.what =2;
							msg.obj ="查询不到客户公司信息";
							handler.sendMessage(msg);
						}
						else
						{
							Message msg = new Message();
							msg.what =1;
							msg.obj =user;
							handler.sendMessage(msg);
						}
						
					}
					else
					{
						Message msg = new Message();
						msg.what =2;
						msg.obj ="用户名或密码错误";
						handler.sendMessage(msg);
					}
					
				} catch (Exception e) {
					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "网络异常,请检查单号是否存在";
					handler.sendMessage(msg);
				}
			}
		});
		mThread.start();
		
	}
	
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				User user=(User)msg.obj;
				
				SharedPreferences.Editor editor = getSharedPreferences("data",
						MODE_PRIVATE).edit();
				editor.putString("username", code);
				editor.commit();
				app.setUsername(code);
				Common.userID = code;
				Common.partnerCode=user.getCompanyCode();
				Common.partnerName=user.getCompanyName();	
				Common.RealName=user.getCnName();
				Common.UserName=user.getName();
				Intent intent = new Intent(LoginActivity.this, MainTwoActivity.class);
				startActivity(intent);
				finish();
				break;
			case 2:
			
				Toaster.toaster(msg.obj.toString());
				break;
			case 3:
				break;
			default:
				break;
			}
		}
	};

	 
	public void showDialog(String title, List<String> list) {
		AlertDialog.Builder builder = new Builder(this,
				AlertDialog.THEME_HOLO_LIGHT);
		// builder.setTitle(title);
		final LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.dialog_item, null);
		adapter = new MyAdapter(this, list);
		mlistView = (ListView) v.findViewById(R.id.dialoglist);
		mlistView.setAdapter(adapter);
		mlistView.setOnItemClickListener(this);
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

		// mlistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		/*
		 * Window window = dialog.getWindow(); WindowManager.LayoutParams lp =
		 * window.getAttributes(); lp.alpha = 0.9f; window.setAttributes(lp);
		 */

		LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
		View mTitleView = layoutInflater.inflate(R.layout.dialogtitle, null);

		builder.setCustomTitle(mTitleView);

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				canCloseDialog(dialog, false);// 不关闭对话框
				for (int i = 0, j = mlistView.getCount(); i < j; i++) {
					View child = mlistView.getChildAt(i);
					RadioButton rdoBtn = (RadioButton) child
							.findViewById(R.id.radio_btn);
					if (rdoBtn.isChecked()) {
						String name = list1.get(i);
						Toast.makeText(LoginActivity.this, name, 1000).show();
					}

				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				canCloseDialog(dialog, true);// 不关闭对话框
			}
		});

		builder.setView(v);

		dialog = builder.create();
		builder.show();// .getWindow().requestFeature(Window.FEATURE_NO_TITLE)
	}

	// 关键部分在这里
	private void canCloseDialog(DialogInterface dialogInterface, boolean close) {
		try {
			Field field = dialogInterface.getClass().getSuperclass()
					.getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialogInterface, close);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class MyAdapter extends BaseAdapter {
		String mg;
		private ListView father;
		List<String> list;

		private Context context;
		HashMap<String, Boolean> states = new HashMap<String, Boolean>();// 用于记录每个RadioButton的状态，并保证只可选一个

		private LayoutInflater mInflater;

		public MyAdapter(Context context, List<String> list) {

			this.context = context;
			this.mInflater = LayoutInflater.from(context);
			this.list = list;
		}

		public int getCount() {
			return list.size();

		}

		public Object getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public boolean isEnabled(int position) {

			if (getItemViewType(position) == 1) {
				return false;
			}
			return super.isEnabled(position);

		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {

			/*
			 * father = (ListView) parent; convertView = mInflater
			 * .inflate(R.layout.list_item, null); TextView tv_adress =
			 * (TextView) convertView .findViewById(R.id.listtext);;
			 * tv_adress.setText(list.get(position));
			 */
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.list_item, null);
				holder = new ViewHolder();
				holder.background = (LinearLayout) convertView
						.findViewById(R.id.lyitem);
				holder.tvname = (TextView) convertView
						.findViewById(R.id.listtext);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final RadioButton radio = (RadioButton) convertView
					.findViewById(R.id.radio_btn);

			holder.rdBtn = radio;
			holder.tvname.setText(list.get(position));

			if (list.size() > 0) {
				if (list.size() == 1) {
					// holder.background.setBackgroundResource(R.drawable.more_item_press);
				} else {
					if (position == 0) {
						// holder.background.setBackgroundResource(R.drawable.more_itemtop_press);
					} else if (position == list.size() - 1) {
						// holder.background.setBackgroundResource(R.drawable.more_itembottom_press);
					} else {
						// holder.background.setBackgroundResource(R.drawable.more_itemmiddle_press);
					}
				}
			}

			// 当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
			holder.rdBtn.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {

					// 重置，确保最多只有一项被选中
					for (String key : states.keySet()) {
						states.put(key, false);

					}
					states.put(String.valueOf(position), radio.isChecked());
					MyAdapter.this.notifyDataSetChanged();
				}
			});

			boolean res = false;
			if (states.get(String.valueOf(position)) == null
					|| states.get(String.valueOf(position)) == false) {
				res = false;
				states.put(String.valueOf(position), false);
			} else
				res = true;

			holder.rdBtn.setChecked(res);

			return convertView;

		}

		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState != OnScrollListener.SCROLL_STATE_IDLE) {
				isIdle = false;
			} else {
				isIdle = true;
				notifyDataSetChanged();
			}
		}

		private volatile boolean isIdle = true;

	}

	static class ViewHolder {
		LinearLayout background;
		TextView tvname;
		RadioButton rdBtn;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(LoginActivity.this, "3", 1000).show();
		switch (arg0.getId()) {
		case R.id.dialoglist:
			Toast.makeText(LoginActivity.this, list1.get(arg2), 2000).show();
			break;
		default:
			break;
		}
	}
}
