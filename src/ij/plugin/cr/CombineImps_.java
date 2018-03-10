package ij.plugin.cr;

import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.StackCombiner;

public class CombineImps_ {
	private ImagePlus[] imps;
	private int seg_x;
	private int seg_y;
	private ImagePlus imp;
	private ImageStack is = new ImageStack();
	private List<ImageStack> iss= new ArrayList<ImageStack>();
	private StackCombiner st_com = new StackCombiner();

	Mscs_ ms = new Mscs_();

	public void setImpsXY(ImagePlus[] imps, int x, int y) {
		this.imps = imps;
		this.seg_x = x;
		this.seg_y = y;
		if(x * y != this.imps.length) {
			IJ.log("lenght is not equal...");
		}else {
			for(int i= 0; i < this.imps.length; i++) {
				//this.imps[i].show();
				this.iss.add(this.imps[i].getImageStack());
			}
		}

	}

	public void combineXY() {
		IJ.log("conbinination X"+this.seg_x+" and Y"+this.seg_y);


		this.imp = new ImagePlus();
		List<ImageStack> iss = this.iss;

		//int[] ss = {this.seg_x, this.seg_y};
		//ms.ints2ijlog(ss);

		for(int j=0; j < this.seg_y; j++) {
			for(int i=0; i < (this.seg_x - 1); i++) {
				this.is = this.st_com.combineHorizontally(iss.get(j), iss.get(j+1));
				iss.set(j, this.is);
				iss.remove(j+1);
			}
		}



		for(int j = 0; j < ( this.seg_y -1); j++) {
			this.is = this.st_com.combineVertically(iss.get(0), iss.get(1));
			iss.set(0, this.is);
			iss.remove(1);
		}


		this.imp = new ImagePlus();
		imp.setStack(iss.get(0));
		//imp.show();
	}

	public ImagePlus getImp() {
		return this.imp;
	}



}
