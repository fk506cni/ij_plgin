package ij.plugin.cr;

import ij.IJ;
import ij.ImagePlus;

public class BigImps_ {
	private ImagePlus[] imps;
	private int[][] bigM;
	private int bigMlength;
	private String file;
	private int ResizeRate;
	private Mscs_ ms = new Mscs_();

	public void setM(int[][] bigM) {
		this.bigM = bigM;
		this.bigMlength = bigM.length;
	}

	public void setfile(String file) {
		this.file = file;
	}

	public void setResizeRate(int Rrate) {
		this.ResizeRate = Rrate;
	}

	public void cropImps() {
		this.imps = new ImagePlus[bigMlength];

		int[][] bigM = this.bigM;
		//ms.arar2log(bigM);
		//IJ.log("is bigM");

		CropFile_ cpf = new CropFile_();
		cpf.setfile(this.file);

		SmallImps_ sis = new SmallImps_();
		sis.setRrate(this.ResizeRate);
		IJ.log(String.valueOf(this.ResizeRate) +" is Rrate");
		IJ.log(String.valueOf(bigMlength)+" is bigMlength");

		for(int i=0; i < this.bigMlength; i++) {
			cpf.setargs(bigM[i]);
			//ms.ints2ijlog(bigM[i]);
			IJ.log(String.valueOf(i));
			//this.imps[i] = cpf.args2imp();
			this.imps[i] = sis.Imp2resizeImp(cpf.args2imp());
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
