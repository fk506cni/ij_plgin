package ij.plugin.cr;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class SmallImps_ {
	private ImagePlus[] imps;
	private ImagePlus[] s_imps;
	private int resize_rate;
	private boolean useAvg = true;
	private ImagePlus imp = new ImagePlus();
	private ImageProcessor ip;

	//https://imagej.nih.gov/ij/developer/api/constant-values.html#ij.process.ImageProcessor.BILINEAR
	private int interploation_mode = 1;

	Mscs_ ms = new Mscs_();

	public void setBigImps(ImagePlus[] imps) {
		this.imps = imps;
		this.s_imps = new ImagePlus[this.imps.length];
	}

	public void setRrate(int resize_rate) {
		this.resize_rate = resize_rate;
	}

	public void runResize() {
		for(int i=0; i < imps.length; i++) {
			imp = this.imps[i];
			ip = imp.getProcessor();
			int wid = ip.getWidth();
			int hei = ip.getHeight();

			//int new_wid = (wid + this.resize_rate -1) / this.resize_rate;
			//int new_hei = (hei + this.resize_rate -1) / this.resize_rate;
			int new_wid = wid / this.resize_rate;
			int new_hei = hei / this.resize_rate;
			String i_tag = String.valueOf(i);

			int[] ss = {new_wid, new_hei};




			//ip = ip.resize(new_wid, new_hei, useAvg);
			ip.setInterpolationMethod(this.interploation_mode);

			ip.setInterpolationMethod(1);
			//ip = ip.resize(64, 64, true);
			ip = ip.resize(new_wid, new_hei, useAvg);

			ms.ints2ijlog(ss);
			this.s_imps[i] = new ImagePlus();
			this.s_imps[i].setProcessor(ip);
			//this.s_imps[i].show();
		}
	}

	public ImagePlus Imp2resizeImp(ImagePlus imp) {

		ImageProcessor ip = imp.getProcessor();

		//if imp is null in this phase...?
		imp = null;

		int wid = ip.getWidth();
		int hei = ip.getHeight();
		int new_wid = wid / this.resize_rate;
		if(new_wid ==0) {
			IJ.log(String.valueOf(new_wid)+"::new_wid 0 ->1");
			new_wid =1;
		}
		int new_hei = hei / this.resize_rate;
		if(new_hei ==0) {
			IJ.log(String.valueOf(new_hei)+ "::new_hei 0 ->1");
			new_hei=1;
		}


		ip.setInterpolationMethod(this.interploation_mode);
		ip = ip.resize(new_wid, new_hei, useAvg);
		ImagePlus resultImp = new ImagePlus();
		resultImp.setProcessor(ip);

		//reducing memory consumption
		ip = null;
		return resultImp;
	}


	public ImagePlus[] getSmallImps() {
		return this.s_imps;
	}

	public void showSmallImps() {
		for(int i=0; i < this.s_imps.length; i++) {
			this.s_imps[i].show();
		}
	}

}
