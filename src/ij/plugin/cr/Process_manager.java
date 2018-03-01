package ij.plugin.cr;

import ij.ImagePlus;

public class Process_manager {
	private String file;
	private ImagePlus[] imps;

	Mscs_ ms = new Mscs_();

	public void setFile(String file) {
		this.file = file;
	}

	public void process_file() {
		String file = "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-AAC9-01A-01-TSA.tif";
		this.file = file;

		//get meta data phase

		GetMeta_ gm = new GetMeta_();
		gm.setfile(this.file);
		gm.readmeta();


		//big design phase
		BigDesign_ bd = new BigDesign_();
		bd.setSize(gm.getsize());
		bd.design();

		ms.arar2log(bd.getM());

		BigImps_ bis = new BigImps_();
		bis.setfile(this.file);
		bis.setM(bd.getM());
		bis.cropImps();
		bis.showImps();

	}


}
