package cn.shanshuan.html.demo.activity;


import java.io.IOException;
import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.shanshuan.html.demo.MyApplication;
import cn.shanshuan.html.demo.R;
import cn.shanshuan.html.demo.entity.Picture;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class SecondMainActivity extends FragmentActivity {
	private String BigPath;
	private ViewPager vp;
	private List<Picture> pics;
	private ImageView iv;
	private MyPagerAdapter adpter;
	private boolean show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_main);
		vp=(ViewPager) findViewById(R.id.vp);
		Intent i = getIntent();
		BigPath = i.getStringExtra("BigPath");
		pics=MyApplication.getApp().getPics();
		Log.i("pics", pics.toString());
		adpter=new MyPagerAdapter();
		vp.setAdapter(adpter);
		vp.setCurrentItem(i.getIntExtra("position", -1));
		
	}
	
	public class MyPagerAdapter extends  PagerAdapter{
			
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Picture p=pics.get(position);
			View v=View.inflate(SecondMainActivity.this, R.layout.item_bigpicture, null);
			final ImageButton btn=(ImageButton) v.findViewById(R.id.btn_show_controller);
			iv=(ImageView) v.findViewById(R.id.imageView1);
			final LinearLayout ll=(LinearLayout) v.findViewById(R.id.ll_bigPictrue_controller);
			final TextView tvSet=(TextView) v.findViewById(R.id.tv_set_wall_pager);
			final TextView tvSave=(TextView) v.findViewById(R.id.tv_save_pager);
			String bigPath=p.getBigPath();
			Log.i("111", bigPath);
			final String b="http://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-"+bigPath+".jpg";
			DisplayImageOptions options=new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.loading2).showImageOnFail(R.drawable.nonetwork).showImageForEmptyUri(R.drawable.nourl)
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
				public void onLoadingComplete(String arg0, View arg1, final Bitmap arg2) {
			
					Log.i("123", "下载完成");
					btn.setVisibility(View.VISIBLE);
					
					ObjectAnimator animator4= ObjectAnimator.ofFloat(btn, "btn",ll.getWidth()/2,0);
					animator4.setDuration(2000);
					animator4.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float f = (Float) animation.getAnimatedValue();
							btn.setTranslationX(f);
						}
					});
					animator4.start();
					
					ObjectAnimator animator= ObjectAnimator.ofFloat(btn, "btn", 0,3,1);
					animator.setDuration(2000);
					animator.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float f = (Float) animation.getAnimatedValue();
							btn.setScaleX(f);
							btn.setScaleY(f);
						}
					});
					animator.start();
					btn.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if(show){
								ObjectAnimator animator= ObjectAnimator.ofFloat(ll, "ll", 1,1);
								animator.setDuration(1000);
								animator.addUpdateListener(new AnimatorUpdateListener() {
									
									@Override
									public void onAnimationUpdate(ValueAnimator animation) {
										float f = (Float) animation.getAnimatedValue();
										ll.setAlpha(f);
									}
								});
								animator.start();
								ObjectAnimator animator1= ObjectAnimator.ofFloat(ll, "ll", 0,ll.getWidth());
								animator1.setDuration(1000);
								animator1.addUpdateListener(new AnimatorUpdateListener() {
									
									@Override
									public void onAnimationUpdate(ValueAnimator animation) {
										float f = (Float) animation.getAnimatedValue();
										ll.setTranslationX(f);
									}
								});
								animator1.start();
								
								
								show=false;
								
							}else{
								ll.setVisibility(View.VISIBLE);
								ObjectAnimator animator= ObjectAnimator.ofFloat(ll, "ll", 0,1);
								animator.setDuration(1000);
								animator.addUpdateListener(new AnimatorUpdateListener() {
									
									@Override
									public void onAnimationUpdate(ValueAnimator animation) {
										float f = (Float) animation.getAnimatedValue();
										ll.setAlpha(f);
									}
								});
								animator.start();
								ObjectAnimator animator1= ObjectAnimator.ofFloat(ll, "ll", ll.getWidth(),0);
								animator1.setDuration(1000);
								animator1.addUpdateListener(new AnimatorUpdateListener() {
									
									@Override
									public void onAnimationUpdate(ValueAnimator animation) {
										float f = (Float) animation.getAnimatedValue();
										ll.setTranslationX(f);
									}
								});
								animator1.start();
								show=true;
							}
						}
					});
					
					tvSave.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							MediaStore.Images.Media.insertImage(getContentResolver(), arg2, b, "高清大图");
							Toast.makeText(SecondMainActivity.this, "保存到图库成库", Toast.LENGTH_SHORT).show();
						}
					});
					tvSet.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							WallpaperManager manager=WallpaperManager.getInstance(SecondMainActivity.this);
							try {
								manager.setBitmap(arg2);
							} catch (IOException e) {
								Toast.makeText(SecondMainActivity.this, "设置壁纸失败", Toast.LENGTH_SHORT).show();
								e.printStackTrace();
							}
							Toast.makeText(SecondMainActivity.this, "设置壁纸成功", Toast.LENGTH_SHORT).show();
							
						}
					});
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
