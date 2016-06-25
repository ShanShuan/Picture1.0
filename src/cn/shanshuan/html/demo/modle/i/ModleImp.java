package cn.shanshuan.html.demo.modle.i;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.shanshuan.html.demo.entity.Picture;
import cn.shanshuan.html.demo.utils.NetUtils;

import android.os.AsyncTask;
import android.util.Log;

public class ModleImp implements IModel{

	@Override
	public void findAllPictrue(final Callback callback,final int pager) {
		AsyncTask<String, String, List<Picture>> task=new AsyncTask<String, String, List<Picture>>(){

			@Override
			protected List<Picture> doInBackground(String... params) {
				try {
					Document d = Jsoup.connect("https://alpha.wallhaven.cc/latest?page="+pager).get();
					Element thumbs = d.getElementById("thumbs");
					Elements sextions = thumbs.getElementsByClass("thumb-listing-page");
					List<Picture> pisc=new ArrayList<Picture>();
					for (int i = 0; i < sextions.size(); i++) {
						Element si = sextions.get(i);
						Elements lis = si.getElementsByTag("li");
						for (int j = 0; j < lis.size(); j++) {
							Element li = lis.get(j);
							Picture p=new Picture();
							String src = li.select("img").attr("data-src");
							Log.i("777", src);
							String Bigpath=src.substring(src.lastIndexOf("th-")+3,src.lastIndexOf(".jpg"));
							Log.i("888", Bigpath);
							p.setBigPath(Bigpath);
							p.setUrl(src);
							pisc.add(p);
					}
					}
					return pisc;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				return null;
			}
			@Override
			protected void onPostExecute(List<Picture> result) {
				callback.onloadPicture(result);
			}
			
		};
		task.execute();
		
	}

	@Override
	public void findBigPictrue(final BigCallbcak callback,final String bigPath) {
		AsyncTask<String, String, Picture> task=new AsyncTask<String, String, Picture>(){

			@Override
			protected Picture doInBackground(String... params) {
				try {
					Document d = Jsoup.connect("https://alpha.wallhaven.cc/wallpaper/"+bigPath).get();
					
					String bigUrl ="http:"+ d.getElementById("wallpaper").attr("src");
					Log.i("1222", bigUrl);
					Picture p=new Picture();
					p.setBigPath(bigUrl);
					return p;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				return null;
			}
			@Override
			protected void onPostExecute(Picture result) {
				callback.onloadBigPicture(result);
			}
			
		};
		task.execute();
		
	}

	

}
