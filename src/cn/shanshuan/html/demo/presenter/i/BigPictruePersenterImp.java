package cn.shanshuan.html.demo.presenter.i;

import cn.shanshuan.html.demo.entity.Picture;
import cn.shanshuan.html.demo.modle.i.IModel;
import cn.shanshuan.html.demo.modle.i.IModel.BigCallbcak;
import cn.shanshuan.html.demo.modle.i.ModleImp;
import cn.shanshuan.html.demo.view.i.IBigPictureView;

public class BigPictruePersenterImp implements IBigPictruePersenter{
	private IModel model;
	private IBigPictureView view;
	public BigPictruePersenterImp(IBigPictureView view){
		this.view=view;
		model=new ModleImp();
	}
	
	@Override
	public void loadBIgPictrue(String bigUrl) {
		model.findBigPictrue(new BigCallbcak() {
			
			@Override
			public void onloadBigPicture(Picture pic) {
				view.setBig(pic);
				view.showBig();
				
			}
		}, bigUrl);
	}

}
