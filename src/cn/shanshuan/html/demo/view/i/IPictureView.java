package cn.shanshuan.html.demo.view.i;

import java.util.List;

import cn.shanshuan.html.demo.entity.Picture;

public interface IPictureView {
	public void setData(List<Picture> pics);
	public void show();
}
