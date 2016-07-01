package cn.shanshuan.html.demo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import cn.shanshuan.html.demo.R;
import cn.shanshuan.html.demo.entity.Picture;
import cn.shanshuan.html.demo.utils.NetUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class PictrueAdapter extends BaseAdapter {
	private Context context;
	private List<Picture> pics;
	private RequestQueue queue;
	private Picture p;
	
	public PictrueAdapter(Context context, List<Picture> pics,RequestQueue queue) {
		super();
		this.context = context;
		this.pics = pics;
		this.queue=queue;
		
	}

	@Override
	public int getCount() {
		if(pics==null){
			Handler handler=new Handler();
			Message msg=new Message();
			msg.arg1=1;
			handler.sendMessage(msg);
			return 0;
		}
		return pics.size();
	}

	@Override
	public Object getItem(int position) {
		return pics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHodler  hodler= null;
		  p=pics.get(position);
		if(convertView==null){
			convertView=View.inflate(context, R.layout.item_picture, null);
			hodler=new ViewHodler();
			hodler.iv=(ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(hodler);
		}
		hodler=(ViewHodler) convertView.getTag();
		final ImageView iv = hodler.iv;
		
		ImageLoader loader=new ImageLoader(queue, new mCache());
		ImageListener listenner = ImageLoader.getImageListener(iv, R.drawable.ic_launcher, R.drawable.nonetwork);
		loader.get(p.getUrl(), listenner);
		return convertView;
	}
	public class mCache implements ImageCache{
		LruCache<String, Bitmap> cache;
		public mCache(){
			int maxSize=10*1024*1024;
			cache=new LruCache<String, Bitmap>(maxSize){
				@Override
				protected int sizeOf(String key, Bitmap value) {
					return value.getRowBytes()*value.getHeight();
				}
			};
		}
		
		@Override
		public Bitmap getBitmap(String arg0) {
			return cache.get(arg0);
		}

		@Override
		public void putBitmap(String arg0, Bitmap arg1) {
			cache.put(arg0, arg1);
		}
		
	}
	public class ViewHodler{
		ImageView iv;
	}
}
