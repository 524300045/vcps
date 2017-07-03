package com.wologic.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wologic.R;
import com.wologic.control.CustomDialog;
import com.wologic.dao.GoodsDao;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.ParameterDao;
import com.wologic.dao.RuKuDao;
import com.wologic.domain.BarCode;
import com.wologic.domain.ItemData;
import com.wologic.domain.Parameter;
import com.wologic.domain.WorkItem;
import com.wologic.util.Common;
import com.wologic.util.Toaster;

public class PanDianGoodsActivity extends Activity implements OnItemClickListener {

	private EditText etbarcode, etnum;
	private ItemDataDao itemDataDao;
	
	private GoodsDao goodsDao;
	
	private String itemNo;// ��ⵥ��
	private TextView tvMsg, tvpinming, tvunit, tvprice, tvtotalscannum,
			tvtotalnum;
	private ListView lvnoend, lvend;
	private ImageView imgfind,imgscan;
	private Button btnSure;
	private TextView tbBack;

	List<Map<String, Object>> mapnoendList;// �����
	List<Map<String, Object>> mapendlist;// �����
	String productNo = "";// ��ǰɨ�����Ʒ��
	Dialog dialog;
	ListView mlistView;
	BaseAdapter adapter;

	List<ItemData> selectList;// ������ѡ���б�list

	private RuKuDao ruKuDao;
	
	private final static int SCANNIN_GREQUEST_CODE = 1;
	
	private MediaPlayer mediaPlayer;

	Parameter parameter7;

	Parameter parameter8;
	
	private ParameterDao parameterDao;
	
	private String lastBarCode="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandiangoods);
		etbarcode = (EditText) findViewById(R.id.etbarcode);
		btnSure = (Button) findViewById(R.id.btnSure);
		etnum = (EditText) findViewById(R.id.etnum);
		tvMsg = (TextView) findViewById(R.id.tvmsg);
		tvpinming = (TextView) findViewById(R.id.tvpinming);
		tvprice = (TextView) findViewById(R.id.tvprice);
		tvunit = (TextView) findViewById(R.id.tvunit);
		tvtotalscannum = (TextView) findViewById(R.id.tvtotalscannum);
		tvtotalnum = (TextView) findViewById(R.id.tvtotalnum);
		itemDataDao = new ItemDataDao();
		lvnoend = (ListView) findViewById(R.id.lvnoend);
		imgfind = (ImageView) findViewById(R.id.imgfind);
		tbBack = (TextView) findViewById(R.id.tvback);
		goodsDao=new GoodsDao();
		ruKuDao=new RuKuDao(PanDianGoodsActivity.this);
		
		imgscan=(ImageView) findViewById(R.id.imgscan);
		
		parameterDao = new ParameterDao();
		
		Intent intent = getIntent();
		if (intent != null) {
			itemNo = intent.getStringExtra("itemno");
		}
		initEvents();
		bindlist();
		
		List<WorkItem> workItemList = ruKuDao.getWorkItem(itemNo);
		if (workItemList != null && workItemList.size() > 0) {
			if (workItemList.get(0).getItemstate().equals("s10")) {
				etbarcode.setCursorVisible(false); // ����������еĹ�겻�ɼ�
				etbarcode.setFocusable(false); // �޽���
				etbarcode.setFocusableInTouchMode(false);
				
				etnum.setCursorVisible(false); // ����������еĹ�겻�ɼ�
				etnum.setFocusable(false); // �޽���
				etnum.setFocusableInTouchMode(false);
				
				lvnoend.setEnabled(false);
				btnSure.setEnabled(false);

			}
		}
		
		Parameter parameterThree = parameterDao.getParameterById(4);
		if (parameterThree != null
				&& parameterThree.getParaValue1().equals("1")) {
			imgscan.setVisibility(View.VISIBLE);// ������ʾ
		} else {
			imgscan.setVisibility(View.GONE);// ���ò���ʾ
		}

