package cn.shanshuan.html.demo;

import java.util.List;

import cn.shanshuan.html.demo.entity.Picture;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;

/**
 *Created by Zifeng Wang 2016-6-29ÏÂÎç7:08:05
 */
public class MyApplication extends Application{
	public static MyApplication app;
	private String BigPath;
	public String getBigPath() {
		return BigPath;
	}

	public void setBigPath(String bigPath) {
		BigPath = bigPath;
	}
	private List<Picture> pics;
	
	public List<Picture> getPics() {
		return pics;
	}

	public void setPics(List<Picture> pics) {
		this.pics = pics;
	}

	public static MyApplication getApp(){
		return app;
	}
	@Override
	public void onCreate() {
		ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		app=this;
		super.onCreate();
	}

}
