package cn.shanshuan.html.demo.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;
import cn.shanshuan.html.demo.MyApplication;
import cn.shanshuan.html.demo.R;
import cn.shanshuan.html.demo.R.id;
import cn.shanshuan.html.demo.R.layout;
import cn.shanshuan.html.demo.R.menu;
import cn.shanshuan.html.demo.adapter.PictrueAdapter;
import cn.shanshuan.html.demo.entity.Picture;
import cn.shanshuan.html.demo.presenter.i.PictruePresenterImp;
import cn.shanshuan.html.demo.utils.NetUtils;
import cn.shanshuan.html.demo.view.i.IPictureView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity implements IPictureView{
	private List<Picture> pics;
	private GridView gv;
	private RequestQueue q;
	private SwipeRefreshLayout sfl;
	private PictruePresenterImp ipm;
	private boolean isUpdating;
	private int i=1;
	private Animation animation;
	private LayoutAnimationController c;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.arg1) {
			case 1:
				Toast.makeText(MainActivity.this, "从网上获取图片数据失败，请重新启动程序", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sfl=(SwipeRefreshLayout) findViewById(R.id.srl);
		gv=(GridView) findViewById(R.id.gridView1);
		
		animation=AnimationUtils.loadAnimation(this, R.anim.item_animation);
		 c=new LayoutAnimationController(animation);
		c.setDelay(0.5f);
		c.setOrder(LayoutAnimationController.ORDER_RANDOM);
		gv.setLayoutAnimation(c);
		
		q=Volley.newRequestQueue(this);
		ipm=new PictruePresenterImp(this,i);
		isUpdating=true;
		ipm.loadAllPictrue();
		sfl.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				if(isUpdating){
					return;
				}else{
					isUpdating=true;
					i++;
					ipm=new PictruePresenterImp(MainActivity.this,i);
					ipm.loadAllPictrue();
					
				}
			}
		});

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Picture p=pics.get(position);
				String BigPath=p.getBigPath();				
				Intent i=new Intent(MainActivity.this,SecondMainActivity.class);
				i.putExtra("BigPath", BigPath);
				i.putExtra("position", position);
				startActivity(i);
			}
		});
		}

	 @Override
	protected void onRestart() {
		 gv.setLayoutAnimation(c);
		super.onRestart();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void setData(List<Picture> pics) {
		this.pics=pics;
		MyApplication.getApp().setPics(pics);
		sfl.setRefreshing(false);
	}

	@Override
	public void show() {
		PictrueAdapter adapter=new PictrueAdapter(this, pics,q);
		gv.setAdapter(adapter);
		isUpdating=false;
		gv.setLayoutAnimation(c);
	}

}
