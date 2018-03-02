package ij.plugin.cr;

import fiji.threshold.Auto_Threshold;
import ij.ImagePlus;

public class PartAnal_ {
	private ImagePlus imp = new ImagePlus();


	public void setImp(ImagePlus imp) {
		this.imp = imp;
	}

	public void autoThd() {
		Auto_Threshold ath = new Auto_Threshold();

	}

}
