package cn.shanshuan.html.demo.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sfl=(SwipeRefreshLayout) findViewById(R.id.srl);
		gv=(GridView) findViewById(R.id.gridView1);
		q=Volley.newRequestQueue(this);
		ipm=new PictruePresenterImp(this,i);
		isUpdating=true;
		ipm.loadAllPictrue();
		isUpdating=false;
		sfl.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				if(isUpdating){
					return;
				}else{
					i++;
					ipm=new PictruePresenterImp(MainActivity.this,i);
					isUpdating=true;
					ipm.loadAllPictrue();
					isUpdating=false;
					sfl.setRefreshing(false);
				}
			}
		});
		gv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				return false;
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
				Log.i("12", BigPath);
				startActivity(i);
			}
		});
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
		
	}

	@Override
	public void show() {
		PictrueAdapter adapter=new PictrueAdapter(this, pics,q);
		gv.setAdapter(adapter);
		
	}

}
