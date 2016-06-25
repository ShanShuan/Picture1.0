package cn.shanshuan.html.demo.presenter.i;

import java.util.List;

import cn.shanshuan.html.demo.entity.Picture;
import cn.shanshuan.html.demo.modle.i.IModel;
import cn.shanshuan.html.demo.modle.i.IModel.BigCallbcak;
import cn.shanshuan.html.demo.modle.i.IModel.Callback;
import cn.shanshuan.html.demo.modle.i.ModleImp;
import cn.shanshuan.html.demo.view.i.IPictureView;

public class PictruePresenterImp implements IPictruePersenter{
	private IPictureView view;
	private IModel model;
	private int pager;
	public PictruePresenterImp(IPictureView view,int pager){
		this.view=view;
		this.model=new ModleImp();
		this.pager=pager;
	}
	@Override
	public void loadAllPictrue() {
		model.findAllPictrue(new Callback() {
			
			@Override
			public void onloadPicture(List<Picture> pics) {
				view.setData(pics);
				view.show();
				
			}
		},pager);
	}


}
