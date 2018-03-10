package ij.plugin.cr;

import java.util.ArrayList;

import ij.IJ;

public class CropDesign_ {
	//ここのWとHの処理も後でlongに変えておく
	private ArrayList<ArrayList> trunked_areas;
	private ArrayList<ArrayList> crop_areas;
	private int additional_margin = 32;
	private int sqlen_size = 32;
	private int over_rate = 1;
	private int min_size = 1024;
	private int[] original_ImageSize = new int[2];
	private ArrayList<Long> area_sizes = new ArrayList<Long>();
	private long max_area_size = 0L;

	private int triming4resize = 16;

	Mscs_ ms = new Mscs_();

	public void setArea(ArrayList<ArrayList> areas) {
		this.trunked_areas = areas;
	}

	public void setMinSize(int min_size) {
		this.min_size = min_size;
	}

	public void setOriginalSize(int[] original_size) {
		this.original_ImageSize = original_size;
	}

	public void setAdditionalMargin(int add) {
		this.additional_margin = add;
	}

	public void setSqLen(int sqlen) {
		this.sqlen_size = sqlen;
	}

	public void makeCropDesign() {
		ArrayList<ArrayList> areas = this.trunked_areas;
		ArrayList<Integer> area = new ArrayList<Integer>();
		ArrayList<ArrayList> crops = new ArrayList<ArrayList>();
		ArrayList<Long> area_sizes = new ArrayList<Long>();

		long W;
		long H;

		for(int i=0; i < areas.size();i++) {
			area = coerceSize(fix2crop(areas.get(i)));
			W = area.get(3).longValue();
			H = area.get(4).longValue();
			if(W*H > this.min_size) {
				crops.add(area);
				area_sizes.add(W*H);
				this.max_area_size = Math.max(this.max_area_size, W*H);
			}
		}

		for(int i=0; i<crops.size();i++) {
			ms.arlist2log(crops.get(i));
			IJ.log(String.valueOf(area_sizes.get(i)));
		}

		IJ.log(String.valueOf(this.max_area_size)+" is max area size");
		this.crop_areas = crops;
		this.area_sizes = area_sizes;
	}


	public ArrayList<Integer> fix2crop(ArrayList<Integer> area){
		int sqlen = this.sqlen_size;
		ArrayList<Integer> crop_area = new ArrayList<Integer>();

		crop_area.add(area.get(0));
		crop_area.add(sqlen*(area.get(1)));
		crop_area.add(sqlen*(area.get(2)));
		crop_area.add(sqlen*(area.get(3) -area.get(1)+1));
		crop_area.add(sqlen*(area.get(4) -area.get(2)+1));
		this.ms.arlist2log(crop_area);
		IJ.log("is fixed crop area");

		return crop_area;

	}

	public ArrayList<Integer> coerceSize(ArrayList<Integer> area) {
		//area is {index, x, y, w, h}
		//int[] original_size = this.original_ImageSize;
		int original_xl = this.original_ImageSize[0];
		int original_yl = this.original_ImageSize[1];
		int x = area.get(1);
		int y = area.get(2);
		int w = area.get(3);
		int h = area.get(4);

		ArrayList<Integer> crop_area = new ArrayList<Integer>();

		int margin = this.additional_margin;

		//int X = area.get(1) - margin;
		int X = Math.max(1, x - margin);
		//int Y = area.get(2) - margin;
		int Y = Math.max(1, y - margin);

		//int W = area.get(3) + 2*margin;
		int W = Math.min(margin, original_xl -x -w) +x+w-X;
		W = W - (W % this.triming4resize);

		int H = Math.min(margin, original_yl -y -h) +y+h-Y;
		H = H -(H % this.triming4resize);

		crop_area.add(area.get(0));
		crop_area.add(X);
		crop_area.add(Y);
		crop_area.add(W);
		crop_area.add(H);

		this.ms.arlist2log(crop_area);
		IJ.log("is oerced crop area.");

		return  crop_area;
	}

	public ArrayList<ArrayList>  getCropDesing(){
		return this.crop_areas;
	}

	public ArrayList<Long> getAreaSizes(){
		return this.area_sizes;
	}

	public long getMaxAreaSize() {
		return this.max_area_size;
	}
}
