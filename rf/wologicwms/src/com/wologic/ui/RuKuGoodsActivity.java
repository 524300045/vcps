package com.wologic.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.wologic.ui.ChuKuGoodsActivity.SpecialAdapter;

import com.wologic.util.Common;
import com.wologic.util.Toaster;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class RuKuGoodsActivity extends Activity implements OnItemClickListener {

	private EditText etbarcode, etnum;
	private ItemDataDao itemDataDao;
	private String itemNo;// ��ⵥ��
	private TextView tvMsg, tvpinming, tvunit, tvprice, tvtotalscannum,
			tvtotalnum;
	private ListView lvnoend, lvend;
	private ImageView imgfind, imgscan;
	private Button btnSure;
	private TextView tbBack;
	private ParameterDao parameterDao;
	private RuKuDao ruKuDao;
	private GoodsDao goodsDao;
	List<Map<String, Object>> mapnoendList;// �����
	List<Map<String, Object>> mapendlist;// �����
	String productNo = "";// ��ǰɨ�����Ʒ��
	Dialog dialog;
	ListView mlistView;
	BaseAdapter adapter;

	private final static int SCANNIN_GREQUEST_CODE = 1;

	List<ItemData> selectList;// ������ѡ���б�list

	private MediaPlayer mediaPlayer;

	Parameter parameter7;

	Parameter parameter8;

	private String lastBarCode = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rukugoods);
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
		ruKuDao = new RuKuDao(RuKuGoodsActivity.this);
		goodsDao=new GoodsDao();
		parameterDao = new ParameterDao();
		lvnoend = (ListView) findViewById(R.id.lvnoend);
		imgfind = (ImageView) findViewById(R.id.imgfind);
		imgscan = (ImageView) findViewById(R.id.imgscan);
		tbBack = (TextView) findViewById(R.id.tvback);
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
						String code = etbarcode.getText().toString().trim();
						if (code.equals("")) {
							return true;
						}

						if (!lastBarCode.equals("")
								&& !lastBarCode.equals(code)) {
							etnum.setText("");
						}
						lastBarCode = code;

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
									RuKuGoodsActivity.this, R.raw.error);
							mediaPlayer.setVolume(1.0f, 1.0f);
							mediaPlayer.start();
							return true;// ��ѯ������Ʒ
						}
						// ���ж��Ƿ��ܲ�ѯ����Ʒ
						List<ItemData> itemDataList = itemDataDao
								.GetItemDataByBarCode(itemNo, code);
						if (itemDataList == null || itemDataList.size() == 0) {
							tvMsg.setVisibility(View.VISIBLE);
							tvMsg.setText("�ò�Ʒ���ڼƻ���֮��!");
							etbarcode.selectAll();
							mediaPlayer = MediaPlayer.create(
									RuKuGoodsActivity.this, R.raw.error);
							mediaPlayer.setVolume(1.0f, 1.0f);
							mediaPlayer.start();
							return true;// ��ѯ������Ʒ
						}

						if (itemDataList.size() == 1) {

							productNo = itemDataList.get(0).getProductno();// ��ȡ��Ʒ��

							tvpinming.setText(itemDataList.get(0)
									.getProductname());// Ʒ��
							tvprice.setText(itemDataList.get(0).getPrice());// �۸�
							tvunit.setText(itemDataList.get(0).getPkgname());// ��λ
							if (etnum.getText().toString().trim().equals("")) {
								etnum.setText(itemDataList.get(0).getPkgrate()
										.toString());// ����
							} else {
								try {
									Double d = Double.parseDouble(etnum
											.getText().toString());
									Double num = d
											+ itemDataList.get(0).getPkgrate();
									etnum.setText(num.toString());

								} catch (NumberFormatException e) {

								}
							}
							etbarcode.selectAll();
							if(!etnum.getText().toString().equals("")){//��ֹ�ֻ��ఴenter�������һ��ȷ����ť����¼�
								btnSure.performClick();
							}
							new RuKuDao().updateWorkItemStatus2(itemNo);
							// etnum.requestFocus();
						}

						if (itemDataList.size() > 1) {
							// �������壬��ʾ
							selectList = new ArrayList<ItemData>();
							selectList = itemDataList;
							new RuKuDao().updateWorkItemStatus2(itemNo);
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
					mediaPlayer = MediaPlayer.create(RuKuGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}
				if (num.equals("")) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("��Ʒ��������Ϊ��!");
					mediaPlayer = MediaPlayer.create(RuKuGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}
				if (!Common.isNumber(num)) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("��������������!");
					return;
				}
				if (productNo.equals("")) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("��δѡ����Ʒ!");
					mediaPlayer = MediaPlayer.create(RuKuGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}

				Double dnum = Double.parseDouble(num);
				if (dnum <= 0) {
					tvMsg.setVisibility(View.VISIBLE);
					tvMsg.setText("�����������0!");
					mediaPlayer = MediaPlayer.create(RuKuGoodsActivity.this,
							R.raw.error);
					mediaPlayer.setVolume(1.0f, 1.0f);
					mediaPlayer.start();
					return;
				}

				List<ItemData> itemDataList = itemDataDao
						.GetItemDataByProductNo(itemNo, productNo);

				if (itemDataList.get(0).getWork_amount() + dnum >= itemDataList
						.get(0).getPlanamount()) {
					itemDataDao.updateWorkAmountEnd(itemNo, productNo,
							Double.parseDouble(num));
				} else {
//					itemDataDao.updateWorkAmount(itemNo, productNo,
//							Double.parseDouble(num));
					itemDataDao.updateWorkAmountEnd(itemNo, productNo,
							Double.parseDouble(num));
				}

				productNo = "";
				tvpinming.setText("");
				tvprice.setText("");//
				tvunit.setText("");//
				etnum.setText("");//
				etbarcode.setText("");
				bindlist();
				//����״̬Ϊ���ڲ���
				new RuKuDao().updateWorkItemStatus2(itemNo);
				etbarcode.requestFocus();
			}
		});

		imgfind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RuKuGoodsActivity.this,
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

		imgscan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(RuKuGoodsActivity.this,
						MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				new RuKuDao().updateWorkItemStatus2(itemNo);
			}
		});
	
		lvnoend.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				//��ȡproductno
				TextView tvproductno = (TextView) arg1.findViewById(R.id.tvproductno);
				TextView tvproductname = (TextView) arg0.findViewById(R.id.tbname);
				String productno=tvproductno.getText().toString();
				//��ȡ����
				String productname=tvproductname.getText().toString();

				TextView tvNum = (TextView) arg1.findViewById(R.id.tvworknm);
				dialog(productno,tvNum.getText().toString(),productname);
			}
		});
	}

	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// ��ʾɨ�赽������
				etbarcode.setText(bundle.getString("result"));

			}
			break;
		}
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	private void bindlist() {
		int saomiaocount = 0;
		Double totalscanNum = itemDataDao.GetTotalScanNum(itemNo);//��ɨ�裬��ʾ�Ѿ�ɨ�����������
		Double totalNum = itemDataDao.GetTotalNum(itemNo);//��ʾ�Ѿ�ɨ���������
//		tvtotalscannum.setText(totalscanNum.toString());
//		tvtotalnum.setText(totalNum.toString());
//		tvtotalscannum.setText(totalNum.toString());// ɨ����������
		tvtotalnum.setText(totalscanNum.toString());// ����������

		// �����
		List<ItemData> noendlist = new ArrayList<ItemData>();
		noendlist = itemDataDao.GetItemDataNoEnd(itemNo);
		mapnoendList = new ArrayList<Map<String, Object>>();
		int index = 0;
		int rows = (noendlist.size()-1);
		for (ItemData item : noendlist) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productname", item.getProductname());
			map.put("sectionid", item.getInfo1());
			map.put("worknum", item.getWorkamount());
			if(item.getWorkamount()>0){
				saomiaocount++;
			}
			map.put("num", item.getPlanamount());
			map.put("productno", item.getProductno());
			if(item.getWorkamount()>=item.getPlanamount()){
				map.put("index",rows--);
				mapnoendList.add(mapnoendList.size(), map);
			}
			else{
				map.put("index", index++);
				mapnoendList.add(0,map);
			}
		}
		tvtotalscannum.setText(Double.toString(saomiaocount));// ɨ����������
		SpecialAdapter adp = new SpecialAdapter(this, mapnoendList,
				R.layout.listitemrukugoods, new String[] { "productname",
						"sectionid", "worknum", "num","productno" }, new int[] {
						R.id.tbname, R.id.tvsectionid, R.id.tvworknm,
						R.id.tvnum,R.id.tvproductno });
		lvnoend.setAdapter(adp);
	}
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
	        int index = 0;//��¼��������position��Ӧ
	        int colorPos = 0;
	        while(it.hasNext()) {
	          Map = (java.util.Map<String, ?>) it.next();
	          Iterator<?> iter = Map.entrySet().iterator();
	        	Double num = 0.0,totalnum = 0.0;
		        while(iter.hasNext()){
		        	@SuppressWarnings("rawtypes")
					Map.Entry entry = (Map.Entry) iter.next();
		        	Object key = entry.getKey();
		        	Object val = entry.getValue();
		        	if(key == "worknum"){
		        		num = Double.parseDouble(val.toString());
		        	}
		        	if(key=="num"){
		        		totalnum =Double.parseDouble(val.toString());
		        	}  	
		        	if(key == "index"){
		        		index = Integer.parseInt(val.toString());
		        	}
		        }
		        if(num<totalnum){
		        	colorPos = 0;
		        }
		        else if(num==totalnum){
		        	colorPos = 1;
		        }
		        else{
		        	colorPos = 2;
		        }
		        if(index == position){
		        	index++;
			        view.setBackgroundColor(colors[colorPos]); 
		        }
	        }
