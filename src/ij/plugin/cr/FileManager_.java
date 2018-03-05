package ij.plugin.cr;

import ij.IJ;

public class FileManager_ {
	private String inputfile;
	private String inputdir;
	private String outputdir;

	public void setInOut() {
		this.inputfile = IJ.getFilePath("InputFile");
		this.outputdir = IJ.getDirectory("choose output dir");
	}

	public void processFile(){
		Process_manager pm = new Process_manager();
		pm.setFile(this.inputfile);
		pm.setOutputDir(outputdir);
		pm.process_file();
	}

}
