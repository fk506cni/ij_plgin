package ij.plugin.cr;

import ij.ImagePlus;

public class BigImps_ {
	private ImagePlus[] imps;
	private int[][] bigM;
	private int bigMlength;
	private String file;

	public void setM(int[][] bigM) {
		this.bigM = bigM;
		this.bigMlength = bigM.length;
	}

	public void setfile(String file) {
		this.file = file;
	}

	public void cropImps() {
		this.imps = new ImagePlus[bigMlength];

		CropFile_ cpf = new CropFile_();
		cpf.setfile(this.file);

		for(int i=0; i < this.bigMlength; i++) {
			cpf.setargs(bigM[i]);
			this.imps[i] = cpf.args2imp();
		}
	}

	public ImagePlus[] getImps() {
		return this.imps;
	}

	public ImagePlus getImg_i(int i) {
		return this.imps[i];
	}

	public int getImpslength() {
		return this.bigMlength;
	}

	public void showImps() {
		for(int i=0; i < this.bigMlength; i++) {
			this.imps[i].show();
		}
	}

}
