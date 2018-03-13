package ij.plugin.cr;

import ij.IJ;

public class FileManager_ {
/*	private String inputfile;
	private String inputdir;
	private String outputdir;

	public void setInOut() {
		this.inputfile = IJ.getFilePath("InputFile");
		this.outputdir = IJ.getDirectory("choose output dir");
	}
*/
	public void processFile(){
		Args_getter agt = new Args_getter();
		agt.ArgsViaGui();

		Process_manager pm = new Process_manager();

		if(agt.getMode()=="file") {
			IJ.log("runnnign "+agt.getMode()+" mode.");
			pm.setArgsGetter(agt);
			pm.process_file();

		}else if(agt.getMode()=="dir") {
			IJ.log("runnnign "+agt.getMode()+" mode.");
			for(int i =0; i<agt.getFilesLength();i++) {
				agt.setFileByIter(i);
				pm.setArgsGetter(agt);
				pm.process_file();
			}
			IJ.log("dir mode loop process have done.");

		}


	}

}
