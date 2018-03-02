package ij.plugin.cr;

import ij.IJ;
import ij.ImagePlus;

public class Process_manager {
	private String file;
	private ImagePlus[] imps;
	private int big_sqlen = 2048;
	private int small_sqlen = 32;
	private int big_divide = 32;


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
		bd.setBigSqlen(this.big_sqlen);
		bd.setSize(gm.getsize());
		bd.design();

		//ms.arar2log(bd.getM());

		//get crops form big data
		BigImps_ bis = new BigImps_();
		bis.setfile(this.file);
		bis.setM(bd.getM());
		bis.cropImps();
		//bis.showImps();

		//resize big imps
		SmallImps_ sis = new SmallImps_();
		sis.setBigImps(bis.getImps());
		sis.setRrate(big_divide);
		sis.runResize();
		//sis.showSmallImps();

		//combine small imps
		CombineImps_ cmp = new CombineImps_();
		cmp.setImpsXY(sis.getSmallImps(), bd.getSegX(), bd.getSegY());
		IJ.log("kokomade");
		cmp.combineXY();

		//particle analysis


	}


}
