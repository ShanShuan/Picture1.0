package cn.shanshuan.html.demo.activity;


import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import cn.shanshuan.html.demo.R;
import cn.shanshuan.html.demo.entity.Picture;
import cn.shanshuan.html.demo.presenter.i.BigPictruePersenterImp;
import cn.shanshuan.html.demo.view.i.IBigPictureView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class SecondMainActivity extends Activity implements IBigPictureView {
	private ImageView iv2;
	private Picture p;
	private TextView tv;
	private String url;
	private String BigPath;
	private String b;
	private BigPictruePersenterImp bt;
	private Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_main);
		iv2=(ImageView) findViewById(R.id.imageView);
		tv=(TextView) findViewById(R.id.tv_save_path);
		Intent i = getIntent();
		 BigPath = i.getStringExtra("BigPath");
		 Log.i("123", BigPath);
		 b="https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-"+BigPath+".jpg";
		 bt=new  BigPictruePersenterImp(this,BigPath);
		 bt.loadBIgPictrue();
		 iv2.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder builder=new Builder(SecondMainActivity.this);
				builder.setTitle("提示信息").setMessage("你确定要保存图片到图库？").setPositiveButton("取消", null)
				.setNegativeButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, b, "高清大图");
						Toast.makeText(SecondMainActivity.this, "保存到图库成库", Toast.LENGTH_SHORT).show();
					}
				}).setNeutralButton("设置次图片为壁纸", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						WallpaperManager manager=WallpaperManager.getInstance(SecondMainActivity.this);
						try {
							manager.setBitmap(bitmap);
						} catch (IOException e) {
							Toast.makeText(SecondMainActivity.this, "设置壁纸失败", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
						Toast.makeText(SecondMainActivity.this, "设置壁纸成功", Toast.LENGTH_SHORT).show();
					}
				})
				.create().show();
				return false;
			}
		});
	}




	@Override
	protected void onPause() {
		bitmap=null;
		super.onPause();
	}


	public void showBig() {
//		Glide.with(this).load(p.getBigPath()).into(iv2);
//		Log.i("111", p.getBigPath());
		RequestQueue q = Volley.newRequestQueue(this);
		ImageRequest ir=new ImageRequest(p.getBigPath(), new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap bitmap) {
				iv2.setImageBitmap(bitmap);
				SecondMainActivity.this.bitmap=bitmap;
				
			}
		}, 0, 0, ScaleType.CENTER_CROP, Config.ARGB_8888, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				
			}
		});
		q.add(ir);
	}


@Override
protected void onDestroy() {
	bitmap=null;
	super.onDestroy();
}




	@Override
	public void setBig(Picture p) {
		this.p=p;
	}



}
