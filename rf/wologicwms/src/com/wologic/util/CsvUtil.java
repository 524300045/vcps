package com.wologic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.os.Environment;

import com.wologic.domain.DetailItem;
import com.wologic.domain.GoodsDto;
import com.wologic.domain.ItemData;

public class CsvUtil {

	public static final String mComma = ",";
	private static StringBuilder mStringBuilder = null;
	private static String mFileName = null;

	/**
	 * csvPath : csv路径
	 */
	public static List<GoodsDto> getCSV(String csvPath) {
		List<GoodsDto> goodsList = new ArrayList<GoodsDto>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvPath));
			String line = "";
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("barcode") > 0) {
					continue;
				}
				ArrayList<String> lists = new ArrayList<String>();
				/*StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					String str = st.nextToken();
					lists.add(str);
				}*/
				
				String[] arr=line.split(",");
				for(String str:arr)
				{
					lists.add(str);
				}

				GoodsDto mSeminarInfo = new GoodsDto();
				if (lists.size() >= 15) {
					mSeminarInfo.setProductno(lists.get(0));
					mSeminarInfo.setProname(lists.get(1));
					mSeminarInfo.setBarcode(lists.get(2));
					mSeminarInfo.setDanwei(lists.get(3));
					mSeminarInfo.setSupdanwei1(lists.get(4));
					mSeminarInfo.setSupxishu1(lists.get(5));
					mSeminarInfo.setSupbarcode1(lists.get(6));
					mSeminarInfo.setSupdanwei2(lists.get(7));
					mSeminarInfo.setSupxishu2(lists.get(8));
					mSeminarInfo.setSupbarcode2(lists.get(9));
					mSeminarInfo.setDanjia(lists.get(10));
					mSeminarInfo.setGuige(lists.get(11));
					mSeminarInfo.setChandi(lists.get(12));
					mSeminarInfo.setKucun(lists.get(13));
					mSeminarInfo.setPingpai(lists.get(14));
					goodsList.add(mSeminarInfo);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return goodsList;
	}
	
	/**
	 * csvPath : csv路径
	 */
	public static List<DetailItem> getImportWorkItemCSV(String csvPath) {
		List<DetailItem> detailsList = new ArrayList<DetailItem>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvPath));
			String line = "";
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (line.indexOf("barcode") > 0) {
					continue;
				}
				ArrayList<String> lists = new ArrayList<String>();
				String[] arr=line.split(",");
				//StringTokenizer st = new StringTokenizer(line, ",");
				/*while (st.hasMoreTokens()) {
					String str = st.nextToken();
					System.out.print(str);
					lists.add(str);
				}*/
				for(String str:arr)
				{
					lists.add(str);
				}

				DetailItem mSeminarInfo = new DetailItem();
				if (lists.size() >=7) {
					mSeminarInfo.setRukuno(lists.get(0));
					mSeminarInfo.setBarcode(lists.get(1));
					mSeminarInfo.setProductno(lists.get(2));
					if(lists.get(3).equals(""))
					{
						mSeminarInfo.setProductqty(0d);
					}
					else
					{
						mSeminarInfo.setProductqty(Double.valueOf(lists.get(3)));
					}
					mSeminarInfo.setSectionid(lists.get(4));
					mSeminarInfo.setSizeid(lists.get(5));
					mSeminarInfo.setItemtype(lists.get(6));
					detailsList.add(mSeminarInfo);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return detailsList;
	}
	
	

	public static void open(String filename) {
		String folderName = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			if (path != null) {
				folderName = path + "/CSV/";
			}
		}

		File fileRobo = new File(folderName);
		if (!fileRobo.exists()) {
			fileRobo.mkdir();
		}
		mFileName = folderName + filename;
		mStringBuilder = new StringBuilder();
		mStringBuilder.append("itemno"); // 单号
		mStringBuilder.append(mComma);
		mStringBuilder.append("itemtype"); // 类型
		mStringBuilder.append(mComma);
		mStringBuilder.append("productno"); // 商品号

		mStringBuilder.append(mComma);
		mStringBuilder.append("plan_amount"); // 计划数量

		mStringBuilder.append(mComma);
		mStringBuilder.append("work_amount"); // 计划数量

		mStringBuilder.append(mComma);
		mStringBuilder.append("deviceid"); // 设备ID

		mStringBuilder.append(mComma);
		mStringBuilder.append("Info1"); // 货位

		mStringBuilder.append("\n");
	}

	public static void writeCsv(List<ItemData> list, String type) {
		for (ItemData item : list) {
			mStringBuilder.append(item.getItemno());
			mStringBuilder.append(mComma);
			mStringBuilder.append(type);
			mStringBuilder.append(mComma);
			mStringBuilder.append(item.getProductno());
			mStringBuilder.append(mComma);
			mStringBuilder.append(item.getPlanamount());
			mStringBuilder.append(mComma);
			mStringBuilder.append(item.getWork_amount());
			mStringBuilder.append(mComma);
			mStringBuilder.append(item.getDeviceid());
			mStringBuilder.append(mComma);
			mStringBuilder.append(item.getInfo1());
			mStringBuilder.append("\n");
		}

	}

	public static void flush() {
		if (mFileName != null) {
			try {
				File file = new File(mFileName);
				FileOutputStream fos = new FileOutputStream(file, false);
				fos.write(mStringBuilder.toString().getBytes());
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("You should call open() before flush()");
		}
	}


}
