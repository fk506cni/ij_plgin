package ij.plugin.cr;

import java.io.File;
import java.util.ArrayList;

import ij.IJ;
import ij.ImagePlus;


public class ImpsSave_ {
	private String original_file;
	private String output_dir;
	private String file_tag;
	private int Xdiv;
	private ArrayList<ImagePlus> imps_list;
	private String save_method = "ZIP";

	public void setImpsList(ArrayList<ImagePlus> imps_list) {
		this.imps_list = imps_list;
	}

	public boolean isWin32Sys() {
		//boolean result = String.valueOf(File.separatorChar) == "\\";
		boolean result = "\\".equals(File.separator);
		if(result) {
			IJ.log("Detected System is Windows system.");
		}else {
			IJ.log("Detected System is Not Windows system.");
		}
		return result;
	}

	public void setOriginalFile(String original_file) {
		this.original_file = original_file;
		String tag = original_file;
		if(isWin32Sys()) {
			tag = tag.replaceAll("^[-:0-9a-zA-Z_\\\\]*\\\\", "");
		}else {
			//for linux system
			tag = tag.replaceAll("^[-:0-9a-zA-Z/_]*/", "");
		}
		tag = tag.replaceAll("\\.[a-z0-9A-Zx]*$", "");
		this.file_tag = tag;
		IJ.log(tag +":: is file tag.");

	}

	public void setOutputDir(String output_dir) {
		this.output_dir = output_dir;
	}

	public void loopSave() {
		String outputfile="";
		ArrayList<ImagePlus> imps = this.imps_list;
		for(int i=0; i<imps.size();i++) {
			outputfile = this.output_dir + this.file_tag + "_" +String.valueOf(i);
			IJ.saveAs(imps.get(i), "ZIP", outputfile);
			IJ.log("saving at "+outputfile);
		}
	}

	public void iterSave(ImagePlus imp, int i) {
		String outputfile ="";
		outputfile = this.output_dir + this.file_tag + "_" +String.valueOf(i);
		IJ.saveAs(imp, this.save_method, outputfile);
		IJ.log("saving at "+outputfile);
	}

	public void keyWdSave(ImagePlus imp, String key) {
		String outputfile ="";
		outputfile = this.output_dir + this.file_tag + "_" +key;
		IJ.saveAs(imp, this.save_method, outputfile);
		IJ.log("saving at "+outputfile);
	}

	public void resultSave(String keyW) {
		String outputfile ="";
		outputfile = this.output_dir + this.file_tag + "_" +keyW;
		IJ.saveAs("Results", outputfile+".csv");
		IJ.log("Results table saved at "+ outputfile);

	}

	public void run(String args) {
		String file = IJ.getFilePath("choice of unko");
		//String file ="/home/dryad/Dropbox/Rsta/151imageJassist/majorsvs_symln/TCGA-ZS-A9CG-01A-01-TS1.57E5A555-C6B1-468D-AB86-94B8BF8C8326.svs.bz2";
		IJ.log(file);
		//String dir = IJ.getDirectory("unko of unko");
		//IJ.log(dir);
		//String file =  "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-AAC9-01A-01-TSA.tif";
		//String file = "D:\\Cloud\\Dropbox\\TCGA-BC-A10Q\\unko\\TCGA-DD-A113-01A-01-TS1.svs";
		setOriginalFile(file);
	}

}
