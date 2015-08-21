package com.uit.Functions;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uit.R;
import com.uit.Functions.TwoColorAdapter.ViewHolder;

public class LuatListAdapter extends BaseAdapter {
	Activity context;
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> contents = new ArrayList<String>();

	public LuatListAdapter(Activity context, ArrayList<String> names,
			ArrayList<String> contents) {
		super();
		this.context = context;
		this.names = names;
		this.contents = contents;
	}

	public int getCount() {
		return names.size();
	}

	public Object getItem(int position) {
		return names.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.activity_hocluat_item, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView
					.findViewById(R.id.luat_txtName);
			holder.txtContent = (TextView) convertView
					.findViewById(R.id.luat_txtNoidung);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(names.size() != 0){
			holder.txtName.setText(names.get(position));
		}
		if(contents.size() != 0){
			holder.txtContent.setText(contents.get(position));
		}
		
		return convertView;

		// View v = null;
		// String inflater = Context.LAYOUT_INFLATER_SERVICE;
		// LayoutInflater li = (LayoutInflater)
		// context.getSystemService(inflater);
		// v = li.inflate(R.layout.activity_hocluat_item, null);
		// TextView txtName = (TextView) v.findViewById(R.id.luat_txtInfo);
		// TextView txtContent = (TextView) v.findViewById(R.id.luat_txtName);
		// txtName.setText(names.get(position));
		// txtContent.setText(contents.get(position));
		// v.setTag(txtName);
		// v.setTag(txtContent);
		// return v;
	}
}
