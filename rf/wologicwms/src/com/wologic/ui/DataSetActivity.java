package com.wologic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.ParameterDao;
import com.wologic.dao.ProductDao;
import com.wologic.dao.RuKuDao;
import com.wologic.domain.BarCode;
import com.wologic.domain.GoodsDto;
import com.wologic.domain.Parameter;
import com.wologic.domain.Product;
import com.wologic.util.Common;
import com.wologic.util.Constant;
import com.wologic.util.SimpleClient;
import com.wologic.util.Toaster;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DataSetActivity extends Activity {

	private TextView tbBack;
	
	private Button btnSure,btnclearbaisc,btnupdate,btnclearbuss;
	
	private EditText etday;
	
	private ParameterDao parameterDao;
	
	private RuKuDao ruKuDao;
	private ItemDataDao itemDataDao;
	
	private GoodsDao goodsDao;
	
	private ProductDao productDao;
	
	private Dialog loadingDialog;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dataset);
		tbBack = (TextView) findViewById(R.id.tvback);
		btnSure=(Button)findViewById(R.id.btnSure);
		btnclearbaisc=(Button)findViewById(R.id.btnclearbaisc);
		btnupdate=(Button)findViewById(R.id.btnupdate);
		btnclearbuss=(Button)findViewById(R.id.btnclearbuss);
		etday=(EditText)findViewById(R.id.etday);
		etday.setText("");
		parameterDao = new ParameterDao();
		ruKuDao=new RuKuDao();
		itemDataDao=new ItemDataDao();
		goodsDao=new GoodsDao();
		productDao=new ProductDao();
		loadingDialog();
		Parameter parameter = parameterDao.getParameterById(6);
		if (parameter != null) {
			etday.setText(parameter.getParaValue1());
		}
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnSure.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				final String num = etday.getText().toString().trim();
				if (!Common.isInteger(num)) {
					Toaster.toaster("数量请整数!");
					return;
				}
				Double dnum = Double.parseDouble(num);
				if (dnum <= 0) {
					Toaster.toaster("数量必须大于0!");
					return;
				}
				Parameter p = new Parameter();
				p.setParameterId(6);
				p.setParaValue1(dnum.toString());
				parameterDao.updateParameter(p);
				Toast.makeText(DataSetActivity.this, "保存设置成功", 1000)
						.show();
			}
		});
		
		btnclearbaisc.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				goodsDao.deleteAllGoods();
				productDao.deleteAllProduct();
				Toast.makeText(DataSetActivity.this, "清除基础数据成功", 2000)
				.show();
			}});
		
		btnupdate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				goodsDao.deleteAllGoods();
				productDao.deleteAllProduct();
				downdangan();
			}});
		
		btnclearbuss.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				ruKuDao.deleteAll();
				itemDataDao.deleteAll();
				Toast.makeText(DataSetActivity.this, "清除业务数据成功", 2000)
				.show();
			}});
		
    }
	

	private void downdangan()
	{
		if (loadingDialog != null && !loadingDialog.isShowing())
			loadingDialog.show();
		
		final List<BarCode> goodsAllList=goodsDao.GetAlllist("");
		
		Thread mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
						// 单号不存在,下载
						String url = Constant.url
								+ "/services/0/products/ProductInfo";
						Map<String, Object> map = new HashMap<String, Object>();
						HttpClient client =SimpleClient.getHttpClient();
						String result = com.wologic.util.SimpleClient
								.doGet(url, map);
						JSONObject json = new JSONObject(result);
						JSONObject json2 = new JSONObject(json.optString("result"));
					 	System.out.print(result);
						 List<BarCode>  barCodeList=new ArrayList<BarCode> ();
						 List<GoodsDto> goodsList=JSON.parseArray(json2.optString("data"), GoodsDto.class);
						 if(goodsList!=null&&goodsList.size()>0)
						 {
							 for(GoodsDto item:goodsList)
							 {
								 //先判断商品信息是否存在
								 Product p=productDao.getProduct(item.getProductno());
								 if(p==null)
								 {
									 //添加商品信息
									 Product p1=new Product();
									 p1.setProductno(item.getProductno());
									 p1.setProductname(item.getProname());
									 p1.setProinfo11(item.getDanjia());//价格
									 p1.setProinfo10(item.getChandi());//产地
									 p1.setProinfo9(item.getGuige());//规格
									 p1.setProinfo8(item.getKucun());
									 p1.setProinfo7(item.getPingpai());
									 productDao.insertProduct(p1);
								 }
								if(!item.getBarcode().equals(""))
								{
									
									List<BarCode> barList=goodsDao.GetAlllistByProductAndBarCodeAndPkg(item.getProductno(), item.getBarcode(), item.getDanwei());
									 //判断条码信息是否存在
									if(barList==null||barList.size()<=0)
									{
										//不存在，添加
										 BarCode barCode=new BarCode();
										 barCode.setProductno(item.getProductno());
										 barCode.setPkgbarcode(item.getBarcode());
										 barCode.setPkgname(item.getDanwei());
										 barCode.setPkgrate(1D);
										 goodsDao.InsertGoods(barCode);
									}
								}
								
								if(!item.getSupbarcode1().equals(""))
								{
									List<BarCode> barList=goodsDao.GetAlllistByProductAndBarCodeAndPkg(item.getProductno(), item.getSupbarcode1(), item.getSupdanwei1());
									 //判断条码信息是否存在
									if(barList==null||barList.size()<=0)
									{
										//不存在，添加
										 BarCode barCode=new BarCode();
										 barCode.setProductno(item.getProductno());
										 barCode.setPkgbarcode(item.getSupbarcode1());
										 barCode.setPkgname(item.getSupdanwei1());
										 if(item.getSupxishu1().equals(""))
										 {
											 barCode.setPkgrate(0d);
										 }
										 else
										 {
											 barCode.setPkgrate(Double.parseDouble(item.getSupxishu1()));
										 }
										
										 goodsDao.InsertGoods(barCode);
									}
								}
								
								if(!item.getSupbarcode2().equals(""))
								{
									List<BarCode> barList=goodsDao.GetAlllistByProductAndBarCodeAndPkg(item.getProductno(), item.getSupbarcode2(), item.getSupdanwei2());
									 //判断条码信息是否存在
									if(barList==null||barList.size()<=0)
									{
										//不存在，添加
										 BarCode barCode=new BarCode();
										 barCode.setProductno(item.getProductno());
										 barCode.setPkgbarcode(item.getSupbarcode2());
										 barCode.setPkgname(item.getSupdanwei2());
										 if(item.getSupxishu1().equals(""))
										 {
											 barCode.setPkgrate(0d);
										 }
										 else
										 {
											 barCode.setPkgrate(Double.parseDouble(item.getSupxishu2()));
										 }
										
										 goodsDao.InsertGoods(barCode);
									}
								}
							
							 }
						 }
						 
						 if (loadingDialog != null && loadingDialog.isShowing())
								loadingDialog.dismiss();
						 
                            //下载完成
							Message msg = new Message();
							msg.what = 2;
							msg.obj = "下载完成";
							handler.sendMessage(msg);
		
				} catch (Exception e) {
					if (loadingDialog != null && loadingDialog.isShowing())
						loadingDialog.dismiss();
					
					System.out.print(e.getMessage());
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "网络异常";
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
				// 绑定
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
	
	private void loadingDialog() {
		loadingDialog = new Dialog(this, R.style.dialog_loading);
		loadingDialog.setContentView(R.layout.dialog_loading_2);
		TextView prompt = (TextView) loadingDialog.findViewById(R.id.prompt);
		prompt.setText("正在下载...");
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setCancelable(true);
	}
}
