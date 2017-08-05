package com.wologic.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.application.MyApplication;

import com.wologic.domainnew.User;
import com.wologic.domainnew.VersionInfo;
import com.wologic.request.LoginRequest;
import com.wologic.request.VersionInfoRequest;
import com.wologic.util.Common;
import com.wologic.util.Constant;
import com.wologic.util.DownLoadManager;
import com.wologic.util.Toaster;

public class LoginActivity extends Activity {

	private MyApplication app;
	private Button btn_login, btn_logout;
	String code = "";
	String pwd = "";
	private EditText et_userName, et_userPassword;

	private TextView tvVersion;
	
	List<String> list1 = new ArrayList<String>();

	Dialog dialog;
	ListView mlistView;
	BaseAdapter adapter;

	VersionInfo versionInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		app = (MyApplication) getApplication();
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		et_userName = (EditText) findViewById(R.id.et_userName);
		et_userPassword = (EditText) findViewById(R.id.et_userPassword);
		tvVersion=(TextView) findViewById(R.id.tvVersion);
		// et_userName.setText("10");
		// et_userPassword.setText("001");
		try {
			tvVersion.setText("Version "+getVersionName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		initEvents();

		// 获取服务端的版本号
		getServerVersion();
	}

	private void getServerVersion() {

		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/versionInfo/getVersionInfoByCode";
					VersionInfoRequest request = new VersionInfoRequest();
					request.setSystemCode("2");
					String json = JSON.toJSONString(request);
					String resultSearch = com.wologic.util.SimpleClient
							.httpPost(searchUrl, json);
					JSONObject jsonSearch = new JSONObject(resultSearch);
					if (jsonSearch.optString("code").toString().equals("200")) {
						versionInfo = JSON.parseObject(
								jsonSearch.optString("result"),
								VersionInfo.class);
						if (!versionInfo.getVersionCode().equals(
								getVersionName())) {
							// 进行升级操作

							Message msg = new Message();
							msg.what = 0;
							handler2.sendMessage(msg);

						} else {
							Message msg = new Message();
							msg.what = 3;
							handler2.sendMessage(msg);
						}
					}

				} catch (Exception e) {

					Message msg = new Message();
					msg.what = 2;
					handler2.sendMessage(msg);
					e.printStackTrace();

				}
			}
		});
		mThread.start();
	}

	private void initEvents() {
		et_userPassword.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:
						login();
					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;

				}
				return false;
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
		loginSync(code, pwd);

	}

	private void loginSync(final String code, final String pwd) {
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = "http://www.bjkalf.net:8090/services/user/checkAndGetUserResource";

					LoginRequest request = new LoginRequest();
					request.setName(code);
					request.setPassword(pwd);

					String json = JSON.toJSONString(request);
					String resultSearch = com.wologic.util.SimpleClient
							.httpPost(searchUrl, json);

					JSONObject jsonSearch = new JSONObject(resultSearch);
					if (jsonSearch.optString("code").toString().equals("200")) {
						User user = JSON.parseObject(
								jsonSearch.optString("result"), User.class);
						if (user == null) {
							Message msg = new Message();
							msg.what = 2;
							msg.obj = "查询不到客户公司信息";
							handler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 1;
							msg.obj = user;
							handler.sendMessage(msg);
						}

					} else {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = "用户名或密码错误";
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
				User user = (User) msg.obj;

				SharedPreferences.Editor editor = getSharedPreferences("data",
						MODE_PRIVATE).edit();
				editor.putString("username", code);
				editor.commit();
				app.setUsername(code);
				Common.userID = code;
				Common.partnerCode = user.getCompanyCode();
				Common.partnerName = user.getCompanyName();
				Common.RealName = user.getCnName();
				Common.UserName = user.getName();
				Common.menuDtos = user.getMenuDtos();
				Intent intent = new Intent(LoginActivity.this,
						MainTwoActivity.class);
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

	/*
	 * 获取当前程序的版本号
	 */
	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}

	Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				// 对话框通知用户升级程序
				showUpdataDialog();
				break;
			case 1:
				// 服务器超时
				Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", 1)
						.show();
				//LoginMain();
				break;
			case 2:
				// 下载apk失败
				Toast.makeText(getApplicationContext(), "下载新版本失败", 1).show();
				//LoginMain();
				break;
			case 3:
				// 下载apk失败

				LoginMain();
				break;
			}
		}
	};

	/*
	 * 
	 * 弹出对话框通知用户更新程序
	 * 
	 * 弹出对话框的步骤： 1.创建alertDialog的builder. 2.要给builder设置属性, 对话框的内容,样式,按钮
	 * 3.通过builder 创建一个对话框 4.对话框show()出来
	 */
	protected void showUpdataDialog() {
		AlertDialog.Builder builer = new Builder(LoginActivity.this);
		builer.setTitle("版本升级");
		builer.setMessage("发现新版本,请升级");
		// 当点确定按钮时从服务器上下载 新的apk 然后安装
		builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				downLoadApk();  
			}
		});

		// 当点取消按钮时进行登录
		builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//LoginMain();  
			}
		});

		AlertDialog dialog = builer.create();
		dialog.show();
	}

	/*
	 * 从服务器中下载APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = DownLoadManager.getFileFromServer(
							versionInfo.getUrl(), pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					
					Message msg = new Message();
					msg.what = 2;
					handler2.sendMessage(msg);
					pd.dismiss(); // 结束掉进度条对话框
					e.printStackTrace();
				}
			}
		}.start();
	}

	// 安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	/*
	 * 进入程序的主界面
	 */
	private void LoginMain() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		// 结束掉当前的activity
		this.finish();
	}
}
