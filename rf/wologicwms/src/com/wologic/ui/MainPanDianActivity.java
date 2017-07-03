package com.wologic.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wologic.R;
import com.wologic.dao.ItemDataDao;
import com.wologic.dao.RuKuDao;
import com.wologic.domain.WorkItem;
import com.wologic.util.Common;
import com.wologic.util.Toaster;

public class MainPanDianActivity extends Activity {

	private RuKuDao ruKuDao;
	private TextView tbBack;
	private EditText etrukucode;
	private ListView lv;
	private Button btnSure,btngen;
	private ItemDataDao itemDataDao;
	private SimpleAdapter adp;// ����������
	private List<Map<String, Object>> mapList;// ��������Դ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pandianmain);
		tbBack = (TextView) findViewById(R.id.tvback);
		etrukucode = (EditText) findViewById(R.id.etrukucode);
		btnSure = (Button) findViewById(R.id.btnSure);
		btngen=(Button) findViewById(R.id.btngen);//���ɵ���
		
		lv = (ListView) findViewById(R.id.lv);
		etrukucode.requestFocus();
		ruKuDao = new RuKuDao(MainPanDianActivity.this);
		itemDataDao = new ItemDataDao();
		getPdaInfo();
		initEvent();
	}

	@Override
	protected void onStart() {
		super.onStart();
		bindlist();
	}
	/**
	 * ��ȡPDA��Ϣ
	 */
	private void getPdaInfo(){
//		PdaInfo pda = new PdaInfo();
//		pda = pdainfoDao.getPdaInfo();
//		Common.deviceid = pda.getPdaName();
		//��Ϊ�ӱ����ļ���
		//String DeviceName = FileOperation.getDeviceName();
		/*if(DeviceName.indexOf("error")!=-1){//����error��Ϣ
			Toast.makeText(this, "��ȡ�豸��Ϣʧ��!", 1000)
			.show();
		}
		else if(DeviceName.indexOf("Wologic")!=-1){//Ĭ��ֵ
			Toast.makeText(this, "��δ�����豸����,��ǰ������!", 1000)
			.show();
		}
		Common.deviceid = DeviceName;*/
//		Toaster.toaster(DeviceName);
	}
	// ����ⵥ�б�
	private void bindlist() {
		List<WorkItem> list = new ArrayList<WorkItem>();
		list = ruKuDao.getWorkItem("", "w3");
		mapList = new ArrayList<Map<String, Object>>();
		for (WorkItem item : list) {
			Long count = itemDataDao.getTotalCount(item.getItemno());
			double num = itemDataDao.GetTotalNum(item.getItemno());

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemno", "[�̵�]" + item.getItemno());
			if (item.getItemstate().equals("s10")) {
				map.put("state", "��");
			} else {
				map.put("state", "��");
			}
			map.put("time", Common.getStringDate(item.getWorktime()));
			map.put("count", "[����]" + count);
			map.put("num", "[����]" + num);
			map.put("code", item.getItemno());
			mapList.add(map);
		}

		adp = new SimpleAdapter(this, mapList, R.layout.listitemruku,
				new String[] { "itemno", "state", "time", "count", "num","code" },
				new int[] { R.id.tvruku, R.id.tvstate, R.id.tvtime,
						R.id.tvcount, R.id.tvnum,R.id.tvcode });
		lv.setAdapter(adp);

	}
	/**
	 * ��֤��д�̵㵥�ŵĺϷ���
	 * @param itemNo
	 * @return
	 */
	private boolean validatepd(String itemNo){
		if((itemNo.indexOf("pd")>=0)&&itemNo.length()==16){
			return true;
		}
		else{
			return false;
		}
	}
	private void initEvent() {
		tbBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		
		// ȷ����ť
		btnSure.setOnClickListener(new OnClickListener() {

			//����Ҫ��֤���ŵĺϷ���
			@Override
			public void onClick(View arg0) {
				final String code = etrukucode.getText().toString()
						.trim();
				if (code.equals("")) {
					etrukucode.selectAll();
					Toaster.toaster("�������̵㵥��!");
					return ;
				}
				if(!validatepd(code)){
					Toaster.toaster("���Ų���Ҫ�����������뵥��!");
					return;
				}
				List<WorkItem> workList = ruKuDao.getWorkItem(code);
				if (workList != null && workList.size() > 0)
				{
					// ���Ŵ���,�ж�״̬
					if (!workList.get(0).getItemtype().equals("w3")) {
						// ҳ����ת
						etrukucode.selectAll();
						Toaster.toaster("��ǰ���ݲ����̵㵥");
					
					} else if (workList.get(0).getItemstate()
							.equals("s10")) {
						//Toaster.toaster( code + "�����");
						// ҳ����ת
						Intent intent = new Intent(MainPanDianActivity.this,
								PanDianGoodsActivity.class);
						intent.putExtra("itemno", code);// ������ⵥ��
						startActivity(intent);
						
					} else {
						// ҳ����ת
						Intent intent = new Intent(MainPanDianActivity.this,
								PanDianGoodsActivity.class);
						intent.putExtra("itemno", code);// ������ⵥ��
						startActivity(intent);
					}
				} else {
					//��������
					WorkItem workItem=new WorkItem();
					workItem.setItemno(code);
					workItem.setItemtype("w3");
					workItem.setItemstate("s5");
					
					ruKuDao.insertWorkItem(workItem);
					
					// ҳ����ת
					Intent intent = new Intent(MainPanDianActivity.this,
							PanDianGoodsActivity.class);
					intent.putExtra("itemno", code);// ������ⵥ��
					startActivity(intent);

				}
			}
		});

		btngen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String pandiancode="pd"+dateToString(new Date());
				etrukucode.setText(pandiancode);
			}
			
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				   //��ȡ��ǰѡ�����ⵥ��
				 TextView   tvcode= (TextView)arg1.findViewById(R.id.tvcode);//��Դ����    
					Intent intent = new Intent(
							MainPanDianActivity.this,
							OperationPanDianActivity.class);
					intent.putExtra("itemno", tvcode.getText());// ������ⵥ��
					startActivity(intent);
			}
		});

	}
	
	public static String dateToString(Date time){ 
	    SimpleDateFormat formatter; 
	    formatter = new SimpleDateFormat ("yyyyMMddHHmmss"); 
	    String ctime = formatter.format(time); 
	    return ctime; 
	} 

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				// ��
				Intent intent = new Intent(MainPanDianActivity.this,
						PanDianGoodsActivity.class);
				intent.putExtra("itemno", msg.obj.toString());// ������ⵥ��
				startActivity(intent);
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
