package cn.shanshuan.html.demo;

import java.util.HashMap;
import java.util.List;

import cn.shanshuan.html.demo.entity.Picture;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 *Created by Zifeng Wang 2016-6-29ÏÂÎç7:08:05
 */
public class MyApplication extends Application{
	public static MyApplication app;
	private String BigPath;
	private HashMap<Integer, Integer> soundMap=new HashMap<Integer, Integer>();
	private boolean haveSound;
	private SoundPool pool;
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
		pool=new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		soundMap.put(1, pool.load(getApplicationContext(), R.raw.flush, 0));
		super.onCreate();
	}
	public void playSound(){
		if(!haveSound){
			pool.play(soundMap.get(1), 1, 1, 0, 0, 1);
			haveSound=true;
		}else{
			haveSound=false;
		}
	}
}
