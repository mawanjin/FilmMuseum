package com.example.filmmuseum;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TestAdapter extends PagerAdapter {

	private List<Integer> mPaths;
	private Context mContext;

	public TestAdapter(Context cx) {
		mContext = cx;
	}

	public void change(List<Integer> paths) {
		mPaths = paths;
	}

	public int getCount() {
		return mPaths.size();
	}

	public boolean isViewFromObject(View view, Object obj) {
		return view == (View) obj;
	}

	private Bitmap bm;

	public Object instantiateItem(ViewGroup container, int position) {
		ImageView iv = new ImageView(mContext);
		try {
			bm = BitmapFactory.decodeResource(mContext.getResources(),
					mPaths.get(position));// ‘ÿ»Îbitmap
			iv.setImageBitmap(bm);
		} catch (OutOfMemoryError e) {
			if(bm!=null && bm.isRecycled()== false)
			{
				bm=null;
				System.gc();
			}
		}
		
		// Drawable bitmapDrawable = mContext.getResources().getDrawable(
		// mPaths.get(position));
		// iv.setImageDrawable(bitmapDrawable);
		((ViewPager) container).addView(iv, 0);
		return iv;
	}
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
