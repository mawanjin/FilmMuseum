package com.example.filmmuseum;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.data.IndexItem;
import com.example.data.MagicFactory;

import java.util.List;

public class TestAdapter extends PagerAdapter {

	private List<IndexItem> indexItems;
	private Context mContext;

	public TestAdapter(Context cx) {
		mContext = cx;
	}

	public void change(List<IndexItem> _indexItems) {
        indexItems = _indexItems;
	}

	public int getCount() {
		return indexItems.size();
	}

	public boolean isViewFromObject(View view, Object obj) {
		return view == (View) obj;
	}

	private Bitmap bm;

	public Object instantiateItem(ViewGroup container, int position) {
		ImageView iv = new ImageView(mContext);
		try {
			iv.setImageBitmap(MagicFactory.getBitmap(indexItems.get(position).getImg()));
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
