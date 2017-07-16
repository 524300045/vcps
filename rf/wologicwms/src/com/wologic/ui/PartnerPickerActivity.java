package com.wologic.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.domain.ItemData;
import com.wologic.domainnew.PackTaskDetail;
import com.wologic.domainnew.PackageAllDetail;
import com.wologic.domainnew.PreprocessInfo;
import com.wologic.request.PackTaskDetailRequest;
import com.wologic.request.PackageDetailRequest;
import com.wologic.request.PreprocessInfoRequest;
import com.wologic.ui.ChuKuGoodsActivity.SpecialAdapter;
import com.wologic.util.Constant;
import com.wologic.util.Toaster;

public class PartnerPickerActivity extends Activity {

	private TextView tbBack;

	private EditText etPackageCode;

	private TextView tvmsg,tvGoodsName;
	
	private ListView lvnoend;
	
	
	private ListView lv;
	   private SimpleAdapter adp;//定义适配器  
	    private List<Map<String,Object>> mapList;//定义数据源  

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partnerpicker);
		tbBack = (TextView) findViewById(R.id.tvback);
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	
		tvGoodsName = (TextView) findViewById(R.id.tvGoodsName);
		tvmsg = (TextView) findViewById(R.id.tvmsg);
		etPackageCode = (EditText) findViewById(R.id.etPackageCode);
		tvGoodsName.setText("");
		lvnoend = (ListView) findViewById(R.id.lvnoend);
		//lv=(ListView)findViewById(R.id.listView2);  
		initEvent();
		bindList();
		etPackageCode.requestFocus();
	
	
	}
	
	private void bindList()
	{
		
		List<PackTaskDetail>  noendlist= new ArrayList<PackTaskDetail>();
		PackTaskDetail detailone=new PackTaskDetail();
		detailone.setId(1l);
		detailone.setStoredCode("001");
		detailone.setStoredName("北京");
		detailone.setPlanNum(new BigDecimal(10L));
		
		PackTaskDetail detailtwo=new PackTaskDetail();
		
		detailtwo.setId(1l);
		detailtwo.setStoredCode("002");
		detailtwo.setStoredName("河南");
		detailtwo.setPlanNum(new BigDecimal(20L));
		
		
		noendlist.add(detailone);
		noendlist.add(detailtwo);
		
		List<Map<String, Object>> 	mapnoendList = new ArrayList<Map<String, Object>>();

		for (PackTaskDetail item : noendlist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", item.getId());
			map.put("storeCode", item.getStoredCode());
			map.put("storeName", item.getStoredName());
			map.put("num", item.getPlanNum());
			mapnoendList.add(map);
			
		}
		SpecialAdapter adp = new SpecialAdapter(this, mapnoendList,
				R.layout.listitemstore, new String[] { "id",
						"storeCode", "storeName", "num" }, new int[] {
						R.id.tvId, R.id.tvStoreCode, R.id.tvStoreName,
						R.id.tvnum});
		lvnoend.setAdapter(adp);
	
		lvnoend.setAdapter(adp);
		
		
	}

	private void initEvent() {
		etPackageCode.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:
						tvmsg.setText("");
						tvmsg.setVisibility(View.GONE);
						String packageCode = etPackageCode.getText().toString()
								.trim();
						if (packageCode.equals("")) {
							etPackageCode.selectAll();
							Toaster.toaster("请扫描包裹号!");
							return true;
						}
						getPackageDetail(packageCode);
						break;
					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;
				}
				return false;
			}
		});

	
		lvnoend.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				TextView tvid = (TextView) arg1.findViewById(R.id.tvId);
				
				Intent intent = new Intent(PartnerPickerActivity.this,
						PartnerPreActivity.class);
				intent.putExtra("packagecode", tvid.getText());// 传递入库单号
				
				startActivity(intent);
				
				
				//获取productno
				/*TextView tvproductno = (TextView) arg1.findViewById(R.id.tvproductno);
				TextView tvproductname = (TextView) arg0.findViewById(R.id.tbname);
				String productno=tvproductno.getText().toString();
				String productname=tvproductname.getText().toString();
				//获取数量
				TextView tvNum = (TextView) arg1.findViewById(R.id.tvworknm);
				dialog(productno,tvNum.getText().toString(),productname);*/
			}
		});
	
	}
	

	
	private void getPackageDetail(final String packageCode)
	{
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					HttpClient client = com.wologic.util.SimpleClient
							.getHttpClient();

					String searchUrl = Constant.url
							+ "/packageDetail/getPackageDetailByPackageCode";

					PackageDetailRequest packageDetailRequest=new PackageDetailRequest();
					packageDetailRequest.setPackageCode(packageCode);
					String json=JSON.toJSONString(packageDetailRequest);
					String resultSearch = com.wologic.util.SimpleClient.httpPost(searchUrl, json);
					
					JSONObject jsonSearch = new JSONObject(resultSearch);
					if(jsonSearch.optString("code").toString().equals("200"))
					{
						if(null==jsonSearch.optString("result"))
						{
							//判断预包装表中是否存在
							
							//查询预包装信息
							searchUrl = Constant.url
									+ "/preprocessInfo/getPreprocessInfoByCode";
							
							PreprocessInfoRequest preprocessInfoRequest=new PreprocessInfoRequest();
							preprocessInfoRequest.setPreprocessCode(packageCode);
							String json3=JSON.toJSONString(preprocessInfoRequest);
							String resultSearch3 = com.wologic.util.SimpleClient.httpPost(searchUrl, json3);
							JSONObject jsonSearch3 = new JSONObject(resultSearch3);
							if(jsonSearch3.optString("code").toString().equals("200"))
							{
								if(null==jsonSearch3.opt("result")||"null".equals(jsonSearch3.opt("result").toString()))
								{
									Message msg = new Message();
									msg.what =2;
									msg.obj ="查询不到包裹信息" +
											"";
									handler.sendMessage(msg);
								}
								else
								{
									
									PreprocessInfo preprocessInfo=JSON.parseObject(jsonSearch3.optString("result"),PreprocessInfo.class);
									if(preprocessInfo.getStatus()==1)
									{
										
										Message msg = new Message();
										msg.what =2;
										msg.obj ="当前包裹已扫描过";
										handler.sendMessage(msg);
									}
									else
									{
										//加载门店需求信息
										String skuCode=preprocessInfo.getSkuCode();
										searchUrl = Constant.url
												+ "/packTaskDetail/getPackTaskDetailListBySkuCode";
										
										PackTaskDetailRequest packTaskDetailRequest=new PackTaskDetailRequest();
										packTaskDetailRequest.setSkuCode(skuCode);
										
										String json4=JSON.toJSONString(preprocessInfoRequest);
										String resultSearch4 = com.wologic.util.SimpleClient.httpPost(searchUrl, json4);
										JSONObject jsonSearch4 = new JSONObject(resultSearch4);
										if(jsonSearch4.optString("code").toString().equals("200"))
										{
											if(null==jsonSearch4.opt("result")||"null".equals(jsonSearch4.opt("result").toString()))
											{
												Message msg = new Message();
												msg.what =2;
												msg.obj ="获取不到门店需求信息" ;
												handler.sendMessage(msg);
											}
											else
											{
												List<PackTaskDetail> packTaskDetailList=JSON.parseArray(jsonSearch4.opt("result").toString(), PackTaskDetail.class);
												 if(packTaskDetailList!=null)
												 {
													  //绑定门店列表
													 
												 }
											}
										}
										else
										{
											Message msg = new Message();
											msg.what =2;
											msg.obj =jsonSearch3.optString("message");
											handler.sendMessage(msg);
										}
										
										
									}
								}
							}
							else
							{
								Message msg = new Message();
								msg.what =2;
								msg.obj =jsonSearch3.optString("message");
								handler.sendMessage(msg);
							}
						
						}
						else
						{
							PackageAllDetail detail=JSON.parseObject(jsonSearch.optString("result"),PackageAllDetail.class);
							if(detail.getStatus()>=5)
							{
								Message msg = new Message();
								msg.what =2;
								msg.obj ="包裹已经分拣过";
								handler.sendMessage(msg);
							}
							else
							{
								//跳转到扫描箱号界面
								Message msg = new Message();
								msg.what = 1;
								msg.obj =detail;
								handler.sendMessage(msg);
							}
							
						}
						
					}
					else
					{
						Message msg = new Message();
						msg.what =2;
						msg.obj =jsonSearch.optString("message");
						handler.sendMessage(msg);
					}
					
				} catch (Exception e) {
					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 3;
					msg.obj =e.getMessage();
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
				PackageAllDetail detail=(PackageAllDetail)msg.obj;
				// 绑定
				Intent intent = new Intent(PartnerPickerActivity.this,
						PartnerOrderActivity.class);
				intent.putExtra("packagecode", detail.getPackageCode());// 传递入库单号
				intent.putExtra("packageId", detail.getId());
				intent.putExtra("storeCode", detail.getStoredCode());
				startActivity(intent);
				break;
			case 2:
				
				Toaster.toaster(msg.obj.toString());
				break;
			case 3:
				tvmsg.setVisibility(View.VISIBLE);
				tvmsg.setText(msg.obj.toString());
				break;
			default:
				break;
			}
		}
	};


	
	public class SpecialAdapter extends SimpleAdapter{  
	    private int[] colors = new int[]{0xFFFFF, 0x300000FF,0x300000FF};  
	    private List<? extends Map<String, ?>> list;
	    private Map<String,?> Map = new HashMap<String,Object>();
	    @SuppressWarnings("unchecked")
		public SpecialAdapter(Context context, List<? extends Map<String, ?>> data,  
	            int resource, String[] from, int[] to) {  
	        super(context, data, resource, from, to);  
	    	this.list = data;
	        // TODO Auto-generated constructor stub  
	    }  
	    /* (non-Javadoc) 
	     * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup) 
	     */  
	    @SuppressWarnings("unchecked")
		@Override  
	    public View getView(int position, View convertView, ViewGroup parent) {  
	        // TODO Auto-generated method stub  
	        View view = super.getView(position, convertView, parent);
	        Iterator<? extends Map<String, ?>> it = list.iterator();
	        int colorPos = 0;
	        int index = 0;
	        while(it.hasNext()) {
	          Map = (java.util.Map<String, ?>) it.next();
	          Iterator<?> iter = Map.entrySet().iterator();
	       
		        while(iter.hasNext()){
		        	@SuppressWarnings("rawtypes")
					Map.Entry entry = (Map.Entry) iter.next();
		        	Object key = entry.getKey();
		        	Object val = entry.getValue();
		        	
		        		
		        }
		        /*if(num<totalnum){
		        	colorPos = 0;
		        }
		        else if(num==totalnum){
		        	colorPos = 1;
		        }
		        else{
		        	colorPos = 2;
		        }
		        if(index == position){
			        view.setBackgroundColor(colors[colorPos]); 
		        }*/
	        }

	        return view;  
	    }  
	} 
	
	


	@Override
	protected void onStart() {
		super.onStart();

	}

}
