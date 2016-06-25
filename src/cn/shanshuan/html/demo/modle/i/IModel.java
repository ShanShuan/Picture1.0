package cn.shanshuan.html.demo.modle.i;

import java.util.List;

import cn.shanshuan.html.demo.entity.Picture;

public interface IModel {
	public void findAllPictrue(Callback callback,int pager);
	public void findBigPictrue(BigCallbcak callback,String bigPath);
	public interface BigCallbcak{
		public void onloadBigPicture(Picture pic);
	}
	public interface Callback{
		public void onloadPicture(List<Picture> pics);
	};
}
