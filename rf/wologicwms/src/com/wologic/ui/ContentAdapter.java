package com.wologic.ui;

import java.util.List;
import java.util.Map;

import com.wologic.R;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ContentAdapter extends BaseAdapter implements  OnClickListener {

    private static final String TAG = "ContentAdapter";
    private List<Map<String, Object>> mContentList;
    private LayoutInflater mInflater;
    private Callback mCallback;

    /**
     * �Զ���ӿڣ����ڻص���ť����¼���Activity
     * @author Ivan Xu
     * 2014-11-26
     */
    public interface Callback {
        public void click(View v);
    }

    public ContentAdapter(Context context, List<Map<String, Object>> contentList,
            Callback callback) {
        mContentList = contentList;
        mInflater = LayoutInflater.from(context);
        mCallback = callback;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount");
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "getItem");
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "getItemId");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView");
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listmendiandetail, null);
            holder = new ViewHolder();
            holder.tvmendiancode = (TextView) convertView
                    .findViewById(R.id.tvmendiancode);
            holder.tvmendian = (TextView) convertView
                    .findViewById(R.id.tvmendian);
            holder.tvjindu = (TextView) convertView
                    .findViewById(R.id.tvjindu);
            
            holder.button = (Button) convertView.findViewById(R.id.btnSure);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvmendiancode.setText(mContentList.get(position).get("storeCode").toString());
        holder.tvmendian.setText(mContentList.get(position).get("storeName").toString());
        holder.tvjindu.setText(mContentList.get(position).get("process").toString());
        
        holder.button.setOnClickListener(this);
        holder.button.setTag(position);
        return convertView;
    }

    public class ViewHolder {
        public TextView tvmendiancode;
        public TextView tvmendian;
        public TextView tvjindu;
        public Button button;
    }

    //��Ӧ��ť����¼�,�����Ӷ���ӿڣ�������View
    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }
}