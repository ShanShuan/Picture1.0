package cn.shanshuan.html.demo.activity;


import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.shanshuan.html.demo.MyApplication;
import cn.shanshuan.html.demo.R;
import cn.shanshuan.html.demo.entity.Picture;

public class SecondMainActivity extends FragmentActivity {
	private String BigPath;
	private ViewPager vp;
	private List<Picture> pics;
	private ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_main);
		vp=(ViewPager) findViewById(R.id.vp);
		Intent i = getIntent();
		BigPath = i.getStringExtra("BigPath");
		pics=MyApplication.getApp().getPics();
		Log.i("pics", pics.toString());
		vp.setAdapter(new MyPagerAdapter());
		vp.setCurrentItem(i.getIntExtra("position", -1));
	}
	
	public class MyPagerAdapter extends  PagerAdapter{
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Picture p=pics.get(position);
			View v=View.inflate(SecondMainActivity.this, R.layout.item_bigpicture, null);
			iv=(ImageView) v.findViewById(R.id.imageView1);
			
			String bigPath=p.getBigPath();
			Log.i("111", bigPath);
			String b="http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-"+bigPath+".jpg";
			DisplayImageOptions options=new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.loading1).showImageOnFail(R.drawable.nonetwork).showImageForEmptyUri(R.drawable.nonetwork)
			.cacheInMemory(true).cacheOnDisc(true).build();
			ImageLoader.getInstance().displayImage(b, iv, options,new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					Log.i("123", "开始下载");
					
				}
				
				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					Log.i("123", "下载失败");
					
				}
				
				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					Log.i("123", "下载完成");
					
				}
				
				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					Log.i("123", "下载取消");
					
				}
			});	
			container.addView(v);
			return v;
		}
		
		
		public MyPagerAdapter() {
			super();
		}

		@Override
		public int getCount() {
			return pics.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
	}
}