//	        colorPos = position%3;
	        return view;  
	    }  
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
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				View child = mlistView.getChildAt((int)arg3);
				RadioButton rdoBtn = (RadioButton) child
						.findViewById(R.id.radio_btn);
				rdoBtn.setChecked(true);
			}
		});
		// mlistView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		/*
		 * Window window = dialog.getWindow(); WindowManager.LayoutParams lp =
		 * window.getAttributes(); lp.alpha = 0.9f; window.setAttributes(lp);
		 */

		LayoutInflater layoutInflater = LayoutInflater
				.from(RuKuGoodsActivity.this);
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
						if (etnum.getText().toString().trim().equals("")) {
							etnum.setText(selectList.get(i).getPkgrate()
									.toString());
						} else {
							try {
								Double d = Double.parseDouble(etnum.getText()
										.toString());
								Double num = d + selectList.get(i).getPkgrate();
								etnum.setText(num.toString());

							} catch (NumberFormatException e) {

							}

						}

						tvprice.setText(selectList.get(i).getPrice());// �۸�
						tvunit.setText(selectList.get(i).getPkgname());// ��λ
						tvpinming.setText(selectList.get(i).getProductname());// ����
						etbarcode.selectAll();
					}
				}
				if (productNo.equals("")) {
					Toast.makeText(RuKuGoodsActivity.this, "��δѡ����Ʒ", 2000)
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
				etbarcode.selectAll();
			}
		});

		builder.setView(v);

		dialog = builder.create();
		builder.show();// .getWindow().requestFeature(Window.FEATURE_NO_TITLE)
	}

	/**
	 * ����Ϊ0ɾ������ȷ��
	 * @param title
	 */
	public void showDialog2(String title,final String productNo){
		new AlertDialog.Builder(RuKuGoodsActivity.this)
		.setTitle("ɾ����Ʒ")
		//���öԻ���ı���
		.setMessage(title)
		//������ʾ����Ϣ
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {// ���ȷ����ť
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ȷ����ť����Ӧ�¼�,ִ��ɾ��
								itemDataDao.updateWorkAmount1(itemNo, productNo);
//								bindlist();
							}
						})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ��Ӧ�¼�
//								bindlist();
							}

						}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
	}
	// ����,���ĵ�ǰ��Ʒ������
	private void dialog(final String productNo,String num,String ProductName) {
		final CustomDialog dialog = new CustomDialog(RuKuGoodsActivity.this);
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
					mediaPlayer = MediaPlayer.create(RuKuGoodsActivity.this,
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
					mediaPlayer = MediaPlayer.create(RuKuGoodsActivity.this,
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		/*
		 * Toast.makeText(LoginActivity.this, "3", 1000).show(); switch
		 * (arg0.getId()) { case R.id.dialoglist:
		 * Toast.makeText(LoginActivity.this, list1.get(arg2), 2000).show();
		 * break; default: break; }
		 */

	}
}
