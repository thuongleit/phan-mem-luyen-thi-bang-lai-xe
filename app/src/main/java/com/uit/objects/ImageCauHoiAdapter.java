package com.uit.objects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageCauHoiAdapter extends BaseAdapter {

	private Context mContext;

	public ImageCauHoiAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			// canh chinh chieu cao va chieu rong cho View, moi hinh anh dc cat
			// va chinh sua voi kich thuong thich hop
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));

			// canh chinh vi tri cho hinh, o giua
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			// canh chinh padding cho cac image
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	// references to our images
	private Integer[] mThumbIds = {

	};
}
