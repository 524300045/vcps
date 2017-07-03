package com.wologic.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.wologic.R;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ProductDao;
import com.wologic.dao.WBaseInfoDao;
import com.wologic.domain.BarCode;
import com.wologic.domain.GoodsDto;
import com.wologic.domain.ItemData;
import com.wologic.domain.Product;

import com.wologic.util.Constant;
import com.wologic.util.CsvUtil;
import com.wologic.util.SimpleClient;
import com.wologic.util.Toaster;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsActivity extends Activity {

	private GoodsDao goodsDao;
	private ImageView imgdown;

//	private TextView tvdown, tvliulan, tvclear, tvimport;

	private LinearLayout tvdowntotal,tvliulantotal,tvcleartotal,tvimporttotal;
	
	private TextView tbBack;

	private ProductDao productDao;

	private Dialog loadingDialog;

	private WBaseInfoDao basicInfoDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
		tvdowntotal = (LinearLayout) findViewById(R.id.tvdowntotal);
		tvliulantotal = (LinearLayout) findViewById(R.id.tvliulantotal);
		tvcleartotal = (LinearLayout) findViewById(R.id.tvcleartotal);
		tvimporttotal = (LinearLayout) findViewById(R.id.tvimporttoal);
		imgdown = (ImageView) findViewById(R.id.imgdown);
		tbBack = (TextView) findViewById(R.id.tvback);
		loadingDialog();
		goodsDao = new GoodsDao();
		productDao = new ProductDao();
		basicInfoDao = new WBaseInfoDao();
		init();
	}

	private void loadingDialog() {
		loadingDialog = new Dialog(this, R.style.dialog_loading);
		loadingDialog.setContentView(R.layout.dialog_loading_2);
		TextView prompt = (TextView) loadingDialog.findViewById(R.id.prompt);
		prompt.setText("正在下载...");
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setCancelable(true);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (loadingDialog != null) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}

	private void init() {
		tvdowntotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				downdangan();
			}
		});
		tvliulantotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(GoodsActivity.this,
						GoodsDangAnActivity.class);
				startActivity(intent);
			}

		});

		tvcleartotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				goodsDao.deleteAllGoods();
				productDao.deleteAllProduct();
				Toast.makeText(GoodsActivity.this, "清除数据成功", 2000).show();
			}

		});

		tvimporttotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(GoodsActivity.this, OpenFileDialogActivity.class);  
				intent.putExtra("DefaultFilePath", Environment.getExternalStorageDirectory().getPath());  
				intent.putExtra("DefaultFileName", "");  
				intent.putExtra("Ext", ".csv");  
				startActivityForResult(intent,5);  
			}

		});

		imgdown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				downdangan();
			}
		});

		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}

	private  String getSuffix(String name)
	{
		    File file = new File(name);  
	        String fileName = file.getName();  
	        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1); 
	        return suffix;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       
     
            if (resultCode == OpenFileDialogActivity.RESULT_CODE)  
            {
            	//获取文件名
            	String FilePathName = data.getStringExtra("FilePathName");
            	String suffixname=getSuffix(FilePathName);
            	if(!suffixname.equals("csv"))
            	{
            		FilePathName+=".csv";
            	}
           
            	importbarcode(FilePathName);
            }
            
     
    }	
	/**
	 * 导入档案
	 */
	private void importbarcode(String path) {

		// 读取文件信息
		List<GoodsDto> goodsList=CsvUtil.getCSV(path);
		if (goodsList != null && goodsList.size() > 0) {
			for (GoodsDto item : goodsList) {
				// 先判断商品信息是否存在
				Product p = productDao.getProduct(item.getProductno());
				if (p == null) {
					// 添加商品信息
					Product p1 = new Product();
					p1.setProductno(item.getProductno());
					p1.setProductname(item.getProname());
					p1.setProinfo11(item.getDanjia());// 价格
					p1.setProinfo10(item.getChandi());// 产地
					p1.setProinfo9(item.getGuige());// 规格
					p1.setProinfo8(item.getKucun());
					p1.setProinfo7(item.getPingpai());
					productDao.insertProduct(p1);
				}
				if (!item.getBarcode().equals("")) {

					List<BarCode> barList = goodsDao
							.GetAlllistByProductAndBarCodeAndPkg(
									item.getProductno(), item.getBarcode(),
									item.getDanwei());
					// 判断条码信息是否存在
					if (barList == null || barList.size() <= 0) {
						// 不存在，添加
						BarCode barCode = new BarCode();
						barCode.setProductno(item.getProductno());
						barCode.setPkgbarcode(item.getBarcode());
						barCode.setPkgname(item.getDanwei());
						barCode.setPkgrate(1D);
						goodsDao.InsertGoods(barCode);
					}
				}

				if (!item.getSupbarcode1().equals("")) {
					List<BarCode> barList = goodsDao
							.GetAlllistByProductAndBarCodeAndPkg(
									item.getProductno(), item.getSupbarcode1(),
									item.getSupdanwei1());
					// 判断条码信息是否存在
					if (barList == null || barList.size() <= 0) {
						// 不存在，添加
						BarCode barCode = new BarCode();
						barCode.setProductno(item.getProductno());
						barCode.setPkgbarcode(item.getSupbarcode1());
						barCode.setPkgname(item.getSupdanwei1());
						if (item.getSupxishu1().equals("")) {
							barCode.setPkgrate(0d);
						} else {
							barCode.setPkgrate(Double.parseDouble(item
									.getSupxishu1()));
						}

						goodsDao.InsertGoods(barCode);
					}
				}

				if (!item.getSupbarcode2().equals("")) {
					List<BarCode> barList = goodsDao
							.GetAlllistByProductAndBarCodeAndPkg(
									item.getProductno(), item.getSupbarcode2(),
									item.getSupdanwei2());
					// 判断条码信息是否存在
					if (barList == null || barList.size() <= 0) {
						// 不存在，添加
						BarCode barCode = new BarCode();
						barCode.setProductno(item.getProductno());
						barCode.setPkgbarcode(item.getSupbarcode2());
						barCode.setPkgname(item.getSupdanwei2());
						if (item.getSupxishu1().equals("")) {
							barCode.setPkgrate(0d);
						} else {
							barCode.setPkgrate(Double.parseDouble(item
									.getSupxishu2()));
						}

						goodsDao.InsertGoods(barCode);
					}
				}
			}
		}
		Toaster.toaster("导入完成");
	}

	private void downdangan() {
		if (loadingDialog != null && !loadingDialog.isShowing())
			loadingDialog.show();

		final List<BarCode> goodsAllList = goodsDao.GetAlllist("");

		Thread mThread = new Thread(new Runnable() {
			int times = 1;//重试3次
			@Override
			public void run() {
				String result = null;
				String url = null;
				Map<String, Object> map = null;
				try {
					int page=1;
					boolean next=true;
					while(next)
					{			
						// 单号不存在,下载
						url = Constant.url
								+ "/services/0/products/ProductInfo";
						map = new HashMap<String, Object>();
						map.put("edit[page]", page);
						HttpClient client = SimpleClient.getHttpClient();
						result = com.wologic.util.SimpleClient.doGet(url,
								map);
						if(result == "error"){
							Toaster.toaster("服务器连接失败!请测试服务器连接!");
							return;
						}
						JSONObject json = new JSONObject(result);
						
						if(json.optString("result").equals("false"))	
						{
							next=false;
							break;
						}
						JSONObject json2 = new JSONObject(json.optString("result"));
						System.out.print(result);
						List<BarCode> barCodeList = new ArrayList<BarCode>();
						List<GoodsDto> goodsList = JSON.parseArray(
								json2.optString("data"), GoodsDto.class);
						if (goodsList != null && goodsList.size() > 0) {
							for (GoodsDto item : goodsList) {
								// 先判断商品信息是否存在
								Product p = productDao.getProduct(item
										.getProductno());
								if (p == null) {
									// 添加商品信息
									Product p1 = new Product();
									p1.setProductno(item.getProductno());
									p1.setProductname(item.getProname());
									p1.setProinfo11(item.getDanjia());// 价格
									p1.setProinfo10(item.getChandi());// 产地
									p1.setProinfo9(item.getGuige());// 规格
									p1.setProinfo8(item.getKucun());
									p1.setProinfo7(item.getPingpai());
									productDao.insertProduct(p1);
								}
								if (!item.getBarcode().equals("")) {

									List<BarCode> barList = goodsDao
											.GetAlllistByProductAndBarCodeAndPkg(
													item.getProductno(),
													item.getBarcode(),
													item.getDanwei());
									// 判断条码信息是否存在
									if (barList == null || barList.size() <= 0) {
										// 不存在，添加
										BarCode barCode = new BarCode();
										barCode.setProductno(item.getProductno());
										barCode.setPkgbarcode(item.getBarcode());
										barCode.setPkgname(item.getDanwei());
										barCode.setPkgrate(1D);
										goodsDao.InsertGoods(barCode);
									}
								}

								if (!item.getSupbarcode1().equals("")) {
									List<BarCode> barList = goodsDao
											.GetAlllistByProductAndBarCodeAndPkg(
													item.getProductno(),
													item.getSupbarcode1(),
													item.getSupdanwei1());
									// 判断条码信息是否存在
									if (barList == null || barList.size() <= 0) {
										// 不存在，添加
										BarCode barCode = new BarCode();
										barCode.setProductno(item.getProductno());
										barCode.setPkgbarcode(item.getSupbarcode1());
										barCode.setPkgname(item.getSupdanwei1());
										if (item.getSupxishu1().equals("")) {
											barCode.setPkgrate(0d);
										} else {
											barCode.setPkgrate(Double
													.parseDouble(item
															.getSupxishu1()));
										}

										goodsDao.InsertGoods(barCode);
									}
								}

								if (!item.getSupbarcode2().equals("")) {
									List<BarCode> barList = goodsDao
											.GetAlllistByProductAndBarCodeAndPkg(
													item.getProductno(),
													item.getSupbarcode2(),
													item.getSupdanwei2());
									// 判断条码信息是否存在
									if (barList == null || barList.size() <= 0) {
										// 不存在，添加
										BarCode barCode = new BarCode();
										barCode.setProductno(item.getProductno());
										barCode.setPkgbarcode(item.getSupbarcode2());
										barCode.setPkgname(item.getSupdanwei2());
										if (item.getSupxishu1().equals("")) {
											barCode.setPkgrate(0d);
										} else {
											barCode.setPkgrate(Double
													.parseDouble(item
															.getSupxishu2()));
										}

										goodsDao.InsertGoods(barCode);
									}
								}

							}
						}						
						page+=1;
						
					}//while
					
					basicInfoDao.UpdateBasicInfoTime("huopin");// 更新时间

					if (loadingDialog != null && loadingDialog.isShowing())
						loadingDialog.dismiss();
					
					// 下载完成
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "下载完成";
					handler.sendMessage(msg);

				} catch (Exception e) {
					if (loadingDialog != null && loadingDialog.isShowing())
						loadingDialog.dismiss();
//					String msg2 = result+map+url;
//					String msg1 = e.getMessage();
					System.out.print(e.getMessage());
					Message msg = new Message();
					
					msg.what = 2;
					msg.obj = "网络异常,正在第"+times+"重试中...";
					handler.sendMessage(msg);
					if(times++>=3){
						msg.obj = "下载失败!";
						handler.sendMessage(msg);
					}	
					else{
						this.run();
					}
				}

			}
		});
		mThread.start();
	}

	private boolean isExist(List<BarCode> list, String barCode) {
		for (BarCode item : list) {
			if (item.getPkgbarcode().equals(barCode)) {

				return true;
			}
		}
		return false;
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
}
