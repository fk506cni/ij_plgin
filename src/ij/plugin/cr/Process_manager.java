package ij.plugin.cr;

import ij.IJ;
import ij.ImagePlus;

public class Process_manager {
	private ImagePlus[] imps;
	private int big_sqlen = 2048;
	//private int small_sqlen = 32;
	private int big_divide = 32;
	private int additional_margin = 	64;
	private int min_size = 1024;
	//private String file;
	private String file = "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-AAC9-01A-01-TSA.tif";
	//private String file = "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-A113-01A-01-TS1.svs";

	private long allowed_pixels = (long)Math.pow(2d, 30d);
	//private long allowed_pixels = (long)Math.pow(2d, 20d);
	//private long allowed_pixels = (long)4000000;

	Mscs_ ms = new Mscs_();

	public void setFile(String file) {
		this.file = file;
	}

	public void process_file() {
		String file = this.file;

		//get meta data phase

		GetMeta_ gm = new GetMeta_();
		gm.setfile(file);
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
		PartAnal_ pt = new PartAnal_();
		pt.setImp(cmp.getImp());
		//pt.setThrMethod("Default");
		pt.autoThd();
		pt.partAnal();
		pt.trunkRect();

		//ms.arar2log(pt.getRectList());
		CompressDesign_ cd = new CompressDesign_();
		cd.setAreas(pt.getRectList());
		cd.trunkArea();


		//crop_out design
		CropDesign_ cpd = new CropDesign_();
		cpd.setArea(cd.getTrunckedArea());
		cpd.setAdditionalMargin(this.additional_margin);
		cpd.setMinSize(this.min_size);
		cpd.setOriginalSize(gm.getsize());
		cpd.setSqLen(this.big_divide);
		cpd.makeCropDesign();


		//crop_out
		TargetListCrop_ tlc = new TargetListCrop_();
		tlc.setFile(this.file);
		tlc.setAllowedPixel(this.allowed_pixels);
		tlc.setMaxArea(cpd.getMaxAreaSize());
		tlc.setAreaSizes(cpd.getAreaSizes());
		tlc.setCropDesign(cpd.getCropDesing());
		tlc.calcXdiv();
		tlc.makeCropImps();




	}


}