		parameter7 = parameterDao.getParameterById(7);
		parameter8 = parameterDao.getParameterById(8);
		
	}

	private void initEvents() {
		etbarcode.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_UP:
						tvMsg.setVisibility(View.GONE);
						tvMsg.setText("");
						productNo = "";
						String code = etbarcode.getText().toString()
								.trim();
						if (code.equals("")) {
							return true;
						}
						if(!lastBarCode.equals("")&&!lastBarCode.equals(code))
						{
							etnum.setText("");
						}
						lastBarCode=code;
						
						String barInfo = validateBarCodeLength(code);
						if (!barInfo.equals("")) {
							etbarcode.selectAll();
							tvMsg.setText(barInfo);
							return true;
						}
						barInfo = validateBarCodePos(code);
						if (!barInfo.equals("")) {
							etbarcode.selectAll();
							tvMsg.setText(barInfo);
							return true;
						} else {
							if (parameter8 != null) {
								if (parameter8.getParaValue1() != null
										&& !parameter8.getParaValue1().equals(
												"")
										&& parameter8.getParaValue2() != null
										&& !parameter8.getParaValue2()
												.toString().equals("")) {
									int startPos = Integer.valueOf(parameter8
											.getParaValue1());
									int endPos = Integer.valueOf(parameter8
											.getParaValue2());
									code = code.substring(startPos - 1,
											endPos - 1);
								}
							}
						}
						
						
						List<BarCode> barCodeList=goodsDao.GetAlllist(code);
						// ���ж��Ƿ��ܲ�ѯ����Ʒ
						
						if (barCodeList == null || barCodeList.size() == 0) {
							tvMsg.setVisibility(View.VISIBLE);
							tvMsg.setText("��ѯ������ǰ����");
							etbarcode.selectAll();
							mediaPlayer = MediaPlayer.create(
									PanDianGoodsActivity.this, R.raw.error);
							mediaPlayer.setVolume(1.0f, 1.0f);
							mediaPlayer.start();
							return true;// ��ѯ������Ʒ
						}

						if (barCodeList.size() == 1) {

							productNo = barCodeList.get(0).getProductno();// ��ȡ��Ʒ��

							tvpinming.setText(barCodeList.get(0)
									.getProductname());// Ʒ��
							tvprice.setText(barCodeList.get(0).getPrice().toString());// �۸�
							tvunit.setText(barCodeList.get(0).getPkgname());// ��λ
							/*etnum.setText(barCodeList.get(0).getPkgrate()
									.toString());// ����
							etnum.requestFocus();*/
							etbarcode.selectAll();
							if(etnum.getText().toString().trim().equals(""))
							{
								etnum.setText(barCodeList.get(0).getPkgrate()
										.toString());// ����
							}
							else
							{
								try {
								   Double	d=Double.parseDouble(etnum.getText().toString());
								   Double num=d+barCodeList.get(0).getPkgrate();
									etnum.setText(num.toString());
									
								} catch (NumberFormatException e) {
									
								}
								
							}
							etbarcode.selectAll();
							btnSure.performClick();
						}

						if (barCodeList.size() > 1) {
							// �������壬��ʾ
							selectList = new ArrayList<ItemData>();
							for(BarCode item:barCodeList)
							{
								ItemData itemdata=new ItemData();
								itemdata.setProductno(item.getProductno());
								itemdata.setProductname(item.getProductname());
								itemdata.setPkgbarcode(item.getPkgbarcode());
								itemdata.setPkgname(item.getPkgname());
								itemdata.setPkgrate(item.getPkgrate());
								itemdata.setInfo1(itemdata.getInfo1());
								if(item.getPrice().toString().equals(""))
								{
									itemdata.setPrice("");
								}
								else
								{
									itemdata.setPrice(item.getPrice().toString());
								}
								
								selectList.add(itemdata);
							}
							
							showDialog(selectList);
						}

					case KeyEvent.ACTION_DOWN:
						break;
					}
					return true;

				}
				return false;
			}
		});

		btnSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				tvMsg.setVisibility(View.GONE);
				tvMsg.setText("");
				final String code = etbarcode.getText().toString().trim();
				final String num = etnum.getText().toString().trim();
				tvMsg.setText("");
				if (code.equals("")) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("��Ʒ���벻��Ϊ��!");
					mediaPlayer = MediaPlayer.create(PanDianGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}
				if (num.equals("")) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("��Ʒ��������Ϊ��!");
					mediaPlayer = MediaPlayer.create(PanDianGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}
				if (!Common.isNumber(num)) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("��������������!");
					mediaPlayer = MediaPlayer.create(PanDianGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}
				if (productNo.equals("")) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("��δѡ����Ʒ!");
					mediaPlayer = MediaPlayer.create(PanDianGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}
				
				  Double dnum=Double.parseDouble(num);
					if(dnum<=0)
					{
						tvMsg.setVisibility(View.VISIBLE);
						tvMsg.setText("�����������0!");
						mediaPlayer = MediaPlayer.create(PanDianGoodsActivity.this,
								R.raw.error);
						mediaPlayer.setVolume(1.0f, 1.0f);
						mediaPlayer.start();
						return;
					}
			
                //���ж��Ƿ���ڣ���������ڣ����������ڵĻ�����
				List<ItemData> itemList=itemDataDao.GetItemDataByProductNo(itemNo, productNo);
				if(itemList!=null&&itemList.size()>0)
				{
//					itemDataDao.updateWorkAmount(itemNo, productNo,
//							Double.parseDouble(num));
					itemDataDao.updateWorkAmountEnd(itemNo, productNo,
							Double.parseDouble(num));
				}
				else
				{
					
					ItemData itemData=new ItemData();
					itemData.setItemno(itemNo);
					itemData.setProductno(productNo);
					itemData.setPlanamount(0d);
					itemData.setCompleteamount(0d);
					itemData.setWork_amount(Double.parseDouble(num));
					itemData.setUserid(Common.userID);
					itemData.setDeviceid(Common.deviceid);
					itemData.setState("00");
					itemData.setInfo1("");// ��λ
					
					itemDataDao.insertItemData(itemData);
				}
				
				productNo = "";
				tvpinming.setText("");
				tvprice.setText("");//
				tvunit.setText("");//
				etnum.setText("");//
				etbarcode.setText("");
				bindlist();
				etbarcode.requestFocus();
			}
		});

		imgfind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(PanDianGoodsActivity.this,
						GoodsDangAnActivity.class);
				startActivity(intent);
			}

		});

		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		imgscan.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PanDianGoodsActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}});
		
		lvnoend.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				//��ȡproductno
				TextView tvproductname = (TextView) arg0.findViewById(R.id.tbname);
				TextView tvproductno = (TextView) arg1.findViewById(R.id.tvproductno);
				String productno=tvproductno.getText().toString();
				String productname=tvproductname.getText().toString();
				//��ȡ����
				TextView tvNum = (TextView) arg1.findViewById(R.id.tvworknm);
				dialog(productno,tvNum.getText().toString(),productname);
			}
		});
		
	}
	
	// ���������ĵ�ǰ��Ʒ����Ŀ������Ϊ0��ʱ��ɾ���ò�Ʒ
			private void dialog(final String productNo,String num,String ProductName) {
				final CustomDialog dialog = new CustomDialog(PanDianGoodsActivity.this);
				TextView textview = (TextView) dialog.findViewById(R.id.title);
				textview.setVisibility(View.VISIBLE);
				textview.setText(ProductName);
				dialog.setTitle(textview);
				final EditText editText = (EditText) dialog.getEditText();// ������CustomDialog��ʵ��
				editText.setText(num);
				dialog.setOnPositiveListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// dosomething youself
						
						if (editText.getText().equals("")) {
							Toaster.toaster("��������Ϊ�� !");
							mediaPlayer = MediaPlayer.create(PanDianGoodsActivity.this,
									R.raw.error);
							mediaPlayer.setVolume(1.0f, 1.0f);
							mediaPlayer.start();
							return;
						}
						if (!Common.isNumber(editText.getText().toString())) {
							Toaster.toaster("��������������!");
							return;
						}
						
						Double dnum = Double.parseDouble(editText.getText().toString());
						if (dnum < 0) 
						{
							Toaster.toaster("����������ڵ���0!");
							mediaPlayer = MediaPlayer.create(PanDianGoodsActivity.this,
									R.raw.error);
							mediaPlayer.setVolume(1.0f, 1.0f);
							mediaPlayer.start();
							return;
						}
						else if(dnum == 0)//��������Ϊ0ɾ����ǰ����
						{
							//�������ж�,���û�ȷ���Ƿ�Ҫɾ������
							showDialog2("��ǰ����Ϊ0,�����ӵ���ɾ���˲�Ʒ��ȷ��ɾ��[��Ʒ-" + productNo + "]��?",productNo);
						}
						
						itemDataDao.updateWorkAmount(itemNo, productNo, dnum);
						dialog.dismiss();
						bindlist();
					}
				});
				dialog.setOnNegativeListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				//��ʾɨ�赽������
				etbarcode.setText(bundle.getString("result"));

			}
			break;
		}
    }	
	
	
	/**
	 * ��֤������Ϣ
	 * 
	 * @param barCode
	 * @return
	 */
	private String validateBarCodeLength(String barCode) {
		if (parameter7 != null) {
			if (parameter7.getParaValue1() != null
					&& !parameter7.getParaValue1().equals("")
					&& parameter7.getParaValue2() != null
					&& !parameter7.getParaValue2().toString().equals("")) {
				int startLength = Integer.valueOf(parameter7.getParaValue1());
				int endLength = Integer.valueOf(parameter7.getParaValue2());
				if (barCode.length() < startLength) {
					return "����λ������";
				}

				if (barCode.length() > endLength) {
					return "����λ������";
				}
			}
		}

		return "";
	}

	private String validateBarCodePos(String barCode) {
		if (parameter8 != null) {
			if (parameter8.getParaValue1() != null
					&& !parameter8.getParaValue1().equals("")
					&& parameter8.getParaValue2() != null
					&& !parameter8.getParaValue2().toString().equals("")) {
				int startPos = Integer.valueOf(parameter8.getParaValue1());
				int endPos = Integer.valueOf(parameter8.getParaValue2());
				if (barCode.length() < startPos) {
					return "��ȡλ�ô���";
				}
				if (barCode.length() < endPos) {
					return "��ȡλ�ô���";
				}
			}
		}
		return "";
	}
	

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	private void bindlist() {

		Double totalscanNum = itemDataDao.GetTotalScanNum(itemNo);//��ɨ�裬��ʾ�Ѿ�ɨ�����������
		Double totalNum = itemDataDao.GetTotalNum(itemNo);//��ʾ�Ѿ�ɨ���������
//		tvtotalscannum.setText(totalscanNum.toString());
//		tvtotalnum.setText(totalNum.toString());
		tvtotalscannum.setText(totalNum.toString());// ɨ����������
		tvtotalnum.setText(totalscanNum.toString());// ����������
		// �����
		List<ItemData> noendlist = new ArrayList<ItemData>();
		noendlist = itemDataDao.GetItemDataNoEnd(itemNo);
		mapnoendList = new ArrayList<Map<String, Object>>();
		for (ItemData item : noendlist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productname", item.getProductname());
//			map.put("sectionid", item.getInfo1());
			map.put("worknum", item.getWorkamount());
//			map.put("num", item.getPlanamount());
			map.put("productno", item.getProductno());
			mapnoendList.add(map);
		}
//		SimpleAdapter adp = new SimpleAdapter(this, mapnoendList,
//				R.layout.listitemrukugoods, new String[] { "productname",
//						"sectionid", "worknum", "num","productno" }, new int[] {
//						R.id.tbname, R.id.tvsectionid, R.id.tvworknm,
//						R.id.tvnum,R.id.tvproductno });
		SimpleAdapter adp = new SimpleAdapter(this, mapnoendList,
				R.layout.listitemrukugoods, new String[] { "productname",
						 "worknum","productno" }, new int[] {
						R.id.tbname, R.id.tvworknm,
						R.id.tvproductno });
		lvnoend.setAdapter(adp);
	}
	
	/**
	 * ����Ϊ0ɾ������ȷ��
	 * @param title
	 */
	public void showDialog2(String title,final String productNo){
		new AlertDialog.Builder(PanDianGoodsActivity.this)
		.setTitle("ɾ����Ʒ")
		//���öԻ���ı���
		.setMessage(title)
		//������ʾ����Ϣ
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {// ���ȷ����ť
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ȷ����ť����Ӧ�¼�,ִ��ɾ��
								itemDataDao.updateWorkAmount1(itemNo, productNo);
								bindlist();
							}
						})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ��Ӧ�¼�
								bindlist();
							}

						}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
	}

	public void showDialog(List<ItemData> list) {
		AlertDialog.Builder builder = new Builder(this,
				AlertDialog.THEME_HOLO_LIGHT);
		// builder.setTitle(title);
		final LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.dialog_item, null);
		adapter = new MyAdapter(this, list);
		mlistView = (ListView) v.findViewById(R.id.dialoglist);
		mlistView.setAdapter(adapter);
		mlistView.setOnItemClickListener(this);
		mlistView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {

			}
		});
		// mlistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		/*
		 * Window window = dialog.getWindow(); WindowManager.LayoutParams lp =
		 * window.getAttributes(); lp.alpha = 0.9f; window.setAttributes(lp);
		 */

		LayoutInflater layoutInflater = LayoutInflater
				.from(PanDianGoodsActivity.this);
		View mTitleView = layoutInflater.inflate(R.layout.dialogtitle, null);

		builder.setCustomTitle(mTitleView);

		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				for (int i = 0, j = mlistView.getCount(); i < j; i++) {
					View child = mlistView.getChildAt(i);
					RadioButton rdoBtn = (RadioButton) child
							.findViewById(R.id.radio_btn);
					if (rdoBtn.isChecked()) {
						productNo = selectList.get(i).getProductno();// ��ȡѡ�����Ʒ��
						//etnum.setText(selectList.get(i).getPkgrate().toString());
						tvprice.setText(selectList.get(i).getPrice());// �۸�
						tvunit.setText(selectList.get(i).getPkgname());// ��λ
						tvpinming.setText(selectList.get(i).getProductname());// ����
						if(etnum.getText().toString().trim().equals(""))
						{
							etnum.setText(selectList.get(i).getPkgrate().toString());
						}
						else
						{
							try {
							   Double	d=Double.parseDouble(etnum.getText().toString());
							   Double num=d+selectList.get(i).getPkgrate();
								etnum.setText(num.toString());
								
							} catch (NumberFormatException e) {
								
							}
						}
					}
					
					etbarcode.selectAll();
				}
				if (productNo.equals("")) {
					Toast.makeText(PanDianGoodsActivity.this, "��δѡ����Ʒ", 2000)
							.show();
					canCloseDialog(dialog, false);// ���رնԻ���
				} else {
					canCloseDialog(dialog, true);
				}
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				canCloseDialog(dialog, true);// �رնԻ���
				etnum.setText("");
				tvprice.setText("");// �۸�
				tvunit.setText("");// ��λ
				tvpinming.setText("");// ����
				etbarcode.setText("");
			}
		});

		builder.setView(v);

		dialog = builder.create();
		builder.show();// .getWindow().requestFeature(Window.FEATURE_NO_TITLE)
	}

	// �ؼ�����������
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
			List<ItemData> list;

			private Context context;
			HashMap<String, Boolean> states = new HashMap<String, Boolean>();// ���ڼ�¼ÿ��RadioButton��״̬������ֻ֤��ѡһ��

			private LayoutInflater mInflater;

			public MyAdapter(Context context, List<ItemData> list) {

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
				holder.tvname.setText(list.get(position).getProductname()
						+ list.get(position).getPkgrate()
						+ list.get(position).getPkgname());

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

				// ��RadioButton��ѡ��ʱ������״̬��¼��States�У�����������RadioButton��״̬ʹ���ǲ���ѡ��
				holder.rdBtn.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {

						// ���ã�ȷ�����ֻ��һ�ѡ��
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
		public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
			// TODO Auto-generated method stub
			/*
			 * Toast.makeText(LoginActivity.this, "3", 1000).show(); switch
			 * (arg0.getId()) { case R.id.dialoglist:
			 * Toast.makeText(LoginActivity.this, list1.get(arg2), 2000).show();
			 * break; default: break; }
			 */
			View child = mlistView.getChildAt(position);
			RadioButton rdoBtn = (RadioButton) child
					.findViewById(R.id.radio_btn);
			rdoBtn.setChecked(true);
		}
	}

