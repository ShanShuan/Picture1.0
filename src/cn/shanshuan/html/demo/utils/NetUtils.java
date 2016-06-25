package cn.shanshuan.html.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
	

	    public static boolean haveNet(Context context) {
	    	
	    	ConnectivityManager manager=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
	    	if(manager==null){
	    		return false;
	    	}
	    	NetworkInfo info = manager.getActiveNetworkInfo();
	    	if(info==null||!info.isAvailable()){
	    		return false;
	    	}
	    	return true;
	    }

}
