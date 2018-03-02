package ij.plugin.cr;

import ij.IJ;

public class BigDesign_ {
	private int[] size = {0,0};
	private int big_sqlen;

	int wid;
	int hei;
	int mat_wid;
	int mat_hei;

	int[] x_start;
	int[] x_wid;
	int[] y_start;
	int[] y_hei;
	int[] segment_size = new int[2];
	//int segmentX;
	//int segmentY;
	int[][] bigM;

	Mscs_ mscs = new Mscs_();


	public void setSize(int[] size) {
		this.size = size;
		this.wid = size[0];
		this.hei = size[1];
	}

	public void setBigSqlen(int big_sqlen) {
		this.big_sqlen = big_sqlen;
	}

	public void design() {
		this.mat_wid = (this.wid / this.big_sqlen) +1;
		this.mat_hei = (this.hei / this.big_sqlen) +1;

		this.x_start = new int[this.mat_wid];
		this.x_wid = new int[this.mat_wid];


		this.segment_size[0] = this.mat_wid;
		this.segment_size[1] = this.mat_hei;
		mscs.ints2ijlog(this.segment_size);
		this.bigM = new int[this.mat_wid * this.mat_hei][4];


		for(int i = 0; i < this.mat_wid; i++) {
			this.x_start[i] = this.big_sqlen * i;
			//IJ.log(String.valueOf(i));

			if(i != this.mat_wid -1) {
				this.x_wid[i] = this.big_sqlen;
			}else {
				this.x_wid[i] = this.wid % this.big_sqlen;
			}
		}

		this.y_start = new int[this.mat_hei];
		this.y_hei = new int[this.mat_hei];
		for(int i = 0; i < this.mat_hei; i ++) {
			this.y_start[i] = this.big_sqlen * i;
			if(i != this.mat_hei -1) {
				//IJ.log("tft");
				this.y_hei[i] = this.big_sqlen;
			}else {
				//IJ.log("ffunko");
				this.y_hei[i] = this.hei % this.big_sqlen;
			}
		}


		for(int j = 0; j < this.mat_hei;j++) {
			for(int i = 0; i < this.mat_wid; i++) {
				//IJ.log(String.valueOf(i));
				int[] crop_ij = {this.x_start[i], this.y_start[j], this.x_wid[i], this.y_hei[j]};
				int array_ij = j*this.mat_wid+i;
				this.bigM[array_ij] = crop_ij;
			}
		}
	}

	public int[] getSegSize() {
		return this.segment_size;
	}

	public int getSegX() {
		return this.segment_size[0];
	}

	public int getSegY() {
		return this.segment_size[1];
	}

	public int[] getRegion_i(int i) {
		if(i > bigM.length) {
			IJ.log("iteral larger than matrix length");
			int[] result = {0,0,0,0};
			return result;
		}else {
			int[] result = this.bigM[i];
			return result;
		}
	}

	public int getMlength() {
		return bigM.length;
	}

	public int[][] getM(){
		return bigM;
	}

}
