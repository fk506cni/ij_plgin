import ij.IJ;
import ij.plugin.PlugIn;
import ij.plugin.cr.FileManager_;

public class CropReader_ implements PlugIn{
	public void run (String arg) {
		IJ.log("starting CR plugin");

		FileManager_ fm = new FileManager_();
		fm.setInOut();
		fm.processFile();


		//Process_manager pm = new Process_manager();
		//pm.process_file();
	}
}