package ij.plugin.cr;

import ij.IJ;
import ij.ImagePlus;

public class Process_manager {
	private ImagePlus[] imps;
	private int big_sqlen = 1024;
	//private int big_sqlen = 4096;
	//private int small_sqlen = 32;
	private int big_divide = 32;
	private int additional_margin = 	64;

	//minimum size is pixel. not length
	private int min_size = 1000000;
	private String file;
	//private String file = "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-AAC9-01A-01-TSA.tif";
	private String output_dir;
	//private String output_dir = "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unkores\\";
	//private String file = "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-A113-01A-01-TS1.svs";

	//private long allowed_pixels = (long)Math.pow(2d, 31d) -2;
	private long allowed_pixels = (long)Math.pow(2d, 31d);
	//private long allowed_pixels = (long)4000000;

	private String thr_method = "Default";

	private Mscs_ ms = new Mscs_();
	private ImpsSave_ isv = new ImpsSave_();

	private Args_getter agt = new Args_getter();


	public void setFile(String file) {
		this.file = file;
	}

	public void setOutputDir(String outputdir) {
		this.output_dir = outputdir;
	}

	public void setArgsViaGUI() {
		this.agt.ArgsViaGui();
	}

	public void process_file() {
		Args_getter agt = this.agt;

		//String file = this.file;
		String file = agt.getFilePath();

		//String output_dir = this.output_dir;
		String output_dir = agt.getOutDir();

		ImpsSave_ isv = this.isv;
		isv.setOriginalFile(file);
		isv.setOutputDir(output_dir);

		//get meta data phase

		GetMeta_ gm = new GetMeta_();
		gm.setfile(file);
		gm.readmeta();


		//big design phase
		BigDesign_ bd = new BigDesign_();
		bd.setBigSqlen(agt.getBigSqlen());
		bd.setSize(gm.getsize());
		bd.design();

		//ms.arar2log(bd.getM());

		//get crops form big data
		BigImps_ bis = new BigImps_();
		bis.setfile(file);
		bis.setM(bd.getM());
		bis.setResizeRate(agt.getBigDivide());
		bis.cropImps();
		//bis.showImps();

		//resize big imps
/*		SmallImps_ sis = new SmallImps_();
		sis.setBigImps(bis.getImps());
		bis = null;
		sis.setRrate(big_divide);
		sis.runResize();
		//sis.showSmallImps();
 */

		//combine small imps
		CombineImps_ cmp = new CombineImps_();
		cmp.setImpsXY(bis.getImps(), bd.getSegX(), bd.getSegY());
		//IJ.log("kokomade");
		cmp.combineXY();

		//particle analysis
		IJ.log("PartAnal_");
		PartAnal_ pt = new PartAnal_();
		pt.setImp(cmp.getImp());
		pt.setThrMethod(agt.getAMethod());
		pt.autoThd();
		pt.partAnal();
		pt.trunkRect();
		isv.keyWdSave(pt.getMaskImp(), agt.getAMethod()+"_thrMsk");
		isv.resultSave("Rect_result");

		//ms.arar2log(pt.getRectList());
		IJ.log("CompressDesign");
		CompressDesign_ cd = new CompressDesign_();
		cd.setAreas(pt.getRectList());
		cd.trunkArea();


		//crop_out design
		IJ.log("CropDesignx");
		CropDesign_ cpd = new CropDesign_();
		cpd.setArea(cd.getTrunckedArea());
		cpd.setAdditionalMargin(agt.getAddMargin());
		cpd.setMinSize(agt.getMinSize());
		cpd.setOriginalSize(gm.getsize());
		cpd.setSqLen(agt.getBigDivide());
		cpd.makeCropDesign();


		//crop_out
		TargetListCrop_ tlc = new TargetListCrop_();
		tlc.setFile(agt.getFilePath());
		tlc.setOutput_dir(agt.getFilePath());
		tlc.setAllowedPixel(agt.getAllowedPx());
		tlc.setMaxArea(cpd.getMaxAreaSize());
		tlc.setAreaSizes(cpd.getAreaSizes());
		tlc.setCropDesign(cpd.getCropDesing());
		tlc.calcXdiv();
		tlc.makeCropImps();

/*
		//file_save
		ImpsSave_ isv = new ImpsSave_();
		isv.setImpsList(tlc.getImpsAr());
		isv.setOriginalFile(this.file);
		isv.setOutputDir(this.output_dir);
		isv.loopSave();
*/
		IJ.log(agt.getFilePath() +" was processed.");

	}


}
