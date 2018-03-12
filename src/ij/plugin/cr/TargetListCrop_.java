package ij.plugin.cr;

import java.util.ArrayList;

import ij.IJ;
import ij.ImagePlus;

public class TargetListCrop_{
	private String file;
	private ArrayList<ArrayList> crop_area;
	private ArrayList<Long> area_sizes;
	private long allowed_pixels;
	private long max_area_size = 1L;
	private ArrayList<ImagePlus> impS_list = new ArrayList<ImagePlus>();

	//xdiv is parameter for local crop out (dividing width) and rezigin paramter
	//so it is decided by allowed pixel and max area size.
	int Xdiv = 1;

	private String output_dir;

	public void setCropDesign(ArrayList<ArrayList> crop_area) {
		this.crop_area = crop_area;
	}

	public void setAreaSizes(ArrayList<Long> area_sizes) {
		this.area_sizes = area_sizes;
	}

	public void setAllowedPixel(long allowed_limit) {
		this.allowed_pixels = allowed_limit;
	}

	public void setMaxArea(long max_area) {
		this.max_area_size = max_area;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setOutput_dir(String output_dir) {
		this.output_dir = output_dir;
	}

	public double log2(double x) {
		return (Math.log(x)/Math.log(2d));
	}

	public void calcXdiv() {
		double div = log2((double)this.max_area_size) - log2((double)this.allowed_pixels);
		IJ.log(String.valueOf(div));
		div = Math.ceil(div / 2d);
		int xdiv = (int)Math.pow(2d, div);
		if(xdiv < 1) {
			xdiv = 1;
		}
		//IJ.log(String.valueOf(div));
		//IJ.log(String.valueOf((int)div));
		//IJ.log(String.valueOf((int)Math.pow(2d, div)));
		IJ.log(String.valueOf(xdiv)+" is current xdivide.");
		this.Xdiv = xdiv;
	}

	public void makeCropImps() {
		for(int i= 0; i<crop_area.size();i++) {
			TargetICrop_ cip = new TargetICrop_();
			cip.setFile(this.file);
			cip.setXdiv(this.Xdiv);
			cip.setCropAreaI(crop_area.get(i));
			cip.makeImps2Imp();

			ImpsSave_ isv = new ImpsSave_();
			isv.setOriginalFile(this.file);
			isv.setOutputDir(this.output_dir);
			isv.iterSave(cip.getResultImp(), i);

			System.gc();

			//impS_list.add(cip.getResultImp());
		}
		//this.impS_list = impS_list;
	}

	public  ArrayList<ImagePlus> getImpsAr(){
		return this.impS_list;
	}

	public int getXdiv() {
		return this.Xdiv;
	}

	public void run(String args) {
		this.max_area_size = (long)Math.pow(2d, 10d);
		this.allowed_pixels = (long)Math.pow(2d, 7d);
		calcXdiv();
	}
}
