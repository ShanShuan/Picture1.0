package cn.shanshuan.html.demo.entity;

public class Picture {
 private String url;
 private String BigPath;
 private String BigUrl;
 

public Picture(String url, String bigPath, String bigUrl) {
	super();
	this.url = url;
	BigPath = bigPath;
	BigUrl = bigUrl;
}

public String getBigUrl() {
	return BigUrl;
}

public void setBigUrl(String bigUrl) {
	BigUrl = bigUrl;
}

public Picture(String url, String bigPath) {
	super();
	this.url = url;
	BigPath = bigPath;
}

public String getBigPath() {
	return BigPath;
}

public void setBigPath(String bigPath) {
	BigPath = bigPath;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public Picture(String url) {
	super();
	this.url = url;
}

public Picture() {
	super();
}

@Override
public String toString() {
	return "Picture [url=" + url + "]";
}

}
